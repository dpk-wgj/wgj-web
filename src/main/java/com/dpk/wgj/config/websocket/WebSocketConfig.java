package com.dpk.wgj.config.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

///**
// * Created by zhoulin on 2018/7/9.
// * 说明:
// */
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Bean
//    public WebSocketHandler newTeacherHandler() {
//        return new TeacherWebSocket();
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(newTeacherHandler(),"/teacherwebsocket").setAllowedOrigins("*").addInterceptors(new HandShake());
//    }
//}
