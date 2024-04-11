package ddc.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author      : BSG
 * @date        : 2019. 08. 01
 * <p>
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class AccessLogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String remoteAddr = (String)request.getRemoteAddr();
        String url = (req.getRequestURL() == null) ? "" : req.getRequestURL().toString();
        String queryString = (String)req.getQueryString();
        String refer = (String)req.getHeader("Refer");
        String agent = (String)req.getHeader("User-Agent");
        String fullUrl = url;
        fullUrl += !"".equals(queryString) ? "?" + queryString : queryString;

        StringBuilder result = new StringBuilder();
        result.append(remoteAddr).append(":").append(fullUrl).append(":").append(refer).append(":").append(agent);

        logger.info("Log Filter : " + result.toString());
        //long startDate = DateUtil.getCurrentMillionseconds();
        chain.doFilter(req, response);
        //long endDate = DateUtil.getCurrentMillionseconds();
        //logger.info("-------------------------\t API Access Time : " + (double) (endDate - startDate) / 1000 + " Second \t-------------------------");
    }

    public void destroy() {

    }
}
