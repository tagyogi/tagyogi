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
import ddc.biz.sys.service.EmplService;
import ddc.biz.sys.service.LogProcService;

import javax.servlet.http.HttpServletRequest;

/** 
 * empl Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-04-03
 * @version 1.0
 * @see 직원관리
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
@RequestMapping("/empl")
@Controller("emplController")
public class EmplController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmplController.class); 
	
	/** emplService */
	@Autowired
	protected EmplService emplService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * 직원관리 현황 조회 
	 */
	@RequestMapping(value="/selectEmplList.do") 
	public ModelAndView selectEmplList(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectEmplList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", emplService.selectEmplList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectEmplList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectEmplList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	
	/**
	 * 직원관리 상세 조회 
	 */
	@RequestMapping(value="/selectEmpl.do") 
	public ModelAndView selectEmpl(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("selectEmpl START");
		JsonResult result = new JsonResult();
		
		try {
			
			paramMap.put("userId", SessionUtil.getSess("userId"));
			result.add("data", emplService.selectEmpl(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectEmplList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectEmpl END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 직원관리 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveEmpl.do") 
	public ModelAndView saveEmpl(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("saveEmpl START");
		JsonResult result = new JsonResult();
		 
		try {
			emplService.saveEmpl(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectEmplList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveEmpl END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 직원관리 수정 처리 (단건)
	 */
	@RequestMapping(value="/updateEmpl.do") 
	public ModelAndView updateEmpl(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateEmpl START");
		JsonResult result = new JsonResult();
		
		try {
			emplService.updateEmpl(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("selectEmplList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateEmpl END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	/**
	 * 직원정보수정 - 마이페이지(개인정보수정-단건)
	 */
	@RequestMapping(value="/updateMyInfo.do") 
	public ModelAndView updateMyInfo(HttpServletRequest request, @RequestBody(required = false) HashMap paramMap) throws Exception {
		LOGGER.debug("updateMyInfo START");
		JsonResult result = new JsonResult();
		
		try {
			
			LOGGER.debug("updateMyInfo param :: " + paramMap);
			paramMap.put("userId", SessionUtil.getSess("userId"));
			emplService.updateMyInfo(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("updateMyInfo BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("updateMyInfo END :: ");

		return new ModelAndView("jsonView", result);
	}
	
	
}
