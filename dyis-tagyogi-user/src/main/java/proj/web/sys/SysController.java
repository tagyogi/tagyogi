package proj.web.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.sys.service.MenuService;
import proj.core.common.CommonUtil;
import proj.core.config.ConfigProperty;
import proj.core.session.SessionUtil;
import proj.web.WebBaseController;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 프로젝트명	: 동양정보서비스 홈페이지 
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
	protected final static List<Object> DEV_MACS  = ConfigProperty.getList("devUser.mac"); //
	protected final static List<Object> DEV_IDS  = ConfigProperty.getList("devUser.id"); //
	protected final static List<Object> DEV_NAMES  = ConfigProperty.getList("devUser.name"); //
	
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
			
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
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