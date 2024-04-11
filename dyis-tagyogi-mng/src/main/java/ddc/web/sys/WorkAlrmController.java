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
import ddc.core.session.SessionUtil;
import ddc.biz.sys.service.LogProcService; /* ddc.biz.sys.service.LogProcService */
import ddc.biz.sys.service.WorkAlrmService;
import javax.servlet.http.HttpServletRequest;

/** 
 * workAlrm Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-10-11
 * @version 1.0
 * @see 알람대상관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-10-11  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/workAlrm")
@Controller("workAlrmController")
public class WorkAlrmController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkAlrmController.class); 
	
	/** workAlrmService */
	@Autowired
	protected WorkAlrmService workAlrmService;
	
	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	
	/**
	 * 알람대상관리 현황 조회 
	 */
	@RequestMapping(value="/selectWorkAlrmList.do") 
	public ModelAndView selectWorkAlrmList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectWorkAlrmList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", workAlrmService.selectWorkAlrmList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectWorkAlrmList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectWorkAlrmList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 알람대상관리 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveWorkAlrm.do") 
	public ModelAndView saveWorkAlrm(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveWorkAlrm START");
		JsonResult result = new JsonResult();
		 
		try {
			workAlrmService.saveWorkAlrm(paramMap);
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveWorkAlrm BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveWorkAlrm END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 사용자별 알람확인 처리
	 */
	@RequestMapping(value="/updateWorkAlrmEmplChk.do") 
	public ModelAndView updateWorkAlrmEmplChk(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateWorkAlrmEmplChk START");
		JsonResult result = new JsonResult();
		 
		try {
			paramMap.put("userId", SessionUtil.getSess("userId"));
			workAlrmService.updateWorkAlrmEmplChk(paramMap);
		} catch (Exception e) {
			LOGGER.error("updateWorkAlrmEmplChk BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			//logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateWorkAlrmEmplChk END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
