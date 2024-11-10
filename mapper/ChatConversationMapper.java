package com.mall.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.backend.model.entity.ChatConversation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ChatConversationMapper extends BaseMapper<ChatConversation> {
    // 查询用户的所有会话
    List<ChatConversation> selectUserConversations(@Param("userId") Long userId);
}