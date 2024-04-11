package ddc.web.memb;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.memb.service.MembSuspService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * membSusp Controller 클래스
 * @author 시스템 개발팀 김상민1
 * @since 2023-05-02
 * @version 1.0
 * @see 업무정지
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-02  김상민1            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/membSusp")
@Controller("membSuspController")
public class MembSuspController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MembSuspController.class); 
	
	/** membSuspService */
	@Autowired
	protected MembSuspService membSuspService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 업무정지 현황 조회 
	 */
	@RequestMapping(value="/selectMembSuspList.do") 
	public ModelAndView selectMembSuspList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembSuspList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", membSuspService.selectMembSuspList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectMembSuspList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembSuspList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 업무정지 상세 조회 
	 */
	@RequestMapping(value="/selectMembSusp.do") 
	public ModelAndView selectMembSusp(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMembSusp START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", membSuspService.selectMembSusp(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectMembSuspList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMembSusp END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 업무정지 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMembSusp.do") 
	public ModelAndView insertMembSusp(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertMembSusp START");
		JsonResult result = new JsonResult();
		
		try {
			membSuspService.insertMembSusp(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembSuspList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMembSusp END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 업무정지 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMembSusp.do") 
	public ModelAndView updateMembSusp(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMembSusp START");
		JsonResult result = new JsonResult();
		
		try {
			membSuspService.updateMembSusp(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembSuspList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMembSusp END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 업무정지 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMembSusp.do") 
	public ModelAndView deleteMembSusp(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMembSusp START");
		JsonResult result = new JsonResult();
		
		try {
			membSuspService.deleteMembSusp(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectMembSuspList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMembSusp END :: ");

		return new ModelAndView("jsonView", result);
	}
}
