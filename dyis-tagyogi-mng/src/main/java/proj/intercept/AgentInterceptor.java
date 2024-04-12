package proj.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 프로젝트명	: 동양정보서비스 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>BrowserInterceptor.java (에이전트 인터셉터)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 11. 18.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Component
public class AgentInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentInterceptor.class);
	
    //private List<String> passUrls = ArrayUtil.casting(ConfigProperty.getList("agent.passUrl"), String.class);
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String url = request.getRequestURI();
        
        LOGGER.debug("url : " + url);
        
        HttpSession session = request.getSession(true);
    	if("".equals(session.getAttribute("userId")) || session.getAttribute("userId") == null) {
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
    	//System.out.println("################################# postHandle");
    }

}
