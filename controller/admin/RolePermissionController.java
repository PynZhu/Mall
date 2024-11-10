package com.mall.backend.controller.admin;

import com.mall.backend.model.entity.AdminRole;
import com.mall.backend.model.entity.Permission;
import com.mall.backend.service.AdminRoleService;
import com.mall.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/role-permissions")
public class RolePermissionController {
    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private PermissionService permissionService;

    // 获取角色的权限
    @GetMapping("/{roleId}")
    public ResponseEntity<List<Permission>> getRolePermissions(@PathVariable Long roleId) {
        List<Permission> permissions = permissionService.getPermissionsByRoleId(roleId);
        return ResponseEntity.ok(permissions);
    }

    // 分配权限给角色
    @PostMapping("/assign")
    public ResponseEntity<?> assignPermissionsToRole(
            @RequestParam Long roleId,
            @RequestBody List<Long> permissionIds
    ) {
        boolean result = permissionService.assignPermissionsToRole(roleId, permissionIds);
        return result ?
                ResponseEntity.ok("权限分配成功") :
                ResponseEntity.badRequest().body("权限分配失败");
    }
}