package com.dpk.wgj.config.webSocket;

import com.dpk.wgj.config.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class HandShake implements HandshakeInterceptor {
    private static Logger logger = LoggerFactory.getLogger(TeacherWebSocket.class);
    @Bean
    public JwtTokenUtil newJwtTokenUtil() {
        return new JwtTokenUtil();
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = ((ServletServerHttpRequest) request).getServletRequest().getParameter("token");
//            int classId = Integer.parseInt(((ServletServerHttpRequest) request).getServletRequest().getParameter("classId"));
            if(token != null){
                attributes.put("token", token);
//                attributes.put("classId", classId);
            }else{
                return false;
            }
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}