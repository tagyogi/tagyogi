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
import ddc.biz.sys.service.CodeService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * code Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-10
 * @version 1.0
 * @see 공통코드 관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-10  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/code")
@Controller("codeController")
public class CodeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class); 
	
	/** codeService */
	@Autowired
	protected CodeService codeService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * code 현황 조회 
	 */
	@RequestMapping(value="/selectCodeList.do") 
	public ModelAndView selectCodeList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectCodeList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", codeService.selectCodeList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectCodeList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectCodeList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * code 상세 현황 조회 
	 */
	@RequestMapping(value="/selectCodeDtlList.do") 
	public ModelAndView selectCodeDtlList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectCodeDtlList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", codeService.selectCodeDtlList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectCodeDtlList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectCodeDtlList END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * code 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveCode.do") 
	public ModelAndView saveCode(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveCode START");
		JsonResult result = new JsonResult();
		
		try {
			
			codeService.saveCode(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectCodeList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveCode END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
