package com.oga.workflow.command.rest.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Classe de configuration pour la messagerie WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure le courtier de messages.
     *
     * @param config Le registre de configuration du courtier de messages.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/lesson"); // Active un courtier de messages simple pour le chemin "/lesson"
        config.setApplicationDestinationPrefixes("/app"); // Définit le préfixe du chemin de destination de l'application pour "/app"
    }

    /**
     * Enregistre les points de terminaison Stomp (Simple Text Oriented Messaging Protocol).
     *
     * @param registry Le registre des points de terminaison Stomp.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS(); // Enregistre le point de terminaison "/gs-guide-websocket" avec SockJS
    }
}
