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
import ddc.biz.sys.service.ConfBaseGradeService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * confBaseGrade Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 기준등급관리
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
@RequestMapping("/confBaseGrade")
@Controller("confBaseGradeController")
public class ConfBaseGradeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfBaseGradeController.class); 
	
	/** confBaseGradeService */
	@Autowired
	protected ConfBaseGradeService confBaseGradeService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * confBaseGrade 현황 조회 
	 */
	@RequestMapping(value="/selectConfBaseGradeList.do") 
	public ModelAndView selectConfBaseGradeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfBaseGradeList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confBaseGradeService.selectConfBaseGradeList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfBaseGradeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfBaseGradeList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * confBaseGrade 상세 조회 
	 */
	@RequestMapping(value="/selectConfBaseGrade.do") 
	public ModelAndView selectConfBaseGrade(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectConfBaseGrade START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", confBaseGradeService.selectConfBaseGrade(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectConfBaseGradeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectConfBaseGrade END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * confBaseGrade 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveConfBaseGrade.do") 
	public ModelAndView saveConfBaseGrade(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveConfBaseGrade START");
		JsonResult result = new JsonResult();
		 
		try {
			confBaseGradeService.saveConfBaseGrade(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectConfBaseGradeList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveConfBaseGrade END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
