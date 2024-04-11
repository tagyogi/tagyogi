package ddc.web.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.LogProcService;
import ddc.core.common.CommonUtil;
import ddc.core.common.FileUploadUtil;
import ddc.core.config.ConfigProperty;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.web.WebBaseController;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>FileController.java (파일관련처리 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  : 파일관련처리
 */
@RequestMapping("/file")
@Controller("fileController")
public class FileController extends WebBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	public static final String SEPARATOR = File.separator;

	/** 첨부파일 위치 지정 */
    private final static String uploadBaseDir = ConfigProperty.getString("file.path");

    /** 첨부파일 위치 지정 (게시판이미지) */
    private final static String uploadImgDir = ConfigProperty.getString("file.imgPath");

	@Autowired
	private FileService fileService;

	@Autowired
	private LogProcService logProcService;

	/**
	 * 첨부파일업로드 (IBUpload 처리방식)
	 * @throws Exception
	 */
	@RequestMapping(value="/fileUpload.do")
	public ModelAndView fileUpload(HttpServletRequest request) throws Exception {
		LOGGER.debug("fileUpload START ");

		JsonResult result = new JsonResult();

		try {
			HashMap paramMap = new HashMap();
			String atthNo = (String)request.getParameter("atthNo");
			String atthType = (String)request.getParameter("atthType");
			String docCd = (String)request.getParameter("docCd");

			LOGGER.debug("fileUpload START atthNo : " + atthNo);
			LOGGER.debug("fileUpload START atthType : " + atthType);
			LOGGER.debug("fileUpload START docCd : " + docCd);


			if("".equals(atthNo) || atthNo == null) {
				//첨부파일번호 추출
				paramMap.put("atthType", atthType);
				fileService.selectMaxFileNo(paramMap);
				atthNo = (String)paramMap.get("atthNo"); //첨부파일추출번호
			}

			List fileList = new ArrayList();
			if(atthNo != null) {
				LOGGER.debug("fileUpload paramMap :: "+ paramMap );

				fileList = FileUploadUtil.uploadFiles(request, atthType, atthNo);

				//파일저장처리
				if(fileList.size() > 0) {
					for (int i = 0; i < fileList.size(); i++) {
						HashMap<String, Object> getMap = (HashMap) fileList.get(i);
						getMap.put("atthType", atthType);
						getMap.put("atthNo", atthNo);
						getMap.put("docCd", docCd);
						getMap.put("userNm", SessionUtil.getSess("userNm"));

						fileService.insertFile(getMap);
					}
				}

			}

			result.add("atthNo", atthNo);

			//업무로그처리 (일괄등록시 오류 발생)
			//paramMap.put("fileList", fileList);
			//logProcService.insertLogProc(request, paramMap);

			LOGGER.debug("fileUpload Result :: "+ request );
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("fileUpload END :: " );

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 첨부파일다운로드
	 * @throws Exception
	 */
	@RequestMapping(value="/fileDown.do")
	public void fileDown(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) HashMap paramMap) throws Exception {
		LOGGER.debug("fileDown START");

		JsonResult result = new JsonResult();

		try {
			String ContentType = "" + request.getContentType();
			String DownloadFiles = ""; // 다운로드 할 파일들
			String ParentID = "";
			String FileDownloadNo = "";
			String fileSeparator = "|";

			if (ContentType != null && !"".equals(ContentType) && ContentType.indexOf("multipart/form-data") == -1) {
				request.setCharacterEncoding("utf-8");

				String DownloadFileName = "";
				String UserAgent = request.getHeader("User-Agent");

				if (request.getParameter("file") != null) {
					DownloadFiles = "" + request.getParameter("file");
				}

				ParentID = request.getParameter("parentid");
				FileDownloadNo = request.getParameter("filedownloadno");

				if (request.getParameter(ParentID + "_downloadFileName" + FileDownloadNo) != null) {
					DownloadFileName = "" + request.getParameter(ParentID + "_downloadFileName" + FileDownloadNo)
							+ ".zip";
				} else {
					DownloadFileName = "download.zip";
				}

				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				response.setContentType("application/octet-stream");


				List<String> downItem = null;
				downItem = Arrays.asList(DownloadFiles.split("\n"));

				HashMap fileKey = new HashMap();
				String fileInfo = DownloadFiles;
				if(DownloadFiles.indexOf(fileSeparator) > 0) {
					fileInfo = DownloadFiles.substring(0, DownloadFiles.indexOf(fileSeparator));
				}
				//파일 키값 추출
				String[] atthInfos = fileInfo.split(":");

				//LOGGER.debug("fileDown START paramMap " + atthInfos.length);

				if(downItem.size() == 1){
					fileKey.put("atthType", atthInfos[0]);
					fileKey.put("atthNo", atthInfos[1]);
					fileKey.put("atthSeq", atthInfos[2]);

					LOGGER.debug("downFile START atthKey : " + fileKey);
					HashMap fileMap = fileService.selectFile(fileKey);

					String filePath = (String)fileMap.get("filePath");
					String fileMngNm = (String)fileMap.get("fileMngNm");
					String fileOrgNm = (String)fileMap.get("fileOrgNm");
					File uFile = new File(uploadBaseDir + SEPARATOR + filePath, fileMngNm);
					LOGGER.debug("downFile START filePath : " + filePath + " / " + fileMngNm);
					long fSize = uFile.length();
					//LOGGER.debug("downFile START fileSaveNm : " + fileMngNm);
					//LOGGER.debug("downFile START fileNm : " + fileOrgNm);
					//LOGGER.debug("downFile START uFile : " + uFile);
					//LOGGER.debug("downFile START fSize : " + fSize);

					if (fSize > 0) {
						String mimetype = "application/x-msdownload";

						response.setContentType(mimetype);
						setDisposition(fileOrgNm, request, response);

						BufferedInputStream in = null;
						BufferedOutputStream out = null;

						try {
							in = new BufferedInputStream(new FileInputStream(uFile));
							out = new BufferedOutputStream(response.getOutputStream());

							FileCopyUtils.copy(in, out);
							out.flush();
						} catch (Exception ex) {
							// 다음 Exception 무시 처리
							// Connection reset by peer: socket write error
							LOGGER.debug("IGNORED: {}", ex.getMessage());
						} finally {
							if (in != null) {
								try {
									in.close();
								} catch (Exception ignore) {
									LOGGER.debug("IGNORED: {}", ignore.getMessage());
								}
							}
							if (out != null) {
								try {
									out.close();
								} catch (Exception ignore) {
									LOGGER.debug("IGNORED: {}", ignore.getMessage());
								}
							}
						}
					}

				}else{ //전체 다운로드(압축)

					//파일 키값 추출
					fileKey.put("atthType", atthInfos[0]);
					fileKey.put("atthNo", atthInfos[1]);

					LOGGER.debug("downFile START atthKey : " + fileKey);
					//압축목록
					List fileList = fileService.selectFileList(fileKey);
					//LOGGER.debug("deleteFile START fileKey : " + fileList);

					ZipOutputStream zos = null;
					FileInputStream fis = null;
					BufferedInputStream bis = null;
					//BufferedOutputStream out = null;

					byte[] buf = new byte[1024];

					response.setContentType("application/zip");

					try {

						if (UserAgent.contains("MSIE") || UserAgent.contains("Trident") || UserAgent.contains("Edge")) {
							DownloadFileName = URLEncoder.encode(DownloadFileName, "utf-8").replaceAll("\\+", "%20");
							response.setHeader("Content-Disposition", "attachment;filename=" + DownloadFileName + ";");
						} else {
							DownloadFileName = new String(DownloadFileName.getBytes("utf-8"), "ISO-8859-1");
							response.setHeader("Content-Disposition",
									"attachment;filename=\"" + DownloadFileName + "\"");
						}
						ServletOutputStream sos = response.getOutputStream();
						zos = new ZipOutputStream(sos);

						for (Iterator<?> it = fileList.iterator(); it.hasNext();) {
							HashMap getMap = (HashMap) it.next();

							String filePath = (String)getMap.get("filePath");
							String fileMngNm = (String)getMap.get("fileMngNm");
							String fileOrgNm = (String)getMap.get("fileOrgNm");

							LOGGER.debug("deleteFile START fileSaveNm : " + uploadBaseDir + SEPARATOR + filePath + SEPARATOR + fileMngNm);
							fis = new FileInputStream(uploadBaseDir + SEPARATOR + filePath + SEPARATOR + fileMngNm);
							bis = new BufferedInputStream(fis, 1024);

							zos.putNextEntry(new ZipEntry(fileOrgNm));
							zos.setLevel(8);

							int length = 0;
			            	while ((length = bis.read(buf,0, 1024)) != -1){
			            		zos.write(buf, 0, length);
			            	}
			            	bis.close();
				            fis.close();
			                zos.flush();
			                zos.closeEntry();

						}
						zos.close();

					} catch (IllegalArgumentException ex){
						throw new IllegalArgumentException("DownloadFiles:"+ex.getMessage());
					} catch (NullPointerException ex){
						throw new NullPointerException("DownloadFiles:"+ex.getMessage());
					} catch (FileNotFoundException ex){
						throw new FileNotFoundException("DownloadFiles:"+ex.getMessage());
					} catch (IOException ex){
						throw new IOException("DownloadFiles:"+ex.getMessage());
					} catch (Exception ex) {
						ex.printStackTrace();
						throw ex;
					}
				}
			}

		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("fileDown END :: " );

	}
	
	/**
	 * 첨부파일다운로드 홈페이지용 (공지사항 등 일반게시판, 서식자료 - 세션 및 전체다운 필요없음)
	 * @throws Exception
	 */
	@RequestMapping(value="/fileDownBrd.do")
	public void fileDownBrd(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) HashMap paramMap) throws Exception {
		LOGGER.debug("fileDownBrd START");
		LOGGER.debug("fileDownBrd START paramMap " + paramMap);

		JsonResult result = new JsonResult();

		try {
			String ContentType = "" + request.getContentType();
			String DownloadFiles = ""; // 다운로드 할 파일들
			String ParentID = "";
			String FileDownloadNo = "";
			String fileSeparator = "|";

			if (ContentType != null && !"".equals(ContentType) && ContentType.indexOf("multipart/form-data") == -1) {
				request.setCharacterEncoding("utf-8");

				String DownloadFileName = "";
				String UserAgent = request.getHeader("User-Agent");

				if (request.getParameter("file") != null) {
					DownloadFiles = "" + request.getParameter("file");
				}

				ParentID = request.getParameter("parentid");
				FileDownloadNo = request.getParameter("filedownloadno");

				if (request.getParameter(ParentID + "_downloadFileName" + FileDownloadNo) != null) {
					DownloadFileName = "" + request.getParameter(ParentID + "_downloadFileName" + FileDownloadNo)
							+ ".zip";
				} else {
					DownloadFileName = "download.zip";
				}

				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				response.setContentType("application/octet-stream");


				List<String> downItem = null;
				downItem = Arrays.asList(DownloadFiles.split("\n"));

				HashMap fileKey = new HashMap();
				String fileInfo = DownloadFiles;
				if(DownloadFiles.indexOf(fileSeparator) > 0) {
					fileInfo = DownloadFiles.substring(0, DownloadFiles.indexOf(fileSeparator));
				}
				//파일 키값 추출
				String[] atthInfos = fileInfo.split(":");

				//LOGGER.debug("fileDown START paramMap " + atthInfos.length);

				if(downItem.size() == 1){
					fileKey.put("atthType", atthInfos[0]);
					fileKey.put("atthNo", atthInfos[1]);
					fileKey.put("atthSeq", atthInfos[2]);

					LOGGER.debug("downFile START atthKey : " + fileKey);
					HashMap fileMap = fileService.selectFile(fileKey);

					String filePath = (String)fileMap.get("filePath");
					String fileMngNm = (String)fileMap.get("fileMngNm");
					String fileOrgNm = (String)fileMap.get("fileOrgNm");
					File uFile = new File(uploadBaseDir + SEPARATOR + filePath, fileMngNm);
					LOGGER.debug("downFile START filePath : " + filePath + " / " + fileMngNm);
					long fSize = uFile.length();
					//LOGGER.debug("downFile START fileSaveNm : " + fileMngNm);
					//LOGGER.debug("downFile START fileNm : " + fileOrgNm);
					//LOGGER.debug("downFile START uFile : " + uFile);
					//LOGGER.debug("downFile START fSize : " + fSize);

					if (fSize > 0) {
						String mimetype = "application/x-msdownload";

						response.setContentType(mimetype);
						setDisposition(fileOrgNm, request, response);

						BufferedInputStream in = null;
						BufferedOutputStream out = null;

						try {
							in = new BufferedInputStream(new FileInputStream(uFile));
							out = new BufferedOutputStream(response.getOutputStream());

							FileCopyUtils.copy(in, out);
							out.flush();
						} catch (Exception ex) {
							// 다음 Exception 무시 처리
							// Connection reset by peer: socket write error
							LOGGER.debug("IGNORED: {}", ex.getMessage());
						} finally {
							if (in != null) {
								try {
									in.close();
								} catch (Exception ignore) {
									LOGGER.debug("IGNORED: {}", ignore.getMessage());
								}
							}
							if (out != null) {
								try {
									out.close();
								} catch (Exception ignore) {
									LOGGER.debug("IGNORED: {}", ignore.getMessage());
								}
							}
						}
					}

				}
			}

		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("fileDown END :: " );

	}

	/**
	 * 첨부파일삭제
	 * @throws Exception
	 */
	@RequestMapping(value="/fileDelete.do")
	public ModelAndView fileDelete(HttpServletRequest request, @RequestBody(required=false) HashMap paramMap) throws Exception {
		LOGGER.debug("fileDelete START ");

		JsonResult result = new JsonResult();

		try {
			//파일삭제
			HashMap fileMap = fileService.selectFile(paramMap); //파일정보조회
			FileUploadUtil.uploadFileDel(uploadBaseDir + SEPARATOR + fileMap.get("filePath") + SEPARATOR + fileMap.get("fileMngNm"));

			//파일삭제업데이트 처리
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			fileService.deleteFile(paramMap);

			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("fileDelete END :: " );

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 파일업로드 (게시판 이미지 업로드)
	 * @throws Exception
	 */
	@RequestMapping(value="/editImgUpload.do")
	public ModelAndView editImgUpload(HttpServletRequest request) throws Exception {
		LOGGER.debug("editImgUpload START ");

		JsonResult result = new JsonResult();

		try {

			HashMap paramMap = new HashMap();
			String atthNo = (String)request.getParameter("imgAtthNo");
			//String atthType = (String)request.getParameter("atthType");
			String atthType = "BR99";

			LOGGER.debug("editImgUpload START atthNo : " + atthNo);
			LOGGER.debug("editImgUpload START atthType : " + atthType);

			if("".equals(atthNo) || atthNo == null) {
				//첨부파일번호 추출
				paramMap.put("atthType", atthType);
				fileService.selectMaxFileNo(paramMap);
				atthNo = (String)paramMap.get("atthNo"); //첨부파일추출번호
			}

			if(atthNo != null) {
				LOGGER.debug("editImgUpload paramMap :: "+ paramMap );
				MultipartHttpServletRequest mptRequest = (MultipartHttpServletRequest) request;
				Iterator<?> fileIter = mptRequest.getFileNames();
				List fileList = new ArrayList();
				String atthSeq = "";
				//파일업로드
				while (fileIter.hasNext()) {
					MultipartFile mFile = mptRequest.getFile((String) fileIter.next());

					fileList = FileUploadUtil.uploadFiles(mFile, atthType, atthNo, "img");
				}
				//파일저장처리
				if(fileList.size() > 0) {
					for (int i = 0; i < fileList.size(); i++) {
						HashMap<String, Object> getMap = (HashMap) fileList.get(i);
						getMap.put("atthType", atthType);
						getMap.put("atthNo", atthNo);
						getMap.put("userNm", SessionUtil.getSess("userNm"));

						fileService.insertFile(getMap);
						atthSeq = (String)getMap.get("atthSeq");
					}
				}

				result.add("url", "/file/fileImgDown.do?atthType=" + atthType + "&atthNo="+atthNo+"&atthSeq="+atthSeq);

				result.add("imgAtthNo", atthNo);
			}else {

			}

			LOGGER.debug("editImgUpload Result :: "+ request );
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("editImgUpload END :: " );

		return new ModelAndView("jsonView", result);
	}

	/**
	 * 게시판 이미지 다운로드
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value="/fileImgDown.do")
	public void fileImgDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.debug("fileImgDown START ");

		JsonResult result = new JsonResult();

		try {
			HashMap setMap = new HashMap();

			String atthType = (String)request.getParameter("atthType");
			
			setMap.put("atthType", atthType);
			setMap.put("atthNo", (String)request.getParameter("atthNo"));
			setMap.put("atthSeq", (String)request.getParameter("atthSeq"));

			HashMap fileMap = fileService.selectFile(setMap);
			OutputStream out = response.getOutputStream();

			//LOGGER.debug("fileImgDown fileMap :: "+ fileMap );
			FileInputStream fin = null;

			String baseDir = "";
			if("BR03".equals(atthType)) { //갤러리게시판인경우 첫번째 첨부파일이 썸네일로 등록
				baseDir = uploadBaseDir;
			}else {
				baseDir = uploadImgDir;
			}
			if("MB05".equals(atthType)) baseDir = uploadBaseDir;	//인감팝업에서 사용

	    	String filePath = baseDir + SEPARATOR + (String)fileMap.get("filePath") + SEPARATOR + (String)fileMap.get("fileMngNm");
	    	LOGGER.debug("fileImgDown filePath :: "+ filePath );
	        int bufferSize = 2048;
	        byte[] buffer = new byte[bufferSize];
	        File uFile = new File(filePath);
	        int fSize = (int) uFile.length();

	        if (fSize > 0) {
	            try {
	               fin = new FileInputStream(uFile);
	               while (fin.read(buffer) > 0) {
	                   out.write(buffer);
	               }
	            } finally {
	               if (fin != null)
	                   fin.close();
	               if (out != null)
	                   out.flush();
	            }
	        }

			//LOGGER.debug("fileImgDown Result :: "+ request );
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
		}

		LOGGER.debug("fileImgDown END :: " );

	}


	/**  Disposition 지정하기. */
	private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser.equals("Firefox")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
			encodedFilename = "\"" + encodedFilename + "\"";
		} else {
			//throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}

	/** 브라우저 구분 얻기.  */
	private String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
			return "Trident";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}


}