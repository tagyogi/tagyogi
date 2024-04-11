package ddc.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import ddc.core.common.HttpHandshakeInterceptor;

/**
 * 프로젝트명	: 프로젝트명 
 * 개발사		: 동양정보서비스 dongyangis
 *
 * <p>configProperty.java (configProperty 접근 Util)</p>
 *
 * @author 	    : BSG
 * @date 		: 2023.01.03
 *
 * modifier 	: websocket 설정 
 * modify-date  :
 * description  :
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/kocfc");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/kocfc-websocket").setAllowedOrigins("http://localhost")
        .withSockJS()
        .setStreamBytesLimit(512 * 1024)
        .setHttpMessageCacheSize(1000)
        .setDisconnectDelay(30 * 1000)
        .setInterceptors(new HttpHandshakeInterceptor());
    }

}
