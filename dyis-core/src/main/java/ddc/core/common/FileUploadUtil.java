package ddc.core.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ddc.core.config.ConfigProperty;
import javax.servlet.http.HttpServletRequest;


/**
 * @Class Name  : FileUploadUtil.java
 * @Description : Spring 기반 File Upload 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2023.01.05       bsg        추가작업
 * @author
 * @since 2023.01.05
 * @version 1.0
 * @see
 */
public class FileUploadUtil  {

	/** 로그설정 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);

	public static final String SEPERATOR = File.separator;

	private static volatile SecureRandom numberGenerator = null;

	public static final int BUFFER_SIZE = 8192;

	/** 첨부 최대 파일 크기 지정 */
    private final static long maxFileBaseSize = 1024 * 1024 * 300;   //업로드 최대 사이즈 설정 (100M)

    /** 서버유형 */
    private final static String serverType = ConfigProperty.getString("active");

    /** 첨부파일 위치 지정 */
    private final static String uploadBaseDir = ConfigProperty.getString("file.path");

    /** 이미지파일  위치 지정 */
    private final static String uploadImgDir = ConfigProperty.getString("file.imgPath");

    /** 레포트파일 위치 지정 */
    private final static String uploadRptDir = ConfigProperty.getString("file.rptPath");

    /** 첨부파일 위치 지정 업로드 된 대용량 파일의 임시 경로 지정 - 가용 메모리를 초과하는 대용량 파일의 경우 임시 경로에 일시적으로 파일이 기록되었다가 자동 제거됨 */
    //private static String uploadTempDir = ConfigProperty.getString("file.path.tmp");

    /** 가능한 이미지확장자 */
    private final static String useImgExt = "gif jpg jpeg png bmp";

    /** 가능한 확장자 */
    private final static String useExt = "txt ppt pptx xls xlsx doc docx hwp hwpx zip 7z gif jpg jpeg png bmp pdf etc hml";

    /** 오즈 확장자 */
    private final static String useExtOz = "ozr odi";

	//================================================================================
	// ③ [옵션][기본값:16MB] 가용 메모리 용량 1MB 로 지정할 경우 1MB 이하의 파일들은 모두 메모리 내에서 직접 처리되며, 초과하는 파일들만 임시적으로 파일 처리됨
	//================================================================================
	// 메모리 사용량 설정 (클수록 디스크 기록 부담율이 적어짐 - 16 MB
	//================================================================================
    private static int AvailableMemory = 16 * 1024 * 1024;

	//================================================================================
	// ⑤ [옵션][기본값:utf-8] 업로드 엔코딩 - 업로드 과정에서 웹페이지의 UploadEncoding 값과 서버쪽 수신시 한글 엔코딩 방식이 무엇인지 설정한다. 둘다 반드시 일치해야 됨.
	//================================================================================
	// 브라우저에서 $(sel).IBUpload("UploadEncoding","utf-8") 로 설정했다면 utf-8 로 설정해야 한다.
	// 브라우저에서 $(sel).IBUpload("UploadEncoding","euc-kr") 로 설정했다면 euc-kr 로 설정해야 한다.
	// ( 한글 깨짐 주의 ★★★ ) 브라우저에서 설정한 값과 아래의 값은 반드시 일치해야 됨.
	//================================================================================
    private static String UploadEncoding = "utf-8";
	//String UploadEncoding = "euc-kr";

	//================================================================================
	// ⑥ 서버에서 제한 하는 파일 확장자 (대/소문자 구분 없음)
	//================================================================================
	//   		권장 제한 확장자 (필요시 더 추가 할 것)
    //private static String[] invalidFileExtention = {"HTML",".HTM",".PHP",".PHP2",".PHP3",".PHP4",".PHP5",".PHTML",".PWML",".INC",".ASP",".ASPX",".ASCX",".JSP",".CFM",".CFC",".PL",".BAT",".EXE",".COM",".DLL",".VBS",".JS",".REG",".CGI",".HTACCESS",".ASIS",".SH",".SHTML",".SHTM",".PHTM",".ADP",".CHM",".CMD",".COM"};

