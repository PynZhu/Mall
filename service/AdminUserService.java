package com.mall.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.backend.mapper.AdminUserMapper;
import com.mall.backend.model.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminUserService extends ServiceImpl<AdminUserMapper, AdminUser> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminUser login(String username, String password) {
        // 查询用户
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        AdminUser adminUser = getOne(queryWrapper);

        // 验证密码
        if (adminUser != null &&
                passwordEncoder.matches(password, adminUser.getPassword())) {
            // 更新登录时间
            adminUser.setLastLoginTime(LocalDateTime.now());
            updateById(adminUser);
            return adminUser;
        }
        return null;
    }

    // 注册管理员用户
    public boolean registerAdminUser(AdminUser adminUser) {
        // 密码加密
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminUser.setCreateTime(LocalDateTime.now());
        return save(adminUser);
    }
}