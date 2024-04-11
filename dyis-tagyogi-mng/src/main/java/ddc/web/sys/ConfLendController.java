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
import ddc.biz.sys.service.ConfLendService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confLend Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 융자상품한도관리
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
@RequestMapping("/confLend")
@Controller("confLendController")
public class ConfLendController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfLendController.class); 
	
	/** confLendService */
	@Autowired
	protected ConfLendService confLendService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * confLend 현황 조회 
	 */
	@RequestMapping(value="/selectConfLendList.do") 
	public ModelAndView selectConfLendList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfLendList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confLendService.selectConfLendList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfLendList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfLendList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * confLend 상세 조회 
	 */
	@RequestMapping(value="/selectConfLend.do") 
	public ModelAndView selectConfLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfLend START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confLendService.selectConfLend(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfLendList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfLend END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * confLend 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfLend.do") 
	public ModelAndView saveConfLend(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfLend START");
		JsonResult result = new JsonResult();
		 
		try {
			confLendService.saveConfLend(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfLendList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfLend END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
