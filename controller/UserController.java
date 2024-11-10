package com.mall.backend.controller;

import com.mall.backend.model.entity.User;
import com.mall.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult bindingResult) {
        // 检查参数验证结果
        if (bindingResult.hasErrors()) {
            // 收集所有错误信息
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMsg.append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.badRequest().body(errorMsg.toString());
        }

        // 执行注册逻辑
        boolean result = userService.registerUser(user);
        if (result) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.badRequest().body("用户已存在");
        }
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("登录失败");
        }
    }

    // 获取用户信息
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            // 不返回敏感信息
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新用户信息
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        boolean result = userService.updateUser(user);
        if (result) {
            return ResponseEntity.ok("更新成功");
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
}