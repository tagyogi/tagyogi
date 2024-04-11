package ddc.core.common;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ddc.core.config.ConfigProperty;
import ddc.core.exception.BaseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
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
			
			
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	
}
