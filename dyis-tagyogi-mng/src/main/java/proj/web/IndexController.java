package proj.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.com.service.ComService;
import proj.biz.sys.service.MenuService;
import proj.core.common.CommonUtil;
import proj.core.common.JsonUtil;
import proj.core.config.ConfigProperty;
import proj.core.crypto.CryptoUtil;
import proj.core.domain.JsonResult;

import proj.core.session.SessionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
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
@RequestMapping("/index")
@Controller("indexController")
@EnableScheduling
public class IndexController extends WebBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
	
	//서버
	protected final static String active  = ConfigProperty.getString("active"); //
	//도메인
	protected final static String SERVER_URL  = ConfigProperty.getString("domain"); //
	//도메인(홈)
	protected final static String domainHome  = ConfigProperty.getString("domainHome"); //
	
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private ComService comService;


	
	/**
     * <p>index</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping(value = {"/", "/index.do"})
    public String index(HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.debug("index START!!");
    	
    	String viewPage = "/main";
    	try {
    		if("".equals(SessionUtil.getSess("userId")) || SessionUtil.getSess("userId") == null) {
	    		viewPage = "/login";
	    		
	    	}else {
				response.sendRedirect("/index/main.do");
				
			}
    	} catch (IOException e) {
    		LOGGER.debug("index Excepiton!!" + e.toString());
		}
    	
    	LOGGER.debug("index END!!");
    	
    	return viewPage;
       
    }
    
    /**
     * <p>메인</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping(value = {"/main.do"})
    public ModelAndView main(HttpServletRequest request) {
    	LOGGER.debug("main START!!");
    	
    	ModelAndView mav = new ModelAndView();
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();
    	try {
    		
    		//관리자
    		paramMap.put("sSysCd", "1");
    		
    		//오늘날짜 
    		mav.addObject("today",  CommonUtil.getCurrentDateDash());
    		
    		//메뉴정보 조회
    		paramMap.put("userId", SessionUtil.getSess("userId"));
        	mav.addObject("menuList", menuService.selectMenuList(paramMap));
        	
    		//상단 메뉴 조회
        	paramMap.put("sMenuStep", "1");
        	mav.addObject("menuTopList", menuService.selectMenuList(paramMap));
        	
        	//즐겨찾기 메뉴
        	String userId = SessionUtil.getSess("userId");
        	mav.addObject("menuFavList", comService.selectUserMenuFavList(userId));
        	
			
			
    	}catch (Exception e) {
    		LOGGER.error("main Exception : " + e.toString());
    	}
    	    	
    	mav.setViewName("/main");
    	LOGGER.debug("main END!!! ");
        return mav;
    }
    
    
    /**
     * <p>즐겨찾기 메뉴 조회</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/selectMenuFav.do")
    public ModelAndView selectMenuFav(HttpServletRequest request) {
    	
    	LOGGER.debug("selectMenuFav START");
		
		JsonResult result = new JsonResult();
		
		//즐겨찾기 메뉴
		HttpSession session = request.getSession();
    	String usrId = (String)session.getAttribute("usrId");
    	if(usrId != null) {
    		//result.add("menuFav", menuService.selectFavLst(usrId));
    	}
		
		LOGGER.debug("selectMenuFav END :: " + result );
		
		return new ModelAndView("jsonView", result);
    }
    
    /**
     * <p>view 호출 (공통)</p>
     *
     * @param request request
     * @return 화면 html
     * @throws Exception 
     */
    @RequestMapping("/pageCall.do")
    public String pageCall(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LOGGER.debug("pageCall START!!");
    	
    	String viewPage = "";
    	
    	try {
    		
	    	String menuCd = request.getParameter("menuCd");
	    	String menuPage = request.getParameter("menuPage");
	    	LOGGER.debug("값체크 menuCd : " +menuCd + " /  menuPage : " + menuPage);
	    	
	    	//기본 값 설정
	    	model.addAttribute("today", CommonUtil.getCurrentDateDash());
	    	model.addAttribute("domainHome", domainHome); //홈페이지 url
	    	
			if(!"".equals(menuCd) && menuCd != null) {
				
				//메뉴정보 조회
				HashMap<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userId", SessionUtil.getSess("userId"));
				paramMap.put("menuCd", menuCd);
				HashMap<String, Object>menuMap = menuService.selectMenu(paramMap);
			
				//메뉴 호출 부모 메뉴 index 설정
				menuMap.put("refMenuIdx", request.getParameter("refMenuIdx"));
				menuMap.put("menuIdx", request.getParameter("menuIdx"));
				model.addAttribute("menuInfo", menuMap);
		    	
				//공통코드 정보 조회
				String workCd = (String)menuMap.get("workCd");
				if(!"".equals(workCd) && workCd != null) {
					menuMap.put("codeList", Arrays.asList(workCd.split(":")));
					model.addAttribute("codeList", comService.selectCodeList(menuMap));
				}
				
				//그리드 정보 조회
				String workGrid = (String)menuMap.get("workGrid");
				LOGGER.debug("pageCall workGrid !!" + workGrid);
				
				if(!"".equals(workGrid) && workGrid != null) {
					menuMap.put("gridList", Arrays.asList(workGrid.split(":")));
					model.addAttribute("gridColList", comService.selectGridDtlList(menuMap));
				}
				
		    	//파라미터 설정
		    	@SuppressWarnings("unchecked")
				HashMap<String, Object> getParam = JsonUtil.string2Json((String)request.getParameter("paramMap"));
		    	LOGGER.debug("pageCall getParam!!" + getParam);
		    	request.setCharacterEncoding("utf-8");
				Enumeration<String> e = request.getParameterNames();
				while ( e.hasMoreElements() ){
					String name = (String) e.nextElement();
					String[] values = request.getParameterValues(name);		
					for (String value : values) {
						getParam.put(name, value);
					}   
				}
				
		    	if(!"".equals(menuMap.get("atthType")) && menuMap.get("atthType") != null) getParam.put("atthType", menuMap.get("atthType"));
		    	if(!"".equals(menuMap.get("refVal")) && menuMap.get("refVal") != null) getParam.put("refVal", menuMap.get("refVal"));
		    	
		    	getParam.put("active", active.toUpperCase());// 서버유형
		    	model.addAttribute("paramMap", getParam);
		    	viewPage = (String)menuMap.get("menuPage");
		    }else {
		    	viewPage = menuPage;
		    }
			response.setHeader("X-Frame-Options", "SAMEORIGIN"); //iframe 허용 처리
    	}catch (Exception ex) {
    		LOGGER.debug("pageCall Exception!!" + ex.toString());
    	}
		LOGGER.debug("pageCall END!!");
		
    	return viewPage;
    }
    
    
    /**
	 * 화면 초기화
	 * 
	 * 공통코드, 화면 그리드 옵션 정보 조회
	 * 
	 * @param request request
     * @return json
	 */
	@RequestMapping(value="/pageInit.do")
	public ModelAndView pageInit(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		LOGGER.debug("pageInit START ");
		
		JsonResult result = new JsonResult();
		
		try {
			//공통코드 정보 조회
			paramMap.put("codeList", Arrays.asList(((String) paramMap.get("codeList")).split(":")));
			result.put("codeList", comService.selectCodeList(paramMap));
			
			//그리드 정보 조회
			List<Object> gridList = new ArrayList<>();
			List<Object> gridNoList = Arrays.asList(((String) paramMap.get("gridNoList")).split(":"));
			LOGGER.debug("pageInit START " + gridNoList);
			for(int i = 0; i < gridNoList.size(); i++) {
				paramMap.put("gridNo", gridNoList.get(i));
				gridList.add(comService.selectGridDtlList(paramMap));
			}
			result.put("gridOptList", gridList);
			LOGGER.debug("pageInit START1 " + gridNoList);
			
		} catch (Exception e) {
			result.setResult(99, "조회중 오류가 발생했습니다.");
		}
		
		LOGGER.debug("pageInit END :: " );
		
		return new ModelAndView("jsonView", result);
	}
	
	
    /** 
     * <p>호출 오류(세션아웃)</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/accesFail.do")
    public String accesFail(ModelMap model, HttpServletRequest request) {
    	model.addAttribute("failType", request.getParameter("failType"));
    	LOGGER.debug("accesFail START!!" + model);
    	
    	return "/accesFail";
    }
    
    /**
     * <p>세션체크</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/sessChk.do")
    public ModelAndView sessChk(ModelMap model, HttpServletRequest request) {
    	//LOGGER.debug("sessChk START");
		
		JsonResult result = new JsonResult();
		
		try {
			HttpSession session = request.getSession();
			//LOGGER.debug("sessChk START session : " + session);
			//LOGGER.debug("sessChk START session : " + session.getId());
			LOGGER.debug("sessChk START session : " + session.getAttribute("userId"));
			if("".equals(session.getAttribute("userId")) || session.getAttribute("userId") == null) {
				result.add("resultCode", 1);
			}else {
				result.add("resultCode", 0);
				//result.add("usrAuth", session.getAttribute("usrAuth"));
			}
			
		} catch (Exception e) {
			result.add("resultCode", 9);
			result.add("resultMsg", "조회중 오류가 발생했습니다.");
		}
		return new ModelAndView("jsonView", result);
	}
    
    /**
     * <p>암호화(테스트)</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/cryptEnc.do")
    public ModelAndView cryptEnc(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
    	LOGGER.debug("cryptEnc START : " + paramMap);
		
		JsonResult result = new JsonResult();
		
		try {
			
			CryptoUtil.setEncrypt(domainHome, SERVER_URL);
			
		} catch (Exception e) {
			result.add("resultCode", 9);
			result.add("resultMsg", "조회중 오류가 발생했습니다.");
		}
		return new ModelAndView("jsonView", result);
	}
    
    /**
     * <p>복호화(테스트)</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/cryptDec.do")
    public ModelAndView cryptDec(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
    	LOGGER.debug("cryptDec START : " + paramMap);
		
		JsonResult result = new JsonResult();
		
		try {
			
			CryptoUtil.setDecrypt((String)paramMap.get("encResult"), (String)paramMap.get("encKey"));
			
		} catch (Exception e) {
			result.add("resultCode", 9);
			result.add("resultMsg", "조회중 오류가 발생했습니다.");
		}
		return new ModelAndView("jsonView", result);
	}
    
    /*게시판 미리보기 띄우기 */
	@RequestMapping(value = {"/preViewTest.do"})
    public String preViewTest(HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.debug("preViewTest START!!");
    	
    	String viewPage = "/preViewTest";
    	viewPage = "/preViewTest";
    	
    	LOGGER.debug("preViewTest END!!");
    	response.setHeader("X-Frame-Options", "SAMEORIGIN"); //iframe 허용 처리
    	return viewPage;
       
    }
	
	
	/**
	 * 업무해지 처리 (테스트)
	 
	@RequestMapping(value="/workRmvProc.do") 
	public ModelAndView workRmvProc(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("workRmvProc START paramMap : " + paramMap );
		JsonResult result = new JsonResult();
		 
		try {
			HashMap<String, Object>resultMap = workRmvService.workRmvProc(paramMap);
			
			LOGGER.debug("workRmvProc START resultMap : " + resultMap );
			
			//if(rtn == 0) result.setResult(0, "정상 처리 되었습니다.");
			//else result.setResult(9, "처리중 오류가 발생했습니다.");
			
		} catch (Exception e) {
			LOGGER.error("selectBatchList BaseException :: " + e.toString());
			result.setResult(9, "처리중 오류가 발생했습니다.");
		}

		LOGGER.debug("workRmvProc END :: ");

		return new ModelAndView("jsonView", result);
	}
	*/
	
    
    /**
     * <p>소켓 호출 (클라이언트 -> 서버 ) - 사용안함</p>
     *
     * @param request request
     * @return 화면 html
    
    @MessageMapping("/sockWorkList")
    public void socketMessage(HashMap<String, Object> paramMap) {
    	LOGGER.debug("socketMessage START : " + paramMap);
		JsonResult result = new JsonResult();
		
		result.add("workList", comService.selectWorkReq(paramMap)); //민원신청현황
		result.add("user", emplService.selectEmpl(paramMap)); //사용자정보 (알람여부)
		
		result.add("sockType", "work");
		socketSmt.convertAndSend("/kocfc/message", result);
		LOGGER.debug("socketMessage END : ");
	}
     */
}