package com.whiteboard.kartik.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.Arrays;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Read allowed origins from ALLOWED_ORIGINS env var (comma-separated)
    @Value("${ALLOWED_ORIGINS:http://localhost:5173}")
    private String allowedOrigins;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple in-memory message broker for /topic prefix
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Parse comma-separated origins and register WebSocket endpoint
        String[] originArray = allowedOrigins.split(",");
        for (int i = 0; i < originArray.length; i++) {
            originArray[i] = originArray[i].trim();
        }
        
        registry.addEndpoint("/ws")
                // Allow all origins read from ALLOWED_ORIGINS env var for cross-origin WS handshake
                .setAllowedOriginPatterns(originArray)
                // Enable SockJS fallback with 25s heartbeat for Render free tier (15min spindown)
                .withSockJS()
                .setHeartbeatTime(25000);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        // Limit message size to 512KB to prevent memory exhaustion
        registration.setMessageSizeLimit(512 * 1024);
        // Set send time limit to 20s to prevent hangs on slow connections
        registration.setSendTimeLimit(20000);
    }
}