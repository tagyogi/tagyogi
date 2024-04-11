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
import ddc.biz.sys.service.KocfcAccService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * kocfcAcc Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 조합계좌관리
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
@RequestMapping("/kocfcAcc")
@Controller("kocfcAccController")
public class KocfcAccController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KocfcAccController.class); 
	
	/** kocfcAccService */
	@Autowired
	protected KocfcAccService kocfcAccService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * kocfcAcc 현황 조회 
	 */
	@RequestMapping(value="/selectKocfcAccList.do") 
	public ModelAndView selectKocfcAccList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectKocfcAccList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", kocfcAccService.selectKocfcAccList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectKocfcAccList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectKocfcAccList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * kocfcAcc 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveKocfcAcc.do") 
	public ModelAndView saveKocfcAcc(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveKocfcAcc START");
		JsonResult result = new JsonResult();
		 
		try {
			kocfcAccService.saveKocfcAcc(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectKocfcAccList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveKocfcAcc END :: ");

		return new ModelAndView("jsonView", result);
	}
}
