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
import ddc.biz.sys.service.ConfGuarService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confGuar Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 보증상품한도관리
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
@RequestMapping("/confGuar")
@Controller("confGuarController")
public class ConfGuarController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfGuarController.class); 
	
	/** confGuarService */
	@Autowired
	protected ConfGuarService confGuarService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	/**
	 * confGuar 현황 조회 
	 */
	@RequestMapping(value="/selectConfGuarList.do") 
	public ModelAndView selectConfGuarList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfGuarList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confGuarService.selectConfGuarList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfGuarList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfGuarList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * confGuar 상세 조회 
	 */
	@RequestMapping(value="/selectConfGuar.do") 
	public ModelAndView selectConfGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfGuar START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confGuarService.selectConfGuar(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfGuarList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfGuar END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * confGuar 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfGuar.do") 
	public ModelAndView saveConfGuar(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfGuar START");
		JsonResult result = new JsonResult();
		 
		try {
			confGuarService.saveConfGuar(paramMap);
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfGuarList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfGuar END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
