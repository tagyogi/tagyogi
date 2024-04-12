package proj.security.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.Assert;



/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>UsernamePasswordAuthenticationFilter.java (시큐리티 인증 필터)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	//@Autowired
	//private MemberService memberService;

	//public void setMemberService(MemberService memberService) {
	//	this.memberService = memberService;
	//}

	/*
	 * 해당 Class는 라이센스 업체측에 라이센스가 존재하지 않을시 사용을 막기위한 용도로
	 * 확장해서 생성되었습니다.
	 * */

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	private String usernameParameter;
	private String passwordParameter;
	private boolean postOnly;
	
	public UsernamePasswordAuthenticationFilter() {
		super("/j_spring_security_check.do");
		usernameParameter = "j_username";
		passwordParameter = "j_password";
		postOnly = true;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(new StringBuilder().append("Authentication method not supported: ").append(request.getMethod()).toString());
		}

		if (username == null)
			username = "";
		if (password == null)
			password = "";

		/*
		Member member = (Member) memberService.findOneDrmnyWith(username);
		if (member != null) {
			// 이전 암호화정책 회원인경우
			if (StringUtil.equals("N", member.getPwdSwYn())) {
				request.setAttribute("nm", member.getNm());
				request.setAttribute("orgPassword", password);

				password = Base64.encodeBase64String(CommonUtil.getSHA256(password));
			} else {
				if (StringUtil.isNotBlank(password)) {
					password = memberService.findOneEncPwd(password);
				}
			}
		}
		*/

		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", TextEscapeUtils.escapeEntities(username));
		}
		setDetails(request, authRequest);
		
		return getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails((javax.servlet.http.HttpServletRequest) request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}

}