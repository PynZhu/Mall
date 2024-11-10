package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.ChatConversationMapper;
import com.mall.backend.model.entity.ChatConversation;
import com.mall.backend.model.entity.ChatConversationMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatConversationService extends ServiceImpl<ChatConversationMapper, ChatConversation> {
    @Autowired
    private ChatConversationMemberService conversationMemberService;

    // 创建单聊会话
    @Transactional
    public ChatConversation createSingleConversation(Long userId1, Long userId2) {
        // 检查是否已存在会话
        QueryWrapper<ChatConversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 0);  // 单聊

        ChatConversation conversation = new ChatConversation();
        conversation.setType(0);  // 单聊
        conversation.setCreateTime(LocalDateTime.now());
        save(conversation);

        // 添加会话成员
        ChatConversationMember member1 = new ChatConversationMember();
        member1.setConversationId(conversation.getId());
        member1.setUserId(userId1);
        member1.setRole(0);  // 普通成员

        ChatConversationMember member2 = new ChatConversationMember();
        member2.setConversationId(conversation.getId());
        member2.setUserId(userId2);
        member2.setRole(0);  // 普通成员

        conversationMemberService.save(member1);
        conversationMemberService.save(member2);

        return conversation;
    }

    // 创建群聊会话
    @Transactional
    public ChatConversation createGroupConversation(String name, List<Long> userIds) {
        ChatConversation conversation = new ChatConversation();
        conversation.setType(1);  // 群聊
        conversation.setName(name);
        conversation.setCreateTime(LocalDateTime.now());
        save(conversation);

        // 添加会话成员
        for (int i = 0; i < userIds.size(); i++) {
            ChatConversationMember member = new ChatConversationMember();
            member.setConversationId(conversation.getId());
            member.setUserId(userIds.get(i));
            member.setRole(i == 0 ? 1 : 0);  // 第一个人为管理员
            conversationMemberService.save(member);
        }

        return conversation;
    }

    // 获取用户的所有会话
    public List<ChatConversation> getUserConversations(Long userId) {
        // 这里需要连表查询，可以使用自定义Mapper方法实现
        return baseMapper.selectUserConversations(userId);
    }
}