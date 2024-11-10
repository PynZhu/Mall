package com.mall.backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityCheck {
    // 密码强度检查
    public static boolean isPasswordStrong(String password) {
        // 至少8个字符，包含大小写字母、数字和特殊字符
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    // 密码加密
    public static String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // 输入验证（防止SQL注入）
    public static String sanitizeInput(String input) {
        if (input == null) return null;
        // 移除特殊字符
        return input.replaceAll("[<>\"'%;()&+]", "");
    }

    // 防止重放攻击的Token生成
    public static String generateOneTimeToken() {
        return java.util.UUID.randomUUID().toString();
    }
}