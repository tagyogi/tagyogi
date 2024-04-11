package ddc.web.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ddc.biz.sys.service.LogProcService;
import ddc.biz.sys.service.MenuService;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.web.WebBaseController;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MenuController.java (메뉴관리 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  : 
 * description  : 메뉴현황 관리
 */
@RequestMapping("/menu")
@Controller("menuController")
public class MenuController extends WebBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;

	/** logProcService 업무처리로그 */
	@Autowired
	private LogProcService logProcService;
	
	/**
	 * 메뉴 현황 조회
	 */
	@RequestMapping(value="/selectMenuList.do")
	public ModelAndView selectMenuList(HttpServletRequest request, @RequestBody(required=false) HashMap paramMap) {
		LOGGER.debug("selectMenuList START");
		
		JsonResult result = new JsonResult();
		
		try {
			result.add("data", menuService.selectMenuList(paramMap));	// Object data
			
		} catch (Exception e) {
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		LOGGER.debug("selectMenuList END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 메뉴 현황 저장
	 */
	@RequestMapping(value="/saveMenu.do")  
	public ModelAndView saveMenu(HttpServletRequest request, @RequestBody(required=false) HashMap paramMap) {
		LOGGER.debug("saveMenu START paramMap " + paramMap);
		
		JsonResult result = new JsonResult();
		
		try {
			//저장처리
			menuService.saveMenu(paramMap);
			
			//업무로그처리
			logProcService.insertLogProc(request, paramMap);
		} catch (Exception e) {
			LOGGER.error("saveMenu BaseException :: " +  e.toString() );
			result.setResult(9, "처리중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}
		
		LOGGER.debug("saveMenu END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	

}