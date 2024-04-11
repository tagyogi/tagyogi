package ddc.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import ddc.core.exception.BaseException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutSuccessHandler implements LogoutHandler {

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                response.setStatus(HttpServletResponse.SC_OK);

                String redirectPath = "/index/index.do";
                
                response.sendRedirect(redirectPath);
            } catch (BaseException | IOException e) {
                e.toString();
            }
        }
    }

}
