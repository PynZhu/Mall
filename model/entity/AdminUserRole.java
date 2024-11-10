package com.mall.backend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

// AdminUserRole.java
@Data
@TableName("admin_user_role")
public class AdminUserRole {
    private Long adminUserId;
    private Long roleId;
}