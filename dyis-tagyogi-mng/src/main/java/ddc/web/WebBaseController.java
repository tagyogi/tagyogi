package ddc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import ddc.core.control.BaseController;

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
@EnableWebSocketMessageBroker
public class WebBaseController extends BaseController {

	@Autowired
	protected SimpMessagingTemplate socketSmt; //웹소켓 통신
	
	/**
	 * 소켓 메세지 호출 방식 
	 * socketSmt.convertAndSend("/kocfc/message", comService.selectIndexDb(paramMap));// indexDB 버전상향 
	 */
	
	
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