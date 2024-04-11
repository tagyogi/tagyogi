package ddc.web.com;


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
import ddc.biz.com.service.MemoService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * memo Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 메모(개인)
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-04-03  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/memo")
@Controller("memoController")
public class MemoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemoController.class); 
	
	/** memoService */
	@Autowired
	protected MemoService memoService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * memo 현황 조회 
	 */
	@RequestMapping(value="/selectMemoList.do") 
	public ModelAndView selectMemoList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectMemoList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", memoService.selectMemoList(paramMap));
			
		} catch (Exception e) {
			LOGGER.debug("selectMemoList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectMemoList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * memo 등록 처리 (단건)
	 */
	@RequestMapping(value="/insertMemo.do") 
	public ModelAndView insertMemo(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("insertMemo START");
		JsonResult result = new JsonResult();
		
		try {
			memoService.insertMemo(paramMap);
			
		} catch (Exception e) {
			LOGGER.error("selectMemoList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("insertMemo END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * memo 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateMemo.do") 
	public ModelAndView updateMemo(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMemo START");
		JsonResult result = new JsonResult();
		
		try {
			memoService.updateMemo(paramMap);
			
		} catch (Exception e) {
			LOGGER.error("selectMemoList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMemo END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * memo 삭제 처리 (단건)
	 */
	@RequestMapping(value="/deleteMemo.do") 
	public ModelAndView deleteMemo(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("deleteMemo START");
		JsonResult result = new JsonResult();
		
		try {
			memoService.deleteMemo(paramMap);
			
		} catch (Exception e) {
			LOGGER.error("selectMemoList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("deleteMemo END :: ");

		return new ModelAndView("jsonView", result);
	}
}
