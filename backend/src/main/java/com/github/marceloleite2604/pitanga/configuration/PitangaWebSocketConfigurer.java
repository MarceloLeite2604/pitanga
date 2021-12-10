package com.github.marceloleite2604.pitanga.configuration;

import com.github.marceloleite2604.pitanga.handler.PitangaTextWebSocketHandler;
import com.github.marceloleite2604.pitanga.properties.WebSocketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class PitangaWebSocketConfigurer implements WebSocketConfigurer {

    private final WebSocketProperties websocketProperties;

    private final PitangaTextWebSocketHandler pitangaTextWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(pitangaTextWebSocketHandler, websocketProperties.getPaths()
                .toArray(String[]::new))
                .setAllowedOrigins(websocketProperties.getAllowedOrigins()
                        .toArray(String[]::new))
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
