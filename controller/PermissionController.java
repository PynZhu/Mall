package com.mall.backend.controller;

import com.mall.backend.model.entity.Permission;
import com.mall.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    // 获取用户权限
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Permission>> getUserPermissions(@PathVariable Long userId) {
        List<Permission> permissions = permissionService.getUserPermissions(userId);
        return ResponseEntity.ok(permissions);
    }

    // 添加权限
    @PostMapping("/add")
    public ResponseEntity<?> addPermission(@RequestBody Permission permission) {
        boolean result = permissionService.save(permission);
        return result ?
                ResponseEntity.ok("权限添加成功") :
                ResponseEntity.badRequest().body("权限添加失败");
    }
}