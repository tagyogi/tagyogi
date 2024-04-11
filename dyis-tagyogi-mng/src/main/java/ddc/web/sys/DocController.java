package ddc.web.sys;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.DocService;
import javax.servlet.http.HttpServletRequest;

/** 
 * doc Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-09-05
 * @version 1.0
 * @see 서식관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-09-05  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/doc")
@Controller("docController")
public class DocController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocController.class); 
	
	/** docService */
	@Autowired
	protected DocService docService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/** fileService */
	@Autowired
	protected FileService fileService;
	
	/**
	 * 서식관리 현황 조회 
	 */
	@RequestMapping(value="/selectDocList.do") 
	public ModelAndView selectDocList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectDocList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", docService.selectDocList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectDocList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectDocList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 서식관리 첨부파일 현황 조회 
	 */
	@RequestMapping(value="/selectDocFileList.do") 
	public ModelAndView selectDocFileList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectDocFileList START");
		JsonResult result = new JsonResult();
		
		try {
			LOGGER.debug("selectDocFileList paramMap" + paramMap);
			result.add("fileList", fileService.selectFileList(paramMap)); //파일첨부조회
			
		} catch (Exception e) {
			LOGGER.error("selectDocFileList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectDocFileList END :: " + result);

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 서식관리 등록 처리
	 */
	@RequestMapping(value="/insertDoc.do") 
	public ModelAndView insertDoc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertDoc START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			
			//첨부파일번호 할당
			fileService.selectMaxFileNo(paramMap);
			
			docService.insertDoc(paramMap, fileList);
			
			//파일처리
			if(fileList != null){
				try{
					//업무처리 후 파일처리전 참조번호세팅
					paramMap.put("refNo", paramMap.get("docNo"));
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertDoc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertDoc END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 서식관리 수정 처리
	 */
	@RequestMapping(value="/updateDoc.do") 
	public ModelAndView updateDoc(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("updateDoc START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			
			docService.updateDoc(paramMap, fileList);
			
			//파일처리
			if(fileList != null){
				try{
					//업무처리 후 파일처리전 참조번호세팅
					paramMap.put("refNo", paramMap.get("docNo"));
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}else { //단순정보수정
				fileService.updateFile(paramMap, fileList);
			}
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateDoc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateDoc END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 서식관리 삭제 처리 
	 */
	@RequestMapping(value="/deleteDoc.do") 
	public ModelAndView deleteDoc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteDoc START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			
			docService.deleteDoc(paramMap);
			
			fileService.deleteFileMaster(paramMap); //파일삭제
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteDoc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteDoc END :: ");

		return new ModelAndView("jsonView", result);
	}
}
