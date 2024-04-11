/**
 * 프로젝트명		: 한국콘텐츠공제시스템 
 * 개발사			: 동양정보서비스 dongyangis
 *
 * 프로그램명		: AuthenticationSuccessHandler.java
 * 프로그램설명		: 로그인 성공 핸들러
 **/
package ddc.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import ddc.core.config.ConfigProperty;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author : BSG
 * @date : 2022.06.13.
 *
 *       modifier : modify-date : description :
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private RequestCache requestCache = new HttpSessionRequestCache();

//	@Autowired
//	private MemberService memberService;

	private String targetUrlParameter;

	private boolean useReferer;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		/*
		 * // 신규암호화 정책정책으로 비밀번호 변경 if (request.getAttribute("orgPassword") != null) {
		 * String orgPassword = (String) request.getAttribute("orgPassword");
		 * memberService.modifyForPwdSw(userDetail.getSeq(), orgPassword); }
		 * 
		 * // 비밀번호 변경 180일을 초과여부 확인 int pwdUseDay =
		 * ConfigProperty.getInt("member.pwdUseDay"); if (pwdUseDay > 0) { if
		 * (DateUtil.getDiffDayCount(userDetail.getPwdChgYmd(),
		 * DateUtil.getCurrentDate()) > pwdUseDay) {
		 * request.getSession().setAttribute(Member.SSSN_PWD_USE_DAY_NM, "Y");
		 * request.getSession().setAttribute(Member.SSSN_AUTH_CHK_NM, "Y"); } }
		 * 
		 * // 임시 비밀번호 여부 if (StringUtil.equals(userDetail.getTmpPwdYn(), "Y")) {
		 * request.getSession().setAttribute(Member.SSSN_TMP_PWD_NM, "Y");
		 * request.getSession().setAttribute(Member.SSSN_AUTH_CHK_NM, "Y"); }
		 * 
		 * // 휴면 여부 확인 if (StringUtil.equals(userDetail.getSt(),
		 * MapUtil.getKey(Member.getStMap(), "휴면"))) {
		 * memberService.dormancy(userDetail.getSeq(), "N");
		 * request.getSession().setAttribute(Member.SSSN_DRMNY_CHK_NM, "Y"); }
		 * 
		 * 
		 * // 해당 사용자의 마지막 로그인 수정 memberService.modifyForFnlLgnYmd(userDetail.getSeq());
		 * 
		 * // 사용자정보 세션화 request.getSession().setAttribute("userDetails", userDetail);
		 * request.getSession().setAttribute("loginIpAddr",
		 * HttpUtil.getUserIP(request));
		 * request.getSession().removeAttribute(NonMember.SSSN_NM);
		 * request.getSession().removeAttribute(MemberSns.SSSN_NM);
		 * 
		 * String returnUrl = StringUtil.toString(request.getParameter("returnUrl"));
		 * 
		 * // 회원정보 수정일때 String modifyInformation = (String)
		 * request.getSession().getAttribute(Member.SSSN_INFORMATION_NM); if
		 * (StringUtil.isNotBlank(modifyInformation)) {
		 * request.getSession().removeAttribute(Member.SSSN_INFORMATION_NM);
		 * request.getSession().setAttribute(Member.SSSN_AUTH_CHK_NM, "Y"); String
		 * userAgent = request.getHeader("User-Agent").toUpperCase();
		 * 
		 * returnUrl = "/member/my_page/information.do";
		 * 
		 * 
		 * }
		 * 
		 * if (StringUtil.isNotBlank(returnUrl)) {
		 * redirectStrategy.sendRedirect(request, response,
		 * ConfigProperty.getString("domain") + returnUrl); } else { int
		 * intRedirectStrategy = decideRedirectStrategy(request, response);
		 * 
		 * switch (intRedirectStrategy) { case 1: useTargetUrl(request, response);
		 * break; case 2: useSessionUrl(request, response); break; case 3:
		 * useRefererUrl(request, response); break; default: useDefaultUrl(request,
		 * response); break; } }
		 */
	}

	/**
	 *      * 인증 성공후 어떤 URL로 redirect 할지를 결정한다      * 판단 기준은 targetUrlParameter 값을
	 * 읽은 URL이 존재할 경우 그것을 1순위      * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을
	 * 2순위      * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을
	 * 3순위      * 3순위 URL이 없을 경우 Default URL을 4순위로 한다      * @param request
	 *      * @param response      * @return   1 : targetUrlParameter 값을 읽은 URL
	 *      *            2 : Session에 저장되어 있는 URL      *            3 : referer 헤더에
	 * 있는 url      *            0 : default url      
	 */
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (!"".equals(targetUrlParameter)) {
			String targetUrl = request.getParameter(targetUrlParameter);
			if (StringUtils.hasText(targetUrl)) {
				result = 1;
			} else {
				if (savedRequest != null) {
					result = 2;
				} else {
					String refererUrl = request.getHeader("REFERER");
					if (useReferer && StringUtils.hasText(refererUrl)) {
						result = 3;
					} else {
						result = 0;
					}
				}
			}
			return result;
		}

		if (savedRequest != null) {
			result = 2;
			return result;
		}

		String refererUrl = request.getHeader("REFERER");
		if (useReferer && StringUtils.hasText(refererUrl)) {
			result = 3;
		} else {
			result = 0;
		}
		return result;
	}

	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}
		String targetUrl = ConfigProperty.getString("domain") + request.getParameter(targetUrlParameter);

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		String targetUrl = savedRequest.getRedirectUrl();
		targetUrl = targetUrl.replaceAll(ConfigProperty.getString("domain"), "");
		targetUrl = ConfigProperty.getString("sslDomain") + targetUrl;

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetUrl = ConfigProperty.getString("domain") + request.getHeader("REFERER");

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetUrl = ConfigProperty.getString("domain");

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

}