package proj.web.sys;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.sys.service.BatchService;
import proj.biz.sys.service.LogProcService;
import proj.core.domain.JsonResult;

import javax.servlet.http.HttpServletRequest;

/** 
 * batch Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-08-03
 * @version 1.0
 * @see 배치관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-08-03  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/batch")
@Controller("batchController")
public class BatchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchController.class); 
	
	/** batchService */
	@Autowired
	protected BatchService batchService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 배치관리 현황 조회 
	 */
	@RequestMapping(value="/selectBatchList.do") 
	public ModelAndView selectBatchList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectBatchList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", batchService.selectBatchList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectBatchList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBatchList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 배치관리 상세 조회 
	 */
	@RequestMapping(value="/selectBatch.do") 
	public ModelAndView selectBatch(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectBatch START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", batchService.selectBatch(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectBatchList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectBatch END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 배치관리 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveBatch.do") 
	public ModelAndView saveBatch(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveBatch START");
		JsonResult result = new JsonResult();
		 
		try {
			batchService.saveBatch(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectBatchList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveBatch END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
