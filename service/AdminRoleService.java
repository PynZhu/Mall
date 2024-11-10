package com.mall.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.AdminRoleMapper;
import com.mall.backend.model.entity.AdminRole;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleService extends ServiceImpl<AdminRoleMapper, AdminRole> {
    // 可以添加自定义方法
}