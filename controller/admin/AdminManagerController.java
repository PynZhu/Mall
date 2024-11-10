package com.mall.backend.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.AdminUser;
import com.mall.backend.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/managers")
public class AdminManagerController {
    @Autowired
    private AdminUserService adminUserService;

    // 分页查询管理员
    @GetMapping("/list")
    public ResponseEntity<Page<AdminUser>> getAdminUserList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<AdminUser> page = new Page<>(pageNum, pageSize);
        Page<AdminUser> result = adminUserService.page(page);

        // 不返回敏感信息
        result.getRecords().forEach(user -> user.setPassword(null));

        return ResponseEntity.ok(result);
    }

    // 创建管理员
    @PostMapping("/create")
    public ResponseEntity<?> createAdminUser(@RequestBody AdminUser adminUser) {
        boolean result = adminUserService.registerAdminUser(adminUser);
        return result ?
                ResponseEntity.ok("管理员创建成功") :
                ResponseEntity.badRequest().body("管理员创建失败");
    }

    // 更新管理员状态
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateAdminUserStatus(
            @PathVariable Long id,
            @RequestParam Integer status
    ) {
        AdminUser adminUser = adminUserService.getById(id);
        if (adminUser == null) {
            return ResponseEntity.notFound().build();
        }

        adminUser.setStatus(status);
        boolean result = adminUserService.updateById(adminUser);

        return result ?
                ResponseEntity.ok("管理员状态更新成功") :
                ResponseEntity.badRequest().body("管理员状态更新失败");
    }
}