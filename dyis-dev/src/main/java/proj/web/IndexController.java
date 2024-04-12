package proj.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.core.config.ConfigProperty;
import proj.core.domain.JsonResult;
import proj.core.session.SessionUtil;
import proj.util.ComUtil;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>IndexController.java (인덱스 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  : 
 * description  :
 */
@Controller("indexController")
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	//도메인
	protected final static String SERVER_URL  = ConfigProperty.getString("domain"); //
	
	//개발자 정보
	protected final static List<Object> DEV_MACS  = ConfigProperty.getList("devUser.mac"); //
	protected final static List<Object> DEV_IDS  = ConfigProperty.getList("devUser.id"); //
	protected final static List<Object> DEV_NAMES  = ConfigProperty.getList("devUser.name"); //
	

	/**
     * <p>index</p>
     *
     * @param request request
     * @return 화면 html
	 * @throws SocketException 
	 * mac 주소 확인 후 로그인 처리 
     */
    @RequestMapping(value = {"/", "/index/index.do"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws SocketException {
    	ModelAndView mav = new ModelAndView();
		
    	logger.debug("dev index START : ");
		
		//MAC 주소 조회
		boolean userYn = false;
		int getIndex = 0;
		String getMac = ComUtil.getMacAddr(request);
		for(int i = 0; i < DEV_MACS.size(); i++) {
			logger.debug("dev index START : " + getMac + " / " + DEV_MACS.get(i));
			if(getMac.equals((String)DEV_MACS.get(i))) {
				getIndex = i;
				userYn = true;
			}
		}
		
		if(userYn) {
			SessionUtil.addSess("userId", (String)DEV_IDS.get(getIndex));
			SessionUtil.addSess("userNm", (String)DEV_NAMES.get(getIndex));
			
			mav.setViewName("redirect:/index/main.do");
		}else {
			mav.setViewName("redirect:/index/accesFail.do?failType=S");
		}
		
		logger.debug("dev index END");
		return mav;		
       
    }
    
    /**
     * <p>메인</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping(value = {"/index/main.do"})
    public ModelAndView main(HttpServletRequest request) {
    	logger.debug("main START!!");
    	
    	ModelAndView mav = new ModelAndView();
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	try {
    		//메뉴정보 조회
        	//mav.addObject("menuList", menuService.selectCmMenuList(paramMap));
        	
        	//상단 메뉴 조회
        	paramMap.put("menuStep", "1");
        	//mav.addObject("menuTopList", menuService.selectCmMenuList(paramMap));

        	//즐겨찾기 메뉴
        	//HttpSession session = request.getSession();
        	//String usrId = (String)session.getAttribute("usrId");
        	//if(usrId != null) {
        	//	mav.addObject("menuFav", menuService.selectFavLst(usrId));
        	//}
	
    	}catch (Exception e) {
    		logger.error("main Exception : " + e.toString());
    	}
    	    	
    	mav.setViewName("/main");
    	logger.debug("main END!!! ");
        return mav;
    }
    
    /**
     * <p>view 호출 (공통)</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/index/pageCall.do")
    public ModelAndView pageCall(HttpServletRequest request, HttpServletResponse response) {
    	logger.debug("pageCall START!!");
    	ModelAndView mav = new ModelAndView();
    	
    	String menuNm = request.getParameter("menuNm");
    	String menuPage = request.getParameter("menuPage");
    	logger.debug("값체크 : " +menuNm + " /" + menuPage);
    	
    	mav.setViewName(menuPage);
    	mav.addObject("menuNm", menuNm);
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN"); //iframe 허용 처리
		
    	return mav;
    }
    
    /** 
     * <p>호출 오류(세션아웃)</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/index/accesFail.do")
    public String accesFail(ModelMap model, HttpServletRequest request) {
    	model.addAttribute("failType", request.getParameter("failType"));
    	logger.debug("accesFail START!!" + model);
    	
    	return "/accesFail";
    }
    
    /**
     * <p>세션체크</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/index/sessChk.do")
    public ModelAndView sessChk(ModelMap model, HttpServletRequest request) {
    	//logger.debug("sessChk START");
		
		JsonResult result = new JsonResult();
		
		try {
			HttpSession session = request.getSession();
			//logger.debug("sessChk START session : " + session);
			//logger.debug("sessChk START session : " + session.getId());
			logger.debug("sessChk START session : " + session.getAttribute("userId"));
			if("".equals(session.getAttribute("userId")) || session.getAttribute("userId") == null) {
				result.add("resultCode", 1);
			}else {
				result.add("resultCode", 0);
		    	
			}
			
		} catch (Exception e) {
			result.add("resultCode", -99);
			result.add("resultMsg", "조회중 오류가 발생했습니다.");
		}
		return new ModelAndView("jsonView", result);
	}
    
    
}