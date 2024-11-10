package com.mall.backend.controller.admin;

import com.mall.backend.model.entity.AdminUser;
import com.mall.backend.service.AdminUserService;
import com.mall.backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        AdminUser adminUser = adminUserService.login(username, password);
        if (adminUser != null) {
            // 生成token
            String token = JwtTokenUtil.generateToken(adminUser);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("登录失败");
    }
}