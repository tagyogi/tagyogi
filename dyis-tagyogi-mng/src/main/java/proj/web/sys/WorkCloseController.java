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
import proj.biz.sys.service.WorkCloseService;
import proj.core.domain.JsonResult;


import javax.servlet.http.HttpServletRequest;

/** 
 * workClos Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-04
 * @version 1.0
 * @see 마감관리
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
@RequestMapping("/workClose")
@Controller("workClosController")
public class WorkCloseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkCloseController.class); 
	
	/** workCloseService */
	@Autowired
	protected WorkCloseService workCloseService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * 업무마감 현황 조회 
	 */
	@RequestMapping(value="/selectWorkCloseList.do") 
	public ModelAndView selectWorkCloseList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectWorkCloseList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", workCloseService.selectWorkCloseList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectWorkCloseList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectWorkCloseList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * 업무마감 마감 처리
	 */
	@RequestMapping(value="/saveWorkClose.do") 
	public ModelAndView saveWorkClose(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveWorkClose START");
		JsonResult result = new JsonResult();
		 
		try {
			
			HashMap<String, Object>emp = workCloseService.selectEmp(paramMap);
			if(emp == null) {
				result.setResult(9, "사번정보가 존재하지 않습니다.");
			}else {
				paramMap.put("cdCompany", emp.get("cdCompany"));
				paramMap.put("cdDept", emp.get("cdDept"));
				
				workCloseService.saveWorkClose(paramMap);
			}
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveWorkClose BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveWorkClose END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 업무마감 마감 원복 처리
	 */
	@RequestMapping(value="/saveWorkCloseRevert.do") 
	public ModelAndView saveWorkCloseRevert(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveWorkCloseRevert START paramMap " + paramMap);
		JsonResult result = new JsonResult();
		 
		try {
			workCloseService.saveWorkCloseRevert(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveWorkCloseRevert BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveWorkCloseRevert END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
