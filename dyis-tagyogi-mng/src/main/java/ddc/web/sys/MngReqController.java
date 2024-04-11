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
import ddc.biz.com.service.FileService;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.sys.service.MngReqService;
import javax.servlet.http.HttpServletRequest;

/** 
 * mngReq Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-12-11
 * @version 1.0
 * @see 보안요청관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-12-11  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/mngReq")
@Controller("mngReqController")
public class MngReqController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MngReqController.class); 
	
	/** mngReqService */
	@Autowired
	protected MngReqService mngReqService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/** fileService */
	@Autowired
	protected FileService fileService;
	
	
	/**
	 * 보안요청관리 현황 조회 
	 */
	@RequestMapping(value="/selectMngReqList.do") 
	public ModelAndView selectMngReqList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMngReqList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", mngReqService.selectMngReqList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectMngReqList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMngReqList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 보안요청관리 상세 조회 
	 */
	@RequestMapping(value="/selectMngReq.do") 
	public ModelAndView selectMngReq(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMngReq START");
		JsonResult result = new JsonResult();
		
		try {
			HashMap getMap = mngReqService.selectMngReq(paramMap);
			result.add("data", getMap);
			
			//첨부파일
			paramMap.put("atthNo", getMap.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectMngReq BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMngReq END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 보안요청관리 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMngReq.do") 
	public ModelAndView insertMngReq(HttpServletRequest request, @RequestPart(value = "param") HashMap<String, Object> paramMap, @RequestPart(value = "file", required = false) List<MultipartFile> fileList) throws Exception {
		LOGGER.debug("insertMngReq START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));

			//첨부번호 발번 (atthNo)
			fileService.selectMaxFileNo(paramMap);
			
			mngReqService.insertMngReq(paramMap);
			
			//업무처리 후 파일처리전 참조번호세팅
			paramMap.put("refNo", paramMap.get("reqYm")+":"+paramMap.get("reqSeq"));

			//파일처리
			if(fileList != null) {
				try{
					fileService.fileUpload(paramMap, fileList);
				} catch (Exception e) {
					result.setResult(1, "정상처리되었습니다. (첨부파일오류)");
					logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
				}
			}
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertMngReq BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMngReq END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 보안요청관리 수정 처리 요청 코멘트추가
	 */
	@RequestMapping(value="/updateMngReqComent.do") 
	public ModelAndView updateMngReqComent(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMngReqComent START");
		JsonResult result = new JsonResult();
		
		try {
			mngReqService.updateMngReqComent(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateMngReqComent BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updateMngReqComent END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 보안요청관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMngReqProc.do") 
	public ModelAndView updateMngReqProc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMngReqProc START");
		JsonResult result = new JsonResult();
		
		try {
			paramMap.put("userNm", SessionUtil.getSess("userNm"));
			paramMap.put("userTelNo", SessionUtil.getSess("userTel"));
			
			mngReqService.updateMngReqProc(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateMngReqProc BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updateMngReqProc END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 보안요청관리 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMngReq.do") 
	public ModelAndView deleteMngReq(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMngReq START");
		JsonResult result = new JsonResult();
		
		try {
			mngReqService.deleteMngReq(paramMap);
			
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteMngReq BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("deleteMngReq END :: ");

		return new ModelAndView("jsonView", result);
	}
}
