package com.mall.backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("chat_conversation_member")
public class ChatConversationMember {
    private Long conversationId;
    private Long userId;
    private Integer role;
    private LocalDateTime joinTime;
}