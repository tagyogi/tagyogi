package proj.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>ConcurrentSessionFilter.java (세션 필터)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class ConcurrentSessionFilter extends GenericFilterBean {
	  //~ Instance fields ================================================================================================

    private SessionRegistry sessionRegistry;
    private String expiredUrl;
    private LogoutHandler[] handlers = new LogoutHandler[] {new SecurityContextLogoutHandler()};
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    //~ Methods ========================================================================================================
    /**
     * @deprecated Use constructor which injects the <tt>SessionRegistry</tt>.
     */
    public ConcurrentSessionFilter() {
    }

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry) {
        this(sessionRegistry, null);
    }

    public ConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
        this.sessionRegistry = sessionRegistry;
        this.expiredUrl = expiredUrl;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(sessionRegistry, "SessionRegistry required");
        Assert.isTrue(expiredUrl == null || UrlUtils.isValidRedirectUrl(expiredUrl),
                expiredUrl + " isn't a valid redirect URL");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        if (session != null) {
            SessionInformation info = sessionRegistry.getSessionInformation(session.getId());

            if (info != null) {
                if (info.isExpired()) {
                    // Expired - abort processing
                    doLogout(request, response);

                    String targetUrl = determineExpiredUrl(request, info);

                    if (targetUrl != null) {
                    	redirectStrategy.sendRedirect(request, null, targetUrl);
                        redirectStrategy.sendRedirect(request, response, targetUrl);
                        return;
                    } else {
                        response.getWriter().print("This session has been expired (possibly due to multiple concurrent " + "logins being attempted as the same user).");
                        response.flushBuffer();
                    }

                    return;
                } else {
                	// 로그인한 계정의 IP 비교
                	String loginIpAddr = (String) request.getSession().getAttribute("loginIpAddr");
                	if (!"".equals(loginIpAddr)) {
                		//if (!loginIpAddr.equals(HttpUtil.getUserIP(request))) {
                			throw new ServletException("페이지 접근 오류입니다.");
                		//}
                	} else {
                        sessionRegistry.refreshLastRequest(info.getSessionId());
                	}
                	
                }
            }
        }

        chain.doFilter(request, response);
    }

    protected String determineExpiredUrl(HttpServletRequest request, SessionInformation info) {
        return expiredUrl;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (LogoutHandler handler : handlers) {
            handler.logout(request, response, auth);
        }
    }

    /**
     * @deprecated use constructor injection instead
     * @param expiredUrl expiredUrl
     */
    @Deprecated
    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }

    /**
     * @deprecated use constructor injection instead
     * @param sessionRegistry sessionRegistry
     */
    @Deprecated
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @SuppressWarnings("deprecation")
    public void setLogoutHandlers(LogoutHandler[] handlers) {
        Assert.notNull(handlers);
        this.handlers = (handlers == null ? null : (LogoutHandler[]) handlers.clone());
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
	
}