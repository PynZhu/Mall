package com.mall.backend.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.User;
import com.mall.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    // 分页查询用户
    @GetMapping("/list")
    public ResponseEntity<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize));
        // 不返回敏感信息
        userPage.getRecords().forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(userPage);
    }

    // 禁用/启用用户
    @PostMapping("/status/{userId}")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Integer status
    ) {
        User user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setStatus(status);
        boolean result = userService.updateById(user);

        return result ?
                ResponseEntity.ok("用户状态更新成功") :
                ResponseEntity.badRequest().body("用户状态更新失败");
    }
}
