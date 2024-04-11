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
import ddc.biz.sys.service.ConfBaseIndiService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confBaseIndi Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 기준지표관리
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
@RequestMapping("/confBaseIndi")
@Controller("confBaseIndiController")
public class ConfBaseIndiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfBaseIndiController.class); 
	
	/** confBaseIndiService */
	@Autowired
	protected ConfBaseIndiService confBaseIndiService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	/**
	 * confBaseIndi 현황 조회 
	 */
	@RequestMapping(value="/selectConfBaseIndiList.do") 
	public ModelAndView selectConfBaseIndiList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfBaseIndiList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confBaseIndiService.selectConfBaseIndiList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfBaseIndiList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfBaseIndiList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * confBaseIndi 상세 조회 
	 */
	@RequestMapping(value="/selectConfBaseIndi.do") 
	public ModelAndView selectConfBaseIndi(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfBaseIndi START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confBaseIndiService.selectConfBaseIndi(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfBaseIndiList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfBaseIndi END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * confBaseIndi 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfBaseIndi.do") 
	public ModelAndView saveConfBaseIndi(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfBaseIndi START");
		JsonResult result = new JsonResult();
		 
		try {
			confBaseIndiService.saveConfBaseIndi(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfBaseIndiList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfBaseIndi END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
