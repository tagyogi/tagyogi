package ddc.security.check;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>AuthorizationChecker.java (인증 체크)</p>
 *
 * @author 	    : BSG
 * @date 		: 2022.06.13.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
@Component
public class AuthorizationChecker {

    public static List<Integer> AUTHENTICATED_PATHS;

    public boolean check(HttpServletRequest request, Authentication authentication) {
        String returnUrl = request.getRequestURI();
        String queryString = request.getQueryString();
        
        queryString = (!"".equals(queryString)) ? "?" + queryString : "";
        int mnSeq = Integer.parseInt(request.getParameter("mnSeq"));

        if (this.AUTHENTICATED_PATHS != null && mnSeq > 0) {
            if (this.AUTHENTICATED_PATHS.contains(mnSeq)) {
                Object object = authentication.getPrincipal();
                if (object.equals("anonymousUser")) {
                    request.getSession().setAttribute("returnUrl", returnUrl + queryString);
                    return false;
                } else {
                    return true;
                }
            }
        }

        return true;
    }

}
