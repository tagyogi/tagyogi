package proj.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import proj.biz.com.service.ComService;
import proj.biz.login.service.LoginService;
import proj.core.crypto.CryptoUtil;
import proj.core.domain.JsonResult;

import proj.core.session.SessionUtil;
import proj.web.WebBaseController;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>MenuController.java (메뉴관리 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	: 
 * modify-date  : 
 * description  : 로그인 처리
 */
@RequestMapping("/login")
@Controller("loginController")
public class LoginController extends WebBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ComService comService;

	/**
	 * 로그인 페이지
	 */
	@RequestMapping(value="/login.do")
	public ModelAndView login(HttpServletRequest request) {
		logger.debug("login START ");
		ModelAndView mav = new ModelAndView();
		
		//접속기기 체크
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if(userAgent.indexOf("MOBI") > -1) {
			mav.setViewName("loginMobile"); // 모바일 뷰 이름으로 변경   
		}
		
		//업체 아이디
		String userId = (String)request.getParameter("userId");
		if("".equals(userId) || userId == null) {
			//mav.setViewName("/com/error/error");
		}else {
			
			//고객정보 조회
			//mav.addObject("cusInfo", cusService.selectCusInfo(cusId));
			//mav.addObject("cusId", cusId);	
		}
		
		logger.debug("login END mav:: " + mav);
		
		return mav;
	}
	
	/**
	 * 로그인 처리
	 */
	@RequestMapping(value="/loginProc.do")
	public ModelAndView loginProc(HttpServletRequest request, @RequestBody(required=false) HashMap<String, Object> paramMap) {
		logger.info("loginProc START " + paramMap);
		JsonResult result = new JsonResult();
		SessionUtil sessUtil = new SessionUtil();
		try {
			
			
			if("mobile".equals(paramMap.get("mod"))) { //홈페이지 모바일로 접속 (모바일용 아이디, 비번)

				//조합원정보
				HashMap<String, Object>memb = comService.selectMembById(paramMap);
				logger.debug("login memb:: " + memb);
				
				if(memb == null) {
					result.setResult(9, "사용자 정보가 존재하지 않습니다.[ERROR-1]");
				}else {
					String userPwEnc = CryptoUtil.setHashEnc((String)paramMap.get("userId")+(String)paramMap.get("userPw"));
					logger.debug("login userPwEnc:: " + userPwEnc);
					
					if(!userPwEnc.equals((String)memb.get("userPw"))) {
						result.setResult(9, "사용자 정보가 존재하지 않습니다.[ERROR-2]");
					}else {
						
						//조합원정보
						paramMap.put("membNo", memb.get("membNo"));
						memb = comService.selectMemb(paramMap);
						
						sessUtil.setSessToMap(memb); //세션 설정
						result.setResult(0, "로그인 되었습니다.");
						
					}
						
				}
				
			}else { // 일반 로그인 (인증서)

				//조합원정보
				HashMap<String, Object>memb = comService.selectMemb(paramMap);
				
				logger.debug("loginProc Exception :: " + memb);
				if(memb == null) {
					result.setResult(9, "조합원 정보가 존재하지 않습니다.");
				}else {
					sessUtil.setSessToMap(memb); //세션 설정
					result.setResult(0, "로그인 되었습니다.");
				}
			}
			
			//개발임시 : 조합원번호로 클릭하고 로그인시 전자인증 패스 
			/*if("adminTest".equals(paramMap.get("mod"))) {
				sessUtil.addSess("signYn","Y");
				logger.debug("loginProc adminTest :: " + sessUtil.getSess("signYn"));
			}*/
			
			//개발 테스트 용
			//sessUtil.setSessToMap(getEmpMap); //세션 설정
			//result.setResult(0, "로그인 되었습니다.");
		
			//logger.debug("loginProc START " + getCusPers);
			
			loginService.insertLoginLog(paramMap);
			
		}catch(Exception e){
			logger.error("loginProc Exception :: " + e.toString());
			
			result.setResult(99, "처리중 오류가 발생하였습니다.");
			
		}
		logger.info("loginProc END:: ");
		
		return new ModelAndView("jsonView", result);
	}
	
	
	
	
	/**
	 * 로그아웃
	 */
	@RequestMapping(value="/logout.do")
	public void logout(HttpServletResponse response) {
		
		logger.debug("logout START ");
		try {
			
			SessionUtil sessUtil = new SessionUtil();
			sessUtil.setSessInit();
			logger.debug("logout END mav:: " );
			response.sendRedirect("/main.do");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 로그인 처리
	 */
	@RequestMapping(value="/adminLoginProc.do")
	public void adminloginProc(HttpServletRequest request, HttpServletResponse response) {
		logger.info("adminloginProc START ");
		//logger.info("adminloginProc START " + request.getParameter("paramMap"));
		
		SessionUtil sessUtil = new SessionUtil();
		try {
			String paramMap = (String)request.getParameter("paramMap");
			String[] params = paramMap.split(":");
			
			if(params.length == 2) {
				HashMap<String, Object>setMap = new HashMap<String, Object>();
				setMap.put("membNo", params[1]);
				
				//담당자 정보 조회
				setMap.put("userId", CryptoUtil.setDecrypt(params[0], ""));
				HashMap<String, Object>getEmpMap = loginService.selectCmEmployee(setMap);
				
				if(getEmpMap != null) {
					//조합원정보
					HashMap<String, Object>memb = comService.selectMemb(setMap);
					
					//세션 설정
					sessUtil.setSessToMap(memb);
				}
			}
			
			//페이지리턴
			response.sendRedirect("/main.do");
		}catch(Exception e){
			logger.error("adminloginProc Exception :: " + e.toString());
			
		}
		logger.info("adminloginProc END:: ");
	}
}