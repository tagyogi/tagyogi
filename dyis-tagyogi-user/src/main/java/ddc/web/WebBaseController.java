package ddc.web;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ddc.core.control.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;

/**
 * 프로젝트명	: 한국콘텐츠공제시스템 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>WebBaseController.java (사용자 베이스 Controller)</p>
 *
 * @author 		: BSG
 * @date 		: 2019. 10. 04.
 *
 * modifier 	:
 * modify-date  :
 * description  :
 */
public class WebBaseController extends BaseController {

    /**
     * <p>security 로그인 사용자 정보</p>
     *
     * @return 사용자 정보
     
    protected UserDetail getSecurityUser() {
        UserDetail userDetail = null;

        try {
            userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.toString();
        }

        return userDetail;
    }

    protected String getIp(HttpServletRequest request) {
        return HttpUtil.getUserIP(request);
    }

    protected BaseDomain setRgstInfo(BaseDomain baseDomain) {
        baseDomain.setRgpnId("");
        baseDomain.setUptpId("");

        return baseDomain;
    }
*/
}