package com.mall.backend.websocket;

import com.mall.backend.model.entity.ChatMessage;
import com.mall.backend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // 保存消息
        chatMessageService.sendMessage(chatMessage);

        // 发送消息到指定会话
        messagingTemplate.convertAndSend(
                "/topic/public/" + chatMessage.getConversationId(),
                chatMessage
        );
    }
}