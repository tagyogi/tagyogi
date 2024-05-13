package proj.web;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.core.domain.JsonResult;
import proj.service.LogProcService;  
import proj.service.SampleService;

import javax.servlet.http.HttpServletRequest;

/** 
 * sample Controller 클래스
 * @author 시스템 개발팀 DEV
 * @since 1900.01.01
 * @version 1.0
 * @see jobContent
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  1900.01.01  DEV            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/sample")
@Controller("sampleController")
public class SampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class); 
	
	/** sampleService */
	@Autowired
	protected SampleService sampleService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * jobContent 현황 조회 
	 */
	@RequestMapping(value="/selectSampleList.do") 
	public ModelAndView selectSampleList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectSampleList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", sampleService.selectSampleList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectSampleList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectSampleList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * jobContent 상세 조회 
	 */
	@RequestMapping(value="/selectSample.do") 
	public ModelAndView selectSample(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectSample START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", sampleService.selectSample(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectSample BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectSample END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * jobContent 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveSample.do") 
	public ModelAndView saveSample(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveSample START");
		JsonResult result = new JsonResult();
		 
		try {
			sampleService.saveSample(paramMap);
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveSample BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveSample END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * jobContent 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertSample.do") 
	public ModelAndView insertSample(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("insertSample START");
		JsonResult result = new JsonResult();
		
		try {
			sampleService.insertSample(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("insertSample BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertSample END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * jobContent 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateSample.do") 
	public ModelAndView updateSample(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("updateSample START");
		JsonResult result = new JsonResult();
		
		try {
			sampleService.updateSample(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateSample BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("updateSample END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * jobContent 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteSample.do") 
	public ModelAndView deleteSample(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("deleteSample START");
		JsonResult result = new JsonResult();
		
		try {
			sampleService.deleteSample(paramMap);
			
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("deleteSample BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("deleteSample END :: ");

		return new ModelAndView("jsonView", result);
	}
}