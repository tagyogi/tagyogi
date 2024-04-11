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
import ddc.biz.sys.service.ConfGuarPayRateService;
import ddc.biz.sys.service.HolyService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * ConfGuarPayRate Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-31
 * @version 1.0
 * @see 선급금약정이자율 관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-31  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/confGuarPayRate")
@Controller("ConfGuarPayRateController")
public class ConfGuarPayRateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfGuarPayRateController.class); 
	
	/** ConfGuarPayRateService */
	@Autowired
	protected ConfGuarPayRateService confGuarPayRateService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * 선급금약정이자율관리 현황 조회 
	 */
	@RequestMapping(value="/selectConfGuarPayRateList.do") 
	public ModelAndView selectConfGuarPayRateList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfGuarPayRateList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confGuarPayRateService.selectConfGuarPayRateList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfGuarPayRateList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectHolyList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 선급금약정이자율 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfGuarPayRate.do") 
	public ModelAndView saveConfGuarPayRate(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfGuarPayRate START");
		JsonResult result = new JsonResult();
		 
		try {
			confGuarPayRateService.saveConfGuarPayRate(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveConfGuarPayRate BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfGuarPayRate END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
