package ddc.web.sys;


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
import ddc.biz.sys.service.ConfRiskService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confRisk Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 리스크관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-04  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/confRisk")
@Controller("confRiskController")
public class ConfRiskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfRiskController.class); 
	
	/** confRiskService */
	@Autowired
	protected ConfRiskService confRiskService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * confRisk 현황 조회 
	 */
	@RequestMapping(value="/selectConfRiskList.do") 
	public ModelAndView selectConfRiskList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfRiskList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confRiskService.selectConfRiskList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfRiskList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfRiskList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * confRisk 상세 조회 
	 */
	@RequestMapping(value="/selectConfRiskMaxAppDt.do") 
	public ModelAndView selectConfRiskMaxAppDt(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfRiskMaxAppDt START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confRiskService.selectConfRiskMaxAppDt(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfRiskMaxAppDt BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfRiskMaxAppDt END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * confRisk 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfRisk.do") 
	public ModelAndView saveConfRisk(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfRisk START");
		JsonResult result = new JsonResult();
		 
		try {
			confRiskService.saveConfRisk(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfRiskList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfRisk END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
