package com.spring.realtimechatapp.config;

import com.spring.realtimechatapp.DTO.ChatMessage;
import com.spring.realtimechatapp.Enum.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;



    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttrs = headerAccessor.getSessionAttributes();
        String username = null;
        if (sessionAttrs != null) {
            Object obj = sessionAttrs.get("username");
            if (obj instanceof String) username = (String) obj;
        }

        if (username == null && headerAccessor.getUser() != null) {
            username = headerAccessor.getUser().getName();
        }

        if (username != null) {
            log.info("User disconnected: {}", username);
            ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }



}
