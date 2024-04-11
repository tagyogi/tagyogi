package ddc.web.sys;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */

/** 
 * log Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-06-21
 * @version 1.0
 * @see 업무로그이력
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-06-21  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/logProc")
@Controller("logProcController")
public class LogProcController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogProcController.class); 
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 로그인이력 현황 조회 
	 */
	@RequestMapping(value="/selectLogLoginList.do") 
	public ModelAndView selectLogLoginList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectLogLoginList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", logProcService.selectLogLoginList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectLogLoginList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectLogLoginList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 업무로그이력 현황 조회 
	 */
	@RequestMapping(value="/selectLogProcList.do") 
	public ModelAndView selectLogProcList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectLogProcList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", logProcService.selectLogProcList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectLogProcList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectLogProcList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 배치로그이력 현황 조회 
	 */
	@RequestMapping(value="/selectLogBatchList.do") 
	public ModelAndView selectLogBatchList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectLogBatchList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", logProcService.selectLogBatchList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectLogProcList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectLogBatchList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 오류로그인력 현황 조회 
	 */
	@RequestMapping(value="/selectLogErrList.do") 
	public ModelAndView selectLogErrList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectLogErrList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", logProcService.selectLogErrList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectLogErrList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectLogErrList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
}
