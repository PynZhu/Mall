package com.mall.backend.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.AdminRole;
import com.mall.backend.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/roles")
public class AdminRoleController {
    @Autowired
    private AdminRoleService adminRoleService;

    // 分页查询角色
    @GetMapping("/list")
    public ResponseEntity<Page<AdminRole>> getRoleList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<AdminRole> page = new Page<>(pageNum, pageSize);
        Page<AdminRole> result = adminRoleService.page(page);
        return ResponseEntity.ok(result);
    }

    // 创建角色
    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody AdminRole role) {
        boolean result = adminRoleService.save(role);
        return result ?
                ResponseEntity.ok("角色创建成功") :
                ResponseEntity.badRequest().body("角色创建失败");
    }

    // 更新角色
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable Long id,
            @RequestBody AdminRole role
    ) {
        role.setId(id);
        boolean result = adminRoleService.updateById(role);
        return result ?
                ResponseEntity.ok("角色更新成功") :
                ResponseEntity.badRequest().body("角色更新失败");
    }
}