package proj.web.sys;


import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.sys.service.HolyService;
import proj.biz.sys.service.LogProcService;
import proj.core.domain.JsonResult;


import javax.servlet.http.HttpServletRequest;

/** 
 * holy Controller 클래스
 * @author 시스템 개발팀 변성균
 * @since 2023-03-31
 * @version 1.0
 * @see 공휴일관리
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2023-03-31  변성균            최초 생성
 *  
 *  
 *  
 *  </pre>
 */
@RequestMapping("/holy")
@Controller("holyController")
public class HolyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HolyController.class); 
	
	/** holyService */
	@Autowired
	protected HolyService holyService;
	
	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * holy 현황 조회 
	 */
	@RequestMapping(value="/selectHolyList.do") 
	public ModelAndView selectHolyList(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectHolyList START");
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", holyService.selectHolyList(paramMap));
			
		} catch (Exception e) {
			LOGGER.error("selectHolyList BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectHolyList END :: ");

		return new ModelAndView("jsonView", result);
	}  
	
	/**
	 * holy 저장 처리 (리스트형)
	 */
	@RequestMapping(value="/saveHoly.do") 
	public ModelAndView saveHoly(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("saveHoly START");
		JsonResult result = new JsonResult();
		 
		try {
			holyService.saveHoly(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveHoly BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("saveHoly END :: ");

		return new ModelAndView("jsonView", result);
	}
	
}
