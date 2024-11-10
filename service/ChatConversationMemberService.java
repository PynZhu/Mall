package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ChatConversationMemberMapper;
import com.mall.backend.model.entity.ChatConversationMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatConversationMemberService extends ServiceImpl<ChatConversationMemberMapper, ChatConversationMember> {
    public List<ChatConversationMember> getMembersByConversationId(Long conversationId) {
        return baseMapper.selectByConversationId(conversationId);
    }

    public boolean addMemberToConversation(ChatConversationMember member) {
        return save(member);
    }
}