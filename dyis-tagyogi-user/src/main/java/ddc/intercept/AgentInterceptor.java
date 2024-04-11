package ddc.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ddc.core.config.ConfigProperty;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>BrowserInterceptor.java (에이전트 인터셉터)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 11. 18.
 *
 * modifier 	:
 * modify-date  :
 * description  : 홈페이지
 */
@Component
public class AgentInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(AgentInterceptor.class);
	
    //private List<String> passUrls = ArrayUtil.casting(ConfigProperty.getList("agent.passUrl"), String.class);
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        //if (StringUtil.isNotBlank(userAgent)) {
        //    userAgent = userAgent.toUpperCase();
        //}
        
        String url = request.getRequestURI();
        
        //if (!passUrls.contains(url)) {
        //   return false;
        //}
        
        HttpSession session = request.getSession(true);
        //System.out.println("################################# preHandle : " + url  + " membNo : " + session.getAttribute("membNo"));
    	logger.debug("preHandle session {} ", session.getAttribute("membNo"));
    	if("".equals(session.getAttribute("membNo")) || session.getAttribute("membNo") == null) {
    		response.sendRedirect("/index/accesFail.do?failType=S");
    		return false;
    	}
        
        
        return true;

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    	//System.out.println("################################# postHandle");
    	
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	//System.out.println("################################# afterCompletion");
    }

}
