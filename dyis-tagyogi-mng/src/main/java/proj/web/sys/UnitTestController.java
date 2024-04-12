package proj.web.sys;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.sys.service.LogProcService;
import proj.biz.sys.service.UnitTestService;
import proj.core.domain.JsonResult;


import javax.servlet.http.HttpServletRequest;

/** 
 * unitTest Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-08
 * @version 1.0
 * @see 검수관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-08  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/unitTest")
@Controller("unitTestController")
public class UnitTestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnitTestController.class); 
	
	/** unitTestService */
	@Autowired
	protected UnitTestService unitTestService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 검수관리 현황 조회 
	 */
	@RequestMapping(value="/selectUnitTestList.do") 
	public ModelAndView selectUnitTestList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectUnitTestList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", unitTestService.selectUnitTestList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectUnitTestList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectUnitTestList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 검수관리 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveUnitTest.do") 
	public ModelAndView saveUnitTest(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveUnitTest START");
		JsonResult result = new JsonResult();
		 
		try {
			unitTestService.saveUnitTest(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveUnitTest BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveUnitTest END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 단위테스트현황 현황 조회 
	 */
	@RequestMapping(value="/selectUnitTestCaseList.do") 
	public ModelAndView selectUnitTestCaseList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectUnitTestCaseList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", unitTestService.selectUnitTestCaseList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectUnitTestCaseList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectUnitTestCaseList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 단위테스트현황 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveUnitTestCase.do") 
	public ModelAndView saveUnitTestCase(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveUnitTestCase START");
		JsonResult result = new JsonResult();
		 
		try {
			unitTestService.saveUnitTestCase(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveUnitTestCase BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveUnitTestCase END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 단위별요청사항 현황 조회 
	 */
	@RequestMapping(value="/selectUnitTestReqList.do") 
	public ModelAndView selectUnitTestReqList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectUnitTestReqList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", unitTestService.selectUnitTestReqList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectUnitTestReqList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		

		LOGGER.debug("selectUnitTestReqList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 단위별요청사항 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveUnitTestReq.do") 
	public ModelAndView saveUnitTestReq(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveUnitTestReq START");
		JsonResult result = new JsonResult();
		 
		try {
			unitTestService.saveUnitTestReq(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveUnitTestReq BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveUnitTestReq END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
}
