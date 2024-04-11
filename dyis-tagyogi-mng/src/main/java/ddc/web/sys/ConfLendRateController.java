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
import ddc.biz.sys.service.ConfLendRateService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confLendRate Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 융자상품이율관리
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
@RequestMapping("/confLendRate")
@Controller("confLendRateController")
public class ConfLendRateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfLendRateController.class); 
	
	/** confLendRateService */
	@Autowired
	protected ConfLendRateService confLendRateService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * confLendRate 현황 조회 
	 */
	@RequestMapping(value="/selectConfLendRateList.do") 
	public ModelAndView selectConfLendRateList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfLendRateList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confLendRateService.selectConfLendRateList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfLendRateList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfLendRateList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * confLendRate 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfLendRate.do") 
	public ModelAndView saveConfLendRate(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfLendRate START");
		JsonResult result = new JsonResult();
		 
		try {
			confLendRateService.saveConfLendRate(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfLendRateList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfLendRate END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
