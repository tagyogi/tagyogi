package proj.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;



/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>HttpUtil.java (Http Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2019. 09. 03.
 *
 * modifier 	: 
 * modify-date  :
 * description  :
 */
@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpHandshakeInterceptor.class);
	
	//로그인 한 인원 전체
	@SuppressWarnings("unused")
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
			
			if(request instanceof ServletServerHttpRequest) {
				ServletServerHttpRequest servReq = (ServletServerHttpRequest) request;
				HttpSession session = servReq.getServletRequest().getSession();
				LOGGER.debug("beforeHandshake " + (String)session.getAttribute("userId"));
				//sessions.add(wss);
				
				//LOGGER.debug("beforeHandshake " + (String)session.getAttribute("userId"));
				//LOGGER.debug("beforeHandshake " + sessions.size());
			}
			
		return true;
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		
	}
	
}
