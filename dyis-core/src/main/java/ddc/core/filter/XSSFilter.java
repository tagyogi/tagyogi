package ddc.core.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XSSFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
	}

}
