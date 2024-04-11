package ddc.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ddc.biz.com.service.ComService;
import ddc.biz.login.service.LoginService;
import ddc.biz.sys.service.LogProcService;
import ddc.core.common.CommonUtil;
import ddc.core.config.ConfigProperty;
import ddc.core.crypto.CryptoUtil;
import ddc.core.domain.JsonResult;
import ddc.core.exception.BaseException;
import ddc.core.session.SessionUtil;
import ddc.web.WebBaseController;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * description  : 로그인 처리
 */
@RequestMapping("/login")
@Controller("loginController")
public class LoginController extends WebBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	private final static String active = ConfigProperty.getString("active"); //서버구분
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ComService comService;

	/** logProcService */
	@Autowired
	protected LogProcService logProcService;
	/**
	 * 로그인 페이지
	 */
	@RequestMapping(value="/login.do")
	public ModelAndView login(HttpServletRequest request) {
		LOGGER.debug("login START ");
		ModelAndView mav = new ModelAndView();
		
		if(!"local".equals(active)) {
			LOGGER.debug(request.getRemoteAddr());
			HashMap setMap = new HashMap();
			setMap.put("cdType", "SY01");
			setMap.put("cdNm", request.getRemoteAddr());
			HashMap codeDtl = comService.selectCodeDtl(setMap);
			
			
			if(codeDtl == null) {
				mav.setViewName("/com/error/errorAcces");
			}
		}		
		LOGGER.debug("login END mav:: " + mav);
		
		return mav;
	}
	
	
	/**
	 * 로그인 처리
	 * @throws SocketException 
	 */
	@RequestMapping(value="/loginProc.do")
	public ModelAndView loginProc(HttpServletRequest request, @RequestBody(required=false) HashMap paramMap) throws SocketException {
		LOGGER.info("loginProc START " + paramMap);
		JsonResult result = new JsonResult();
		SessionUtil sessUtil = new SessionUtil();
		try {
			
			String getIp = request.getHeader("X-Forwarded-For");
			if(getIp == null) getIp = request.getRemoteAddr();
			
			LOGGER.error("loginProc getIp :: " + getIp);
			
			//담당자 정보 조회
			HashMap getEmpMap = new HashMap(loginService.selectCmEmployee(paramMap));
			
			if(getEmpMap == null) {
				result.setResult(9, "사용자 정보가 존재하지 않습니다.[ERROR-1]");
			}else {
				String userPwEnc = CryptoUtil.setHashEnc((String)paramMap.get("userId")+(String)paramMap.get("userPw"));
				
				if(!userPwEnc.equals((String)getEmpMap.get("userPw"))) {
					result.setResult(9, "사용자 정보가 존재하지 않습니다.[ERROR-2]");
				}else {
					//담당자 암호키설정 (로그인용)
					getEmpMap.put("userEncVal", CryptoUtil.setEncrypt((String)paramMap.get("userId"), ""));
					
					sessUtil.setSessToMap(getEmpMap); //세션 설정
					result.setResult(0, "로그인 되었습니다.");
					
					paramMap.put("connIp", getIp); //업무처리변수
					paramMap.put("connRef", request.getRequestURI()); //업무처리변수
					paramMap.put("connMac", CommonUtil.getMacAddr(request)); //업무처리변수
					
					loginService.insertLoginLog(paramMap);
				}
					
			}
			
			
			//LOGGER.debug("loginProc START " + getCusPers);
			
		}catch(Exception e){
			LOGGER.error("loginProc Exception :: " + e.toString());
			result.setResult(99, "처리중 오류가 발생하였습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
			
		}
		LOGGER.info("loginProc END:: " + result);
		
		return new ModelAndView("jsonView", result);
	}
	
	
	/**
	 * 로그아웃
	 */
	@RequestMapping(value="/logout.do")
	public ModelAndView logout(HttpServletResponse response) {
		LOGGER.debug("logout START ");
		
		JsonResult result = new JsonResult();
		
		try {
			
			SessionUtil sessUtil = new SessionUtil();
			sessUtil.setSessInit();
			
			result.setResult(0, "로그아웃 되었습니다.");
			result.add("moveUrl", "/login/login.do");
			
			LOGGER.debug("logout END result:: "  + result);
		} catch (Exception e) {
			result.setResult(99, "오류가 발생하였습니다.");
			e.printStackTrace();
		}
		
		return new ModelAndView("jsonView", result);
	}
	
	
	
	/**
	 * 로그인 처리(관리자 전용)
	 * @throws SocketException 
	 */
	@RequestMapping(value="/loginProcTmp.do")
	public ModelAndView loginProcTmp(HttpServletRequest request, @RequestBody(required=false) HashMap paramMap) throws SocketException {
		LOGGER.info("loginProcTmp START " + paramMap);
		JsonResult result = new JsonResult();
		SessionUtil sessUtil = new SessionUtil();
		try {
			
			String getIp = request.getHeader("X-Forwarded-For");
			if(getIp == null) getIp = request.getRemoteAddr();
			
			LOGGER.error("loginProc getIp :: " + getIp);
			
			//담당자 정보 조회
			HashMap getEmpMap = new HashMap(loginService.selectCmEmployee(paramMap));
			
			if(getEmpMap == null) {
				result.setResult(9, "사용자 정보가 존재하지 않습니다.[ERROR-1]");
			}else {
				//담당자 암호키설정 (로그인용)
				getEmpMap.put("userEncVal", CryptoUtil.setEncrypt((String)paramMap.get("userId"), ""));
				
				sessUtil.setSessToMap(getEmpMap); //세션 설정
				result.setResult(0, "로그인 되었습니다.");
				
				paramMap.put("connIp", getIp); //업무처리변수
				paramMap.put("connRef", request.getRequestURI()); //업무처리변수
				paramMap.put("connMac", CommonUtil.getMacAddr(request)); //업무처리변수
				
				loginService.insertLoginLog(paramMap);
					
			}
			
			
			//LOGGER.debug("loginProc START " + getCusPers);
			
		}catch(Exception e){
			LOGGER.error("loginProc Exception :: " + e.toString());
			result.setResult(99, "처리중 오류가 발생하였습니다.");
			logProcService.insertLogErr(request, paramMap, e.toString()); //에러처리로그
			
		}
		LOGGER.info("loginProc END:: " + result);
		
		return new ModelAndView("jsonView", result);
	}
}