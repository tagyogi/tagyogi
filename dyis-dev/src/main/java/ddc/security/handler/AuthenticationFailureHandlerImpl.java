/**
 * 프로젝트명		: 한국콘텐츠공제시스템 
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: AuthenticationFailureHandlerImpl.java
 * 프로그램설명		: 로그인 실패 핸들러
 **/
package ddc.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import ddc.core.config.ConfigProperty;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author 		: BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

	private String defaultFailureUrl;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		final String id = (String)request.getParameter("j_username");
		final String nm = (String)request.getAttribute("mbrNm");
		//final String ip = HttpUtil.getUserIP(request);
		String errorMsg = exception.getMessage();
		String rsn = this.getRsn(errorMsg);
		String returnUrl = request.getParameter("returnUrl");
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		String redirectPath = "/login/login.do";

		if (!"".equals(returnUrl)) {
			request.getSession().setAttribute("returnUrl", returnUrl);
		}

		response.sendRedirect(ConfigProperty.getString("domain") + redirectPath + "?login_error=" + URLEncoder.encode(rsn));
	}

	private String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	/**
	 * <p>로그인 실패 사유를 구합니다.</p>
	 * 
	 * @param errorMsg
	 * @return
	 */
	private String getRsn(String errorMsg) {
		String result = "";
		
		if ("Bad credentials".equals(errorMsg)) {
			result = "아이디 또는 비밀번호 확인 후 다시 입력해주세요.";
		} else if ("User account is locked".equals(errorMsg)) {
			result = "계정잠김";
		} else if ("User account has expired".equals(errorMsg)) {
			result = "사용기간 미유효";
		} else if ("User credentials have expired".equals(errorMsg)) {
			result = "상태 미승인";
		} else if ("User account is ip not matched".equals(errorMsg)) {
			result = "아이피 불일치";
		} else if ("User account is dormancy".equals(errorMsg)) {
			result = "휴면";
		} else {
			result = "미정의 오류";
		}
		
		return result;
	}
	
}