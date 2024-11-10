package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ChatMessageMapper;
import com.mall.backend.model.entity.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessage> {
    // 发送消息
    public boolean sendMessage(ChatMessage message) {
        message.setCreateTime(LocalDateTime.now());
        message.setIsRead(0);  // 默认未读
        return save(message);
    }

    // 获取会话的消息记录
    public List<ChatMessage> getConversationMessages(Long conversationId) {
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", conversationId)
                .orderByAsc("create_time");
        return list(queryWrapper);
    }

    // 标记消息为已读
    public boolean markMessagesAsRead(List<Long> messageIds) {
        return updateBatchById(
                messageIds.stream().map(id -> {
                    ChatMessage message = new ChatMessage();
                    message.setId(id);
                    message.setIsRead(1);
                    return message;
                }).collect(java.util.stream.Collectors.toList())
        );
    }

    // 获取未读消息数量
    public int getUnreadMessageCount(Long userId) {
        return baseMapper.countUnreadMessages(userId);
    }
}