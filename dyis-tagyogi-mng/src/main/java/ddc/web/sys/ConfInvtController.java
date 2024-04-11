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
import ddc.biz.sys.service.LogProcService;
import ddc.biz.sys.service.ConfInvtService;
import javax.servlet.http.HttpServletRequest;

/** 
 * confInvt Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-05-22
 * @version 1.0
 * @see 출자좌수액
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-05-22  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/confInvt")
@Controller("confInvtController")
public class ConfInvtController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfInvtController.class); 
	
	/** confInvtService */
	@Autowired
	protected ConfInvtService confInvtService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 출자좌수액 현황 조회 
	 */
	@RequestMapping(value="/selectConfInvtList.do") 
	public ModelAndView selectConfInvtList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfInvtList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confInvtService.selectConfInvtList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfInvtList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfInvtList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 출자좌수액 상세 조회 (최종)
	 */
	@RequestMapping(value="/selectConfInvtMaxAppDt.do") 
	public ModelAndView selectConfInvtMaxAppDt(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfInvtMaxAppDt START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confInvtService.selectConfInvtMaxAppDt(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfInvtMaxAppDt BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfInvtMaxAppDt END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 출자좌수액 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfInvt.do") 
	public ModelAndView saveConfInvt(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfInvt START");
		JsonResult result = new JsonResult();
		 
		try {
			confInvtService.saveConfInvt(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfInvtList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfInvt END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