    /**
	 * 업무 첨부파일 등록
	 *
	 * @param request 전체로 넘어오는 경우
	 * @return
	 * @throws Exception
	 */
    public static List<Map<String, Object>> uploadFiles(HttpServletRequest request, String atthType, String fileAtthNo) throws Exception {
    	MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator<?> fileIter = mptRequest.getFileNames();
		List<MultipartFile> fileList = new ArrayList();

		while (fileIter.hasNext()) {
			fileList.add(mptRequest.getFile((String) fileIter.next()));
		}
    	return uploadFiles(fileList, atthType, fileAtthNo, "");

    }

    /**
	 * 업무 첨부파일 등록
	 *
	 * @param 파일리스트 넘어오는 경우
	 * @return
	 * @throws Exception
	 */
    public static List<Map<String, Object>> uploadFiles(List<MultipartFile> fileList, String atthType, String fileAtthNo) {

    	return uploadFiles(fileList, atthType, fileAtthNo, "");

    }

    /**
	 * 업무 첨부파일 등록
	 *
	 * @param 파일 단건으로 넘어오는 경우
	 * @return
	 * @throws Exception
	 */
    public static List<Map<String, Object>> uploadFiles(MultipartFile file, String atthType, String fileAtthNo, String fileType) {
		List fileList = new ArrayList();
		fileList.add(file);

    	return uploadFiles(fileList, atthType, fileAtthNo, fileType);

    }

    /**
   	 * 업무 첨부파일 등록
   	 *
   	 * @param
   	 * @return
   	 * @throws Exception
   	 */
	public static List<Map<String, Object>> uploadFiles(List<MultipartFile> fileList, String atthType, String fileAtthNo, String fileType) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;

		String sToday = CommonUtil.getCurrentDate(); //오늘일자
		String sPath = uploadBaseDir+SEPERATOR+atthType+SEPERATOR+sToday.substring(0,8); //파일저장경로 기본경로+첨부id+년월+일
		String sDbPath = atthType+SEPERATOR+sToday.substring(0,8); //DB저장정보 (기본경로 제외)

		if("img".equals(fileType)) {
			sPath = uploadImgDir+SEPERATOR+atthType+SEPERATOR+sToday.substring(0,8); //파일저장경로 기본경로+첨부id+년월+일
			sDbPath = atthType+SEPERATOR+sToday.substring(0,8); //DB저장정보 (기본경로 제외)
		}

		LOGGER.debug("uploadFiles sPath: " + sPath);

		uploadFolderCreate(sPath); //폴더 생성
		LOGGER.debug("uploadFiles fileList: " + fileList.size());
		for(MultipartFile mFile : fileList){
			Map<String, Object> setMap = new HashMap<String, Object>();

			String fileOrgNm = mFile.getOriginalFilename();

			//확장자 체크
			String sExt = fileOrgNm.substring(fileOrgNm.lastIndexOf(".")+1, fileOrgNm.length()).toLowerCase();
			//LOGGER.debug("uploadFiles sExt: " + sExt);

			//허용확장자만 저장
			if(useExt.indexOf(sExt) > -1){

				if (fileOrgNm.lastIndexOf("\\") >= 0) {
					fileOrgNm = fileOrgNm.substring(fileOrgNm.lastIndexOf("\\") + 1);
				}

				File file = new File(sPath);

				if(!file.exists()){
					file.mkdir();
				}

				String filePhysiNm = getPhysicalFileName();
				LOGGER.debug("uploadFiles atthTyp: " + atthType);
				LOGGER.debug("uploadFiles fileOrgNm: " + fileOrgNm);
				LOGGER.debug("uploadFiles getPhysicalFileName: " + filePhysiNm);
				LOGGER.debug("uploadFiles getSize: " + mFile.getSize());
				//LOGGER.debug("uploadFiles mFile: " + mFile);
				//LOGGER.debug("uploadFiles file isDirectory: " + file.isDirectory());
				//LOGGER.debug("uploadFiles file exists: " + file.exists());

				setMap.put("fileOrgNm", fileOrgNm);
				setMap.put("fileMngNm", filePhysiNm);
				setMap.put("filePath", sDbPath);
				setMap.put("fileSize", mFile.getSize());
				setMap.put("contentType", mFile.getContentType());

				if (mFile.getSize() > 0 && mFile.getSize() < maxFileBaseSize) {
					InputStream is = null;

					try {
						is = mFile.getInputStream();
						saveFile(is, new File(filePathBlackList(sPath + SEPERATOR + setMap.get("fileMngNm"))));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						LOGGER.debug("uploadFiles IOException : " + e.toString());
					} finally {
						if (is != null) {
							try {
								is.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								LOGGER.debug("uploadFiles IOException : " + e.toString());
							}
						}
					}
					setMap.put("uploadYn", "Y");
				}else{
					setMap.put("uploadYn", "N");
				}

				list.add(setMap);
			}else {
				LOGGER.debug("uploadFiles 미허용 확장자: " + fileOrgNm);
			}

		}
		return list;
	}


	/**
	 * 파일 업로드 실패시 삭제 (리스트)
	 * @param resources
	 */
	public static void uploadFileDel(List fileList) {


		for(int i =0; i < fileList.size(); i++){
			HashMap<String, Object> setMap = (HashMap) fileList.get(i);

			LOGGER.debug("uploadFileDel : " + uploadBaseDir + SEPERATOR + setMap.get("filePath") + SEPERATOR + setMap.get("fileMngNm"));
			File file = new File(uploadBaseDir + SEPERATOR + setMap.get("filePath") + SEPERATOR + setMap.get("fileMngNm"));
			if(file.exists()) {
				file.delete();
			}

		}

	}

	 /**
   	 * 레포트 첨부파일 등록
   	 *
   	 * @param
   	 * @return
   	 * @throws Exception
   	 */
	public static HashMap uploadRptFiles(List<MultipartFile> fileList, HashMap paramMap) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;

		String sToday = CommonUtil.getCurrentDate(); //오늘일자
		String sPath = uploadRptDir; //파일저장경로

		LOGGER.debug("uploadRptFiles sPath: " + sPath);
		uploadFolderCreate(sPath); //폴더 생성

		LOGGER.debug("uploadRptFiles fileList: " + fileList.size());
		for(MultipartFile mFile : fileList){
			String fileOrgNm = mFile.getOriginalFilename();

			//확장자 체크
			String sExt = fileOrgNm.substring(fileOrgNm.lastIndexOf(".")+1, fileOrgNm.length()).toLowerCase();
			LOGGER.debug("uploadRptFiles sExt: " + sExt);
			//serverType

			//허용확장자만 저장
			if(useExtOz.indexOf(sExt) > -1){

				File file = new File(sPath + SEPERATOR + fileOrgNm);

				//기존 파일 존재 할 경우 백업
				if(file.isFile()) {
					file.renameTo(new File(sPath + SEPERATOR + fileOrgNm + "_" + sToday)); //파일복사
					file.delete(); //기존파일삭제
				}

				if (fileOrgNm.lastIndexOf("\\") >= 0) {
					fileOrgNm = fileOrgNm.substring(fileOrgNm.lastIndexOf("\\") + 1);
				}


				//LOGGER.debug("uploadRptFiles fileOrgNm: " + fileOrgNm);
				//LOGGER.debug("uploadRptFiles getSize: " + mFile.getSize());

				if (mFile.getSize() > 0 && mFile.getSize() < maxFileBaseSize) {
					InputStream is = null;

					try {
						is = mFile.getInputStream();
						saveFile(is, new File(filePathBlackList(sPath + SEPERATOR + fileOrgNm)));
					} finally {
						if (is != null) {
							is.close();
						}
					}
				}

				if("ozr".indexOf(sExt) > -1){
					paramMap.put("rptFileNm", fileOrgNm.substring(0, fileOrgNm.lastIndexOf(".")));
				}

			}else {
				LOGGER.debug("uploadRptFiles 미허용 확장자: " + fileOrgNm);
			}

		}
		return paramMap;
	}


	/**
	 * 파일 업로드 실패시 삭제 (레포트)
	 * @param resources
	 */
	public static void uploadFileRptDel(HashMap fileMap) {


		//백업된 정보 복구
		String sToday = CommonUtil.getCurrentDate(); //오늘일자

		String[] files = {".ozr",".odi"};

		File file = null;
		for(int i = 0; i < files.length; i++) {
			//업로드 삭제
			LOGGER.debug("uploadFileRptDel : " + uploadRptDir + SEPERATOR + fileMap.get("rptFileNm") + files[0]);
			file = new File(uploadRptDir + SEPERATOR + fileMap.get("rptFileNm") + files[0]);
			if(file.exists()) {
				file.delete();
			}

			//백업파일 존재시 복구
			LOGGER.debug("uploadFileRptDel : " + uploadRptDir + SEPERATOR + fileMap.get("rptFileNm") + ".ozr_" + sToday);
			file = new File(uploadRptDir + SEPERATOR + fileMap.get("rptFileNm") + ".ozr_" + sToday);
			if(file.isFile()) {
				file.renameTo(new File(uploadRptDir + SEPERATOR + fileMap.get("rptFileNm") + ".ozr" )); //파일복사
				file.delete(); //기존파일삭제
			}
		}
	}


	/**
	 * 폴더 생성
	 *
	 * @param request
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static void uploadFolderCreate(String atthTypNm) {
		LOGGER.debug("uploadFiles atthTypNm: " + atthTypNm);
		String[] folders = atthTypNm.split("/");
		String folderPath = "";
		for(int i = 0; i < folders.length; i ++) {
			if(folders[i].indexOf(":") != -1) {
				folderPath = folders[i];
			}else {
				folderPath = folderPath + "/" + folders[i];
				if( (!new File(folderPath).exists())&&(!new File(folderPath).isDirectory())  ){
					new File(folderPath).mkdir();
				}

			}
		}
	}


	/**
	 * 물리적 파일명 생성.
	 * @return
	 */
	public static String getPhysicalFileName() {
		return BasedUUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}


	/**
	 * 파일 경로 처리
	 * @return
	 */
	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * Stream으로부터 파일을 저장함.
	 * @param is InputStream
	 * @param file File
	 * @throws IOException
	 */
	public static long saveFile(InputStream is, File file) {
		// 디렉토리 생성
		if (!file.getParentFile().exists()) {
			//2017.02.08 	이정은 	시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
			if(file.getParentFile().mkdirs()){
				LOGGER.debug("[file.mkdirs] file : Directory Creation Success");
			}
			else{
				LOGGER.error("[file.mkdirs] file : Directory Creation Fail");
			}
		}

		OutputStream os = null;
		long size = 0L;

		try {
			LOGGER.debug("[file.mkdirs] file : Directory Creation file " + file);
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFFER_SIZE];

			while ((bytesRead = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
				size += bytesRead;
				os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(os);
		}

		return size;
	}

	/**
	 * Resource close 처리.
	 * @param resources
	 */
	public static void close(Closeable  ... resources) {
		for (Closeable resource : resources) {
			if (resource != null) {
				try {
					resource.close();
				} catch (IOException ignore) {
					LOGGER.error("[IOException] : Connection Close");
				} catch (Exception ignore) {
					LOGGER.error("["+ ignore.getClass() +"] Connection Close : " + ignore.getMessage());
				}
			}
		}
	}

	/**
	 * 파일 삭제
	 * @param resources
	 */
	public static void uploadFileDel(String filePath) {

		File file = new File(filePath);
		if(file.exists()) {
			LOGGER.debug("uploadFileDel 파일 삭제 처리 !! : " + file.getName() );
			file.delete();
		}else {
			LOGGER.debug("uploadFileDel 파일 존재 하지 않음!!" );
		}
	}

	/**
	 * 경로에 있는 파일 다른 경로로 이동
	 * @param resources
	 */
	public static Map<String, Object> moveFile(String currentPath, String atthType, String fileName) {
		Map<String, Object> resultFile = new HashMap<>();

		String sToday = CommonUtil.getCurrentDate(); //오늘일자
		String movePath = uploadBaseDir+SEPERATOR+atthType+SEPERATOR+sToday.substring(0,8); //파일저장경로 기본경로+첨부id+년월+일

		LOGGER.debug("moveG2bFile currentPath: " + currentPath);
		LOGGER.debug("moveG2bFile movePath: " + movePath);

        // 파일 객체 생성
        File currentDirectory = new File(currentPath);
        File moveDirectory = new File(movePath);
        if (!moveDirectory.exists()) 	moveDirectory.mkdirs();

        // 현재 경로의 파일 목록 가져오기
        File[] files = currentDirectory.listFiles();

        // 파일 정보 출력 및 이동
        if (files != null) {
            for (File file : files) {
            	//LOGGER.debug("경로 파일 이름: " + file.getName());
                if (file.isFile() && file.getName().equals(fileName)) {	//이동하려는 파일 찾아서 이동

                	resultFile.put("fileOrgNm", fileName);
                	resultFile.put("fileMngNm", getPhysicalFileName());
                	resultFile.put("filePath", atthType+SEPERATOR+sToday.substring(0,8));	//DB저장정보 (기본경로 제외)
                	resultFile.put("fileSize", file.length());
                	resultFile.put("delYn", "N");
                	resultFile.put("atthType", atthType);

                    // 파일 이동
                    try {
                        Path source = file.toPath();
                        Path destination = new File(moveDirectory, CommonUtil.toString(resultFile.get("fileMngNm"))).toPath();
                        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);

                    } catch (IOException e) {
                    	LOGGER.error("moveFile 파일 이동 중 오류가 발생했습니다: " + e.getMessage());
                    }
                }
            }
        } else {
        	LOGGER.debug(currentPath + " 에 파일이 존재하지 않습니다.");
        }
        return resultFile;
	}

	/**
	 * 경로에 있는 파일 다른 경로로 이동
	 * @param resources
	 */
	public static List<Map<String, Object>> moveG2bFiles(List<Map<String, Object>> fileMap, String atthType, String g2bPath, String fileNm) {

		List<Map<String, Object>> resultMap = new ArrayList<>();

		for(int i=0; i<fileMap.size(); i++) {

			Map<String, Object> file = new HashMap<>();
			file.putAll(fileMap.get(i));

			LOGGER.debug("moveG2bFiles fileNm: " + fileNm);
			String fileName = fileNm.split("\\.")[0] + "." + file.get("atflSn") + "." + ((String) file.get("atchDoc")).split("\\.")[1];
			LOGGER.debug("moveG2bFiles fileName: " + fileName);

			if(!CommonUtil.isNull(fileName)) {
				Map<String, Object> moveFileMap = moveFile(g2bPath, atthType, fileName);
				LOGGER.debug("moveG2bFiles moveFileMap: " + moveFileMap);
				if(!moveFileMap.isEmpty())	{
					moveFileMap.put("fileOrgNm", fileMap.get(i).get("atchDoc"));	//xml에서 얻은 파일이름으로 변경(GUISAP2024020515180896390R.1.pptx -> 보증서발급신청서및보증접수업무흐름.pptx)
					resultMap.add(moveFileMap);
				}
			}
		}
		LOGGER.debug("moveG2bFiles fileInfo: " + resultMap);

		return resultMap;
	}


}

