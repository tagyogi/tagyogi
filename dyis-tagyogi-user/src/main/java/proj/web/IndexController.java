package proj.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.com.service.ComService;
import proj.biz.com.service.FileService;
import proj.biz.sys.service.BrdService;
import proj.biz.sys.service.LogProcService;
import proj.biz.sys.service.MenuService;
import proj.biz.sys.service.PopService;
import proj.core.common.CommonUtil;
import proj.core.common.JsonUtil;
import proj.core.common.PageUtil;
import proj.core.config.ConfigProperty;
import proj.core.domain.JsonResult;

import proj.core.session.SessionUtil;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 프로젝트명	: 동양정보서비스 홈페이지
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>IndexController.java (인덱스 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2023.06.21
 *
 * modifier 	:
 * modify-date  :
 * description  : 홈페이지
 */
@Controller("indexController")
public class IndexController extends WebBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	//도메인
	protected final static String SERVER_URL  = ConfigProperty.getString("domain"); //

	//서버(개발,운영)
	protected final static String active  = ConfigProperty.getString("active"); //

	@Autowired
	private MenuService menuService;

	@Autowired
	private ComService comService;

	@Autowired
	private BrdService brdService;

	/** fileService 파일첨부 */
	@Autowired
	private FileService fileService;


	/** popService */
	@Autowired
	protected PopService popService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;


    /**
     * <p>메인</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping(value={"/", "/main.do"})
    public ModelAndView main(HttpServletRequest request) {
    	LOGGER.debug("main START!!");

    	ModelAndView mav = new ModelAndView();
    	HashMap<String, Object> paramMap = new HashMap<String, Object>();

    	try {
    		mav.addObject("active", active);

    		//홈페이지
    		paramMap.put("sSysCd", "2");

    		//메뉴정보 조회
    		mav.addObject("menuList", menuService.selectMenuList(paramMap));

    		//상단 메뉴 조회
        	paramMap.put("sMenuStep", "1");
        	mav.addObject("menuTopList", menuService.selectMenuList(paramMap));

        	//공지사항
        	paramMap.put("brdNo", "1");

        	int totalPage = brdService.totalPage(paramMap);

			PageUtil pageUtil = new PageUtil();
			pageUtil.PageUtil(totalPage, 1, 4);
			paramMap.put("pageUtil", pageUtil);

			mav.addObject("brdList1", brdService.selectHomeBrdList(paramMap));

			//메인팝업
			paramMap.put("today", CommonUtil.getCurrentDate());//오늘날짜

			List<Object> popList = popService.selectPopList(paramMap);
			mav.addObject("popList", popList);

			HashMap<String, Object> popCont = new HashMap<String, Object>();//팝업내용 순서대로
			for(int i = 0; i < popList.size(); i++){
				//popCont.put("popCont" + i, CommonUtil.removeHtml((String)popList.get(i).get("cont")));
				//popCont.put("popCont" + i, (String)popList.get(i).get("cont"));
			}
			mav.addObject("popCont", popCont);

//			LOGGER.debug("main mav :: " + mav );

    	}catch (Exception e) {
    		LOGGER.error("main Exception : " + e.toString());
    	}

    	mav.setViewName("main");
    	LOGGER.debug("main END!!! ");
        return mav;
    }
    
    /**
     * <p>설치파일</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping(value={"/installInfoView.do"})
    public ModelAndView installInfoView(HttpServletRequest request) {
    	LOGGER.debug("installInfoView START!!" + request.getParameter("chkVals"));
    	ModelAndView mav = new ModelAndView();
    	
    	try {
    		
    		mav.addObject("chkVals", request.getParameter("chkVals"));
    		mav.addObject("active", active);

    	}catch (Exception e) {
    		LOGGER.error("installInfoView Exception : " + e.toString());
    	}

    	mav.setViewName("installInfoView");
    	LOGGER.debug("installInfoView END!!! ");
        return mav;
    }


    /**
     * <p>view 호출 (공통)</p>
     *
     * @param request request
     * @return 화면 html
     * @throws Exception
     */
    @RequestMapping(value = {"/kocfc.do"})
    public String pageCall(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false) HashMap<String, Object> paramMap) throws Exception {
    	LOGGER.debug("pageCall START!!" + paramMap);
    	String menuCd = (String)paramMap.get("menuCd");

    	String userAgent = request.getHeader("User-Agent").toUpperCase();
    	LOGGER.debug("pageCall userAgent!!" + userAgent);

    	//기본 값 설정
    	model.addAttribute("active", active); //서버유형
    	model.addAttribute("today", CommonUtil.getCurrentDateDash());

		String viewPage = "";
		if(!"".equals(menuCd) && menuCd != null) {
			//메뉴정보 조회
			HashMap<String, Object>menuMap = menuService.selectMenu(paramMap);

			//세션 메뉴 접근여부
			if("Y".equals((String)menuMap.get("sessYn"))) {
				String membNo = SessionUtil.getSess("membNo");
				if("".equals(membNo) || membNo == null) {

					if(userAgent.indexOf("MOBI") > -1) {
						paramMap.put("menuCd", "20608"); //로그인페이지 이동(모바일)
					} else {
						paramMap.put("menuCd", "20601"); //로그인페이지 이동
					}


					menuMap = menuService.selectMenu(paramMap);
				}
			}
			model.addAttribute("menuInfo", menuMap);

			LOGGER.debug("pageCall menuPage!! " + menuMap);

	    	//LOGGER.debug("pageCall menuMap!! " + menuMap);

	    	//콘텐츠인 경우 게시판 정보 조회
	    	if("C".equals(menuMap.get("menuType"))) {
	    		HashMap<String, Object>setMap = new HashMap<String, Object>();
	    		setMap.put("brdNo", "0");
	    		setMap.put("brdSeq", menuMap.get("refVal"));
	    		model.addAttribute("contInfo", brdService.selectBrd(setMap));
	    	}

	    	//공통코드 정보 조회
			String workCd = (String)menuMap.get("workCd");
			if(!"".equals(workCd) && workCd != null) {
				menuMap.put("codeList", Arrays.asList(workCd.split(":")));
				model.addAttribute("codeList", comService.selectCodeList(menuMap));
			}

			//그리드 정보 조회
			String workGrid = (String)menuMap.get("workGrid");
			if(!"".equals(workGrid) && workGrid != null) {
				menuMap.put("gridList", Arrays.asList(workGrid.split(":")));
				model.addAttribute("gridColList", comService.selectGridDtlList(menuMap));
			}

	    	//파라미터 설정
			HashMap<String, Object> getParam = JsonUtil.string2Json((String)paramMap.get("param"));
			if(!"".equals(menuMap.get("atthType")) && menuMap.get("atthType") != null) getParam.put("atthType", menuMap.get("atthType"));
	    	if(!"".equals(menuMap.get("refVal")) && menuMap.get("refVal") != null) getParam.put("refVal", menuMap.get("refVal"));
	    	if("".equals(getParam.get("sKeyword")) || getParam.get("sKeyword") == null) getParam.put("sKeyword", "");
	    	model.addAttribute("paramMap", getParam);
	    	
	    	
	    	LOGGER.debug("pageCall paramMap!!" + getParam);

	    	//화면호출
	    	viewPage = (String)menuMap.get("menuPage");
	    }else {
	    	viewPage = "/common/error/error404";
	    }
		LOGGER.debug("pageCall END!!");
		response.setHeader("X-Frame-Options", "SAMEORIGIN"); //iframe 허용 처리

    	return viewPage;
    }

    /**
     * <p>view 호출 (팝업)</p>
     *
     * @param request request
     * @return 화면 html
     */
	@RequestMapping(value = {"/index/popPageCall.do"})
    public String popPageCall(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.debug("popPageCall START!!");
    	String viewPage = "";
    	String menuPage = request.getParameter("menuPage");

    	model.addAttribute("seq", request.getParameter("seq"));

    	if(!"".equals(menuPage) && menuPage != null) {
    		viewPage = menuPage;
    	}else {

    		String menuCd = request.getParameter("menuCd");
    		if(!"".equals(menuCd) && menuCd != null) {
    			//메뉴정보 조회
    			HashMap<String, Object> paramMap = new HashMap<>();
    			paramMap.put("menuCd", menuCd);
    			HashMap<String, Object>menuMap = menuService.selectMenu(paramMap);

    			model.addAttribute("menuInfo", menuMap);

    			LOGGER.debug("popPageCall menuPage!! " + menuMap);

    	    	//LOGGER.debug("popPageCall menuMap!! " + menuMap);

    	    	//공통코드 정보 조회
    			String workCd = (String)menuMap.get("workCd");
    			if(!"".equals(workCd) && workCd != null) {
    				menuMap.put("codeList", Arrays.asList(workCd.split(":")));
    				model.addAttribute("codeList", comService.selectCodeList(menuMap));
    			}

    	    	//화면호출
    	    	viewPage = (String)menuMap.get("menuPage");
    	    }else {
    	    	viewPage = "/common/error/error404";
    	    }
	    }
    	LOGGER.debug("popPageCall END!!");
    	response.setHeader("X-Frame-Options", "SAMEORIGIN"); //iframe 허용 처리

    	return viewPage;
    }

	/**
	 * 메인 팝업 상세 조회
	 */
	@RequestMapping(value="/selectMainPop.do")
	public ModelAndView selectPop(HttpServletRequest request, @RequestBody(required = false) HashMap<String, Object> paramMap) throws Exception {
		LOGGER.debug("selectPop START");
		JsonResult result = new JsonResult();

		try {

			//상세
			HashMap<String, Object>getMap = popService.selectPop(paramMap);
			result.add("data", getMap);

			//첨부파일
			paramMap.put("atthNo", getMap.get("atthNo"));
			result.add("fileList", fileService.selectFileList(paramMap));

		} catch (Exception e) {
			LOGGER.error("selectPop BaseException :: " + e.toString());
			result.setResult(9, "조회중 오류가 발생했습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
		}

		LOGGER.debug("selectPop END :: ");

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
    @RequestMapping("/index/sessChk.do")
    public ModelAndView sessChk(ModelMap model, HttpServletRequest request) {
    	//LOGGER.debug("sessChk START");

		JsonResult result = new JsonResult();

		try {
			HttpSession session = request.getSession();
			//LOGGER.debug("sessChk START session : " + session);
			//LOGGER.debug("sessChk START session : " + session.getId());
			LOGGER.debug("sessChk START session : " + session.getAttribute("membNo"));
			if("".equals(session.getAttribute("membNo")) || session.getAttribute("membNo") == null) {
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
     * <p>공인인증서 서명 확인처리</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/index/certSingChk.do")
    public ModelAndView certSingChk( HttpServletRequest request, HttpServletResponse response, @RequestBody(required=false) HashMap<String, Object> paramMap) {
    	LOGGER.debug("certSingChk START paramMap " + paramMap);

		JsonResult result = new JsonResult();

		try {
			String certType = (String)paramMap.get("certType"); // REG 등록, LOGIN 로그인
			String idn = (String)paramMap.get("idn"); // 사업자(주민) 번호
			//String subjectNm = (String)paramMap.get("subjectNm"); // 인증서정보
			
			HashMap<String, Object>getMap = new HashMap<String, Object>();
			if("REG".equals(certType)) {
				paramMap.put("bizNo", idn);
				getMap = comService.selectMembBizNo(paramMap);
				paramMap.put("membNo", getMap.get("membNo"));
			}else {
				
			}
			
			
		} catch (Exception e) {
			LOGGER.error("certSingChk Exception : " + e.toString());
			result.add("resultCode", 9);
			result.add("resultMsg", "인증서 검증 중 오류가 발생했습니다.");
		}
		return new ModelAndView("jsonView", result);
	}

    
    /**
     * <p>세션체크</p>
     *
     * @param request request
     * @return 화면 html
     */
    @RequestMapping("/index/SystemProperties.do")
    public void SystemProperties(ModelMap model, HttpServletRequest request) {
    	LOGGER.debug("SystemProperties START");
    	Properties props = System.getProperties(); // get list of properties
    	
    	try{
    		//output = new FileOutputStream(new File(Path + "SystemProperties.txt"));
    		// Print properties using Enumeration
    		for (Enumeration<String> enum1 = (Enumeration<String>) props.propertyNames(); enum1.hasMoreElements();) {
    			String key = (String)enum1.nextElement();
    			LOGGER.debug(key + " = " + (String)(props.get(key)));
    			//output.write(new String(key + " = " + (String)(props.get(key) + "\n")).getBytes());
    			//output.flush();
    		}
    	}catch (Exception e){
    		LOGGER.debug(e.toString());
    		e.printStackTrace();
    	} 
	}
    
    
    
}