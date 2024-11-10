package com.mall.backend.controller;

import com.mall.backend.model.entity.ChatMessage;
import com.mall.backend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    // 发送消息
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage message) {
        boolean result = chatMessageService.sendMessage(message);
        return result ?
                ResponseEntity.ok("消息发送成功") :
                ResponseEntity.badRequest().body("消息发送失败");
    }

    // 获取会话消息
    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<List<ChatMessage>> getConversationMessages(
            @PathVariable Long conversationId
    ) {
        List<ChatMessage> messages = chatMessageService.getConversationMessages(conversationId);
        return ResponseEntity.ok(messages);
    }

    // 标记消息为已读
    @PostMapping("/read")
    public ResponseEntity<?> markMessagesAsRead(@RequestBody List<Long> messageIds) {
        boolean result = chatMessageService.markMessagesAsRead(messageIds);
        return result ?
                ResponseEntity.ok("消息标记成功") :
                ResponseEntity.badRequest().body("消息标记失败");
    }

    // 获取未读消息数量
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<Integer> getUnreadMessageCount(@PathVariable Long userId) {
        int count = chatMessageService.getUnreadMessageCount(userId);
        return ResponseEntity.ok(count);
    }
}