package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.ChatConversationMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ChatConversationMemberMapper extends BaseMapper<ChatConversationMember> {
    List<ChatConversationMember> selectByConversationId(@Param("conversationId") Long conversationId);
}