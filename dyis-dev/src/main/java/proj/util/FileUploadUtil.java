package proj.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @Class Name  : EgovFileUploadUtil.java
 * @Description : Spring 기반 File Upload 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.08.26       한성곤                  최초 생성
 *   2019.06.17       bsg        추가작업
 * @author 공통컴포넌트 개발팀 한성곤
 * @since 2009.08.26
 * @version 1.0
 * @see
 */
public class FileUploadUtil extends BasedFileUtil {
	
	/** 로그설정 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
	
	/** 첨부 최대 파일 크기 지정 */
    //private final static long maxFileBaseSize = 1024 * 1024 * 100;   //업로드 최대 사이즈 설정 (100M)
    
    
    /** 서버유형(개발, 운영) */
    //private final static String serverType = "";//EgovProperties.getProperty("Globals.serverType");
    
    /** 첨부파일 위치 지정 */
    private final static String uploadBaseDir = "";//EgovProperties.getProperty("Globals.fileStorePath."+serverType);
    
    /** 이미지파일  위치 지정 */
    private final static String uploadImgDir = "";//EgovProperties.getProperty("Globals.fileImgPath."+serverType);
    
    

    /** 첨부파일 위치 지정 업로드 된 대용량 파일의 임시 경로 지정 - 가용 메모리를 초과하는 대용량 파일의 경우 임시 경로에 일시적으로 파일이 기록되었다가 자동 제거됨 */
    private static String uploadTempDir = "";//EgovProperties.getProperty("Globals.fileStoreTempPath."+serverType);
    
    /** 가능한 이미지확장자 */
    private final static String useImgExt = "gif jpg jpeg png bmp";

    /** 가능한 확장자 */
    private final static String useExt = "txt ppt pptx xls xlsx doc hwp zip 7z gif jpg jpeg png bmp pdf";
    
    /** 첨부 최대 파일 크기 지정 */
    private static long maxFileSize = 1024 * 1024 * 50;   //업로드 최대 사이즈 설정 (50M)
    
    /** 가능한 이미지확장자(증명사진) */
    private final static String usePhotoExt = "gif jpg jpeg";

    /** 가능한 이미지확장자(엑셀업로드) */
    private final static String useExcelExt = "xls xlsx";

    /** 첨부 최대 파일 크기 지정(증명사진) */
    private static long maxPhotoSize = 1024 * 1024 / 10;   //업로드 최대 사이즈 설정 (100KB)
    
	//================================================================================
	// ③ [옵션][기본값:16MB] 가용 메모리 용량 1MB 로 지정할 경우 1MB 이하의 파일들은 모두 메모리 내에서 직접 처리되며, 초과하는 파일들만 임시적으로 파일 처리됨
	//================================================================================
	// 메모리 사용량 설정 (클수록 디스크 기록 부담율이 적어짐 - 16 MB
	//================================================================================
    //private static int AvailableMemory = 16 * 1024 * 1024;

	//================================================================================
	// ⑤ [옵션][기본값:utf-8] 업로드 엔코딩 - 업로드 과정에서 웹페이지의 UploadEncoding 값과 서버쪽 수신시 한글 엔코딩 방식이 무엇인지 설정한다. 둘다 반드시 일치해야 됨.
	//================================================================================
	// 브라우저에서 $(sel).IBUpload("UploadEncoding","utf-8") 로 설정했다면 utf-8 로 설정해야 한다.
	// 브라우저에서 $(sel).IBUpload("UploadEncoding","euc-kr") 로 설정했다면 euc-kr 로 설정해야 한다.
	// ( 한글 깨짐 주의 ★★★ ) 브라우저에서 설정한 값과 아래의 값은 반드시 일치해야 됨.
	//================================================================================
    //private static String UploadEncoding = "utf-8";
	//String UploadEncoding = "euc-kr";
	
	//================================================================================
	// ⑥ 서버에서 제한 하는 파일 확장자 (대/소문자 구분 없음)
	//================================================================================
	//   		권장 제한 확장자 (필요시 더 추가 할 것)
    //private static String[] invalidFileExtention = {"HTML",".HTM",".PHP",".PHP2",".PHP3",".PHP4",".PHP5",".PHTML",".PWML",".INC",".ASP",".ASPX",".ASCX",".JSP",".CFM",".CFC",".PL",".BAT",".EXE",".COM",".DLL",".VBS",".JS",".REG",".CGI",".HTACCESS",".ASIS",".SH",".SHTML",".SHTM",".PHTM",".ADP",".CHM",".CMD",".COM"}; 

    
    /**
	 * 업무 첨부파일 등록
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<Object> uploadFiles(HttpServletRequest request, String atthTypNm) throws Exception {
		List<Object> list = new ArrayList<>();

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator<?> fileIter = mptRequest.getFileNames();
		
		String sToday = getTodayString();
		String sPath = uploadBaseDir+atthTypNm+SEPERATOR+sToday.substring(0,4)+SEPERATOR+sToday.substring(4,6)+SEPERATOR+sToday.substring(6,8);
		String sDbPath = atthTypNm+SEPERATOR+sToday.substring(0,4)+SEPERATOR+sToday.substring(4,6)+SEPERATOR+sToday.substring(6,8);
		LOGGER.debug("uploadFiles sExt: " + mptRequest.getFileNames());		
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

			HashMap<String, Object>setMap = new HashMap<String, Object>();

			String sTmp = mFile.getOriginalFilename();
			
			//확장자 체크
			String sExt = sTmp.substring(sTmp.lastIndexOf(".")+1, sTmp.length()).toLowerCase();
			LOGGER.debug("uploadFiles sExt: " + sExt);
			
			//허용확장자만 저장
			if(useExt.indexOf(sExt) > -1){
				
				if (sTmp.lastIndexOf("\\") >= 0) {
					sTmp = sTmp.substring(sTmp.lastIndexOf("\\") + 1);
				}
				
				
				File file = new File(sPath);
				
				if(!file.exists()){
					file.mkdir();
				}
				
				LOGGER.debug("uploadFiles atthTyp: " + atthTypNm);
				LOGGER.debug("uploadFiles sTmp: " + sTmp);
				LOGGER.debug("uploadFiles mFile: " + mFile);
				LOGGER.debug("uploadFiles getPhysicalFileName: " + getPhysicalFileName());
				LOGGER.debug("uploadFiles getSize: " + mFile.getSize());
				LOGGER.debug("uploadFiles file isDirectory: " + file.isDirectory());
				LOGGER.debug("uploadFiles file exists: " + file.exists());
				
				
				setMap.put("atthNm", sTmp);
				setMap.put("atthSaveNm", getPhysicalFileName());
				setMap.put("atthPath", sDbPath);
				setMap.put("atthSize", mFile.getSize());
				setMap.put("contentType", mFile.getContentType());
				
	
				if (mFile.getSize() > 0 && mFile.getSize() < maxFileSize) {
					InputStream is = null;
	
					try {
						is = mFile.getInputStream();
						saveFile(is, new File(WebUtil.filePathBlackList(sPath + SEPERATOR + setMap.get("atthSaveNm"))));
					} finally {
						if (is != null) {
							is.close();
						}
					}
					setMap.put("uploadYn", "Y");	
				}else{
					setMap.put("uploadYn", "N");	
				}
				
				list.add(setMap);
			}
		}

		return list;
	}
	
	/**
	 * 게시판 이미지을 Upload 처리한다.
	 *
	 * @param request
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static List<Object> uploadImgFiles(HttpServletRequest request) throws Exception {
		
		List<Object> list = new ArrayList<>();

		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		Iterator<?> fileIter = mptRequest.getFileNames();

		String sToday = getTodayString();
		String sPath = uploadImgDir + "board"+SEPERATOR+sToday.substring(0,4)+SEPERATOR+sToday.substring(4,6)+SEPERATOR+sToday.substring(6,8) + SEPERATOR;
		
		while (fileIter.hasNext()) {
			MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

			HashMap<String, Object>setMap = new HashMap<String, Object>();

			String sTmp = mFile.getOriginalFilename();

			//확장자 체크
			String sExt = sTmp.substring(sTmp.lastIndexOf(".")+1, sTmp.length()).toLowerCase();
			//LOGGER.debug("uploadFiles sExt: " + sExt);
			
			//허용확장자만 저장
			if(useImgExt.indexOf(sExt) > -1){
				
				if (sTmp.lastIndexOf("\\") >= 0) {
					sTmp = sTmp.substring(sTmp.lastIndexOf("\\") + 1);
				}
				
				File file = new File(sPath);
				
				/*
				LOGGER.debug("uploadFiles sTmp: " + sTmp);
				LOGGER.debug("uploadFiles mFile: " + mFile);
				LOGGER.debug("uploadFiles getTodayString: " + getTodayString());
				LOGGER.debug("uploadFiles getPhysicalFileName: " + getPhysicalFileName());
				LOGGER.debug("uploadFiles getSize: " + mFile.getSize());
				LOGGER.debug("uploadFiles file isDirectory: " + file.isDirectory());
				LOGGER.debug("uploadFiles file exists: " + file.exists());
				*/
				if(!file.exists()){
					file.mkdir();
				}
				
				setMap.put("atthNm", sTmp);
				setMap.put("atthSaveNm", getPhysicalFileName());
				setMap.put("atthPath", sPath);
				setMap.put("atthSize", mFile.getSize());
				setMap.put("contentType", mFile.getContentType());
	
				if (mFile.getSize() > 0 && mFile.getSize() < maxFileSize) {
					InputStream is = null;
	
					try {
						is = mFile.getInputStream();
						saveFile(is, new File(WebUtil.filePathBlackList(sPath + SEPERATOR + setMap.get("atthSaveNm"))));
					} finally {
						if (is != null) {
							is.close();
						}
					}
					list.add(setMap);
				}
			}
		}

		return list;
	}
	
	/**
	 * 증명사진 이미지을 Upload 처리
	 *
	 * @param request
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object>uploadPhotoFiles(HttpServletRequest request) throws Exception {
		
		HashMap<String, Object>resultMap = new HashMap<String, Object>();
		
		try{
			MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
			Iterator<?> fileIter = mptRequest.getFileNames();
			
			String sToday = getTodayString();
			String sPath = uploadImgDir + "photo"+SEPERATOR+sToday.substring(0,6)+SEPERATOR;
			
			LOGGER.debug("uploadFiles fileIter: " + fileIter);
			
			while (fileIter.hasNext()) {
				MultipartFile mFile = mptRequest.getFile((String) fileIter.next());
				String sTmp = mFile.getOriginalFilename();
				LOGGER.debug("uploadFiles sTmp: " + sTmp);
				//확장자 체크
				String sExt = sTmp.substring(sTmp.lastIndexOf(".")+1, sTmp.length()).toLowerCase();
				LOGGER.debug("uploadFiles sExt: " + sExt);
				
				
				//허용확장자만 저장
				if(usePhotoExt.indexOf(sExt) > -1){
					if (sTmp.lastIndexOf("\\") >= 0) {
						sTmp = sTmp.substring(sTmp.lastIndexOf("\\") + 1);
					}
					
					File file = new File(sPath);
					
					
					LOGGER.debug("uploadFiles sTmp: " + sTmp);
					LOGGER.debug("uploadFiles mFile: " + mFile);
					LOGGER.debug("uploadFiles getTodayString: " + getTodayString());
					LOGGER.debug("uploadFiles getPhysicalFileName: " + getPhysicalFileName());
					LOGGER.debug("uploadFiles getSize: " + mFile.getSize());
					LOGGER.debug("uploadFiles file isDirectory: " + file.isDirectory());
					LOGGER.debug("uploadFiles file exists: " + file.exists());
				
					LOGGER.debug("uploadFiles file exists: " + mFile.getContentType());
					if(!file.exists()){
						file.mkdir();
					}
					
					if (mFile.getSize() > 0 && mFile.getSize() < maxPhotoSize) {
						InputStream is = null;
		
						try {
							resultMap.put("atthNm", sTmp);
							resultMap.put("atthSaveNm", getPhysicalFileName());
							resultMap.put("atthPath", sPath);
							resultMap.put("atthSize", mFile.getSize());
							resultMap.put("contentType", mFile.getContentType());
							resultMap.put("resultCode", 0);
							
							is = mFile.getInputStream();
							saveFile(is, new File(WebUtil.filePathBlackList(sPath + SEPERATOR + resultMap.get("atthSaveNm"))));
							
						} finally {
							if (is != null) {
								is.close();
							}
						}
					}else{
						resultMap.put("resultCode", -1);
					}
				}else{
					resultMap.put("resultCode", -1);
				}
			}
		}catch(Exception e){
			resultMap.put("resultCode", -1);
			LOGGER.debug("uploadFiles Exception: " + e.getMessage());
		}
		
		return resultMap;
	}
	
	
	/**
	 * 실무수련자 엑셀 업로드 
	 *
	 * @param request
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> uploadExcelFiles(HttpServletRequest request, String sFolder) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		LOGGER.debug("uploadExcelFiles START ");
		MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
		
		String sToday = getTodayString();
		String sPath = uploadBaseDir + SEPERATOR + sFolder+SEPERATOR+sToday.substring(0,6)+SEPERATOR;
		
		MultipartFile mFile = mptRequest.getFile("file");
		String sTmp = mFile.getOriginalFilename();
		//확장자 체크
		String sExt = sTmp.substring(sTmp.lastIndexOf(".")+1, sTmp.length()).toLowerCase();
		//LOGGER.debug("uploadFiles sExt: " + sExt);
		
		LOGGER.debug("uploadExcelFiles file sTmp: " + sTmp);
		
		//허용확장자만 저장
		if(useExcelExt.indexOf(sExt) > -1){
			if (sTmp.lastIndexOf("\\") >= 0) {
				sTmp = sTmp.substring(sTmp.lastIndexOf("\\") + 1);
			}
			
			File file = new File(sPath);
			
			if(!file.exists()){
				file.mkdir();
			}
			if (mFile.getSize() > 0 ) {
				InputStream is = null;

				try {
					resultMap.put("fileNm", sTmp);
					resultMap.put("fileSaveNm", getPhysicalFileName());
					resultMap.put("filePath", sPath);
					resultMap.put("fileSize", mFile.getSize());
					resultMap.put("contentType", mFile.getContentType());
					resultMap.put("resultCode", 0);
					//LOGGER.debug("uploadExcelFiles file resultMap " + resultMap);
					is = mFile.getInputStream();
					saveFile(is, new File(WebUtil.filePathBlackList(sPath + SEPERATOR + resultMap.get("fileSaveNm"))));
					
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}else{
				resultMap.put("resultCode", -1);
			}
		}else{
			resultMap.put("resultCode", -1);
		}
		
		return resultMap;
	}
	
	
	/**
	 * 폴더 생성
	 *
	 * @param request
	 * @param maxFileSize
	 * @return
	 * @throws Exception
	 */
	public static void uploadFolderCreate(String atthTypNm) throws Exception {
		
		String sToday = getTodayString();
		//String sPath = uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)+"/"+sToday.substring(6,8);
		
		//저장할 디렉토리 생성 (없으면 생성함.)
		if( (!new File(uploadBaseDir).exists())&&(!new File(uploadBaseDir).isDirectory())  ){
			File saveDir = new File(uploadBaseDir);
			saveDir.mkdir();
		}
		//temp 디렉토리 생성 (없는 경우에만 생성함)
		if( (!new File(uploadTempDir).exists())&&(!new File(uploadTempDir).isDirectory())  ){
			File saveDir = new File(uploadTempDir);
			saveDir.mkdir();
		}
		//저장할 디렉토리 이하에 sub 디렉토리 생성 
		if( (!new File(uploadBaseDir+atthTypNm).exists())&&(!new File(uploadBaseDir+atthTypNm).isDirectory())  ){
			File saveDir = new File(uploadBaseDir+atthTypNm);
			saveDir.mkdir();
		}
		//저장할 디렉토리 이하에 sub 디렉토리 생성 
		if( (!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)).exists())&&(!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)).isDirectory())  ){
			File saveDir = new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4));
			saveDir.mkdir();
		}
		//저장할 디렉토리 이하에 sub 디렉토리 생성 
		if( (!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)).exists())&&(!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)).isDirectory())  ){
			File saveDir = new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6));
			saveDir.mkdir();
		}
		//저장할 디렉토리 이하에 sub 디렉토리 생성 
		if( (!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)+"/"+sToday.substring(6,8)).exists())&&(!new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)+"/"+sToday.substring(6,8)).isDirectory())  ){
			File saveDir = new File(uploadBaseDir+atthTypNm+"/"+sToday.substring(0,4)+"/"+sToday.substring(4,6)+"/"+sToday.substring(6,8));
			saveDir.mkdir();
		}
	}
}
