package com.chat.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
@Controller
@CrossOrigin
@Order(1)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
		registry.addEndpoint("/ws").setAllowedOriginPatterns("*").setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		registry.enableSimpleBroker("/user");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/user");
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// TODO Auto-generated method stub
		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
		return false;
	}
	/*
	 * @Override public void configureClientInboundChannel(ChannelRegistration
	 * registration) { // TODO Auto-generated method stub
	 * //WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(
	 * registration); registration.interceptors(new ChannelInterceptor() {
	 * 
	 * @Override public Message<?> preSend(Message<?> message, MessageChannel
	 * channel) { // TODO Auto-generated method stub StompHeaderAccessor accessor =
	 * MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	 * if(StompCommand.CONNECT.equals(accessor.getCommand())){ try { String jwt =
	 * accessor.getHeader("Authorization").toString();
	 * System.out.println("Interceptor Authorization"+jwt); }catch(Exception e) {
	 * System.out.println("Authorization not found"); } } return
	 * ChannelInterceptor.super.preSend(message, channel); }
	 * 
	 * }); }
	 */
	
	@Bean
	public WebSocketClient webSocketClient() {
		return new SockJsClient(Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient())));
	}
	
}
