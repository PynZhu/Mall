package com.mall.backend.controller;

import com.mall.backend.model.dto.AdminDashboardDTO;
import com.mall.backend.service.AdminStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/statistics")
public class AdminStatisticsController {
    @Autowired
    private AdminStatisticsService adminStatisticsService;

    // 获取管理后台统计数据
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardDTO> getDashboardStatistics() {
        AdminDashboardDTO dashboard = adminStatisticsService.getDashboardStatistics();
        return ResponseEntity.ok(dashboard);
    }
}