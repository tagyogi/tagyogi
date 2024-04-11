package ddc.web.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.biz.com.service.ComService;
import ddc.biz.sys.service.MenuService;
import ddc.core.common.CommonUtil;
import ddc.core.common.JsonUtil;
import ddc.core.config.ConfigProperty;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.message.MessageProperty;
import ddc.core.session.SessionUtil;
import ddc.web.WebBaseController;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 홈페이지 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>SysController.java (인덱스 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2023.05.12
 *
 * modifier 	:
 * modify-date  : 
 * description  : 홈페이지 시스템관리 전용
 */
@RequestMapping("/sys")
@Controller("sysController")
public class SysController extends WebBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SysController.class);
	
	//도메인
	protected final static String SERVER_URL  = ConfigProperty.getString("domain"); //

	//개발자 정보
	protected final static List DEV_MACS  = ConfigProperty.getList("devUser.mac"); //
	protected final static List DEV_IDS  = ConfigProperty.getList("devUser.id"); //
	protected final static List DEV_NAMES  = ConfigProperty.getList("devUser.name"); //
	
	@Autowired
	private MenuService menuService;
	
	/**
     * <p>sys</p>
     *
     * @param request request
     * @return 화면 html
	 * @throws SocketException 
     */
    @RequestMapping(value = "/sysMain.do")
    public ModelAndView sysMain(HttpServletRequest request, HttpServletResponse response) throws SocketException {
    	LOGGER.debug("sysMain START!!");
    	
    	ModelAndView mav = new ModelAndView();
    	
    	//MAC 주소 조회
		boolean userYn = false;
		int getIndex = 0;
		String getMac = CommonUtil.getMacAddr(request);
		for(int i = 0; i < DEV_MACS.size(); i++) {
			//LOGGER.debug("dev index START : " + getMac + " / " + DEV_MACS.get(i));
			if(getMac.equals((String)DEV_MACS.get(i))) {
				getIndex = i;
				userYn = true; 
			}
		}
		
		if(userYn) {
			SessionUtil.addSess("userId", (String)DEV_IDS.get(getIndex));
			SessionUtil.addSess("userNm", (String)DEV_NAMES.get(getIndex));
			
			HashMap paramMap = new HashMap();
			//홈페이지
    		paramMap.put("sSysCd", "2");
    		
    		//메뉴정보 조회
    		mav.addObject("menuList", menuService.selectMenuList(paramMap));
        	
    		//상단 메뉴 조회
        	paramMap.put("sMenuStep", "1");
        	mav.addObject("menuTopList", menuService.selectMenuList(paramMap));
        	
			mav.setViewName("/sys/sysMain");
		}else {
			mav.setViewName("redirect:/index/accesFail.do?failType=sysFail");
		}
		
    	LOGGER.debug("sysMain END!!");
    	
    	return mav;
       
    }
    
    
}