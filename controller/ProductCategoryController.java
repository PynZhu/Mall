package com.mall.backend.controller;

import com.mall.backend.model.entity.ProductCategory;
import com.mall.backend.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    // 添加类目
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody ProductCategory category) {
        boolean result = productCategoryService.save(category);
        return result ?
                ResponseEntity.ok("类目添加成功") :
                ResponseEntity.badRequest().body("类目添加失败");
    }

    // 获取所有一级类目
    @GetMapping("/top-level")
    public ResponseEntity<List<ProductCategory>> getTopLevelCategories() {
        List<ProductCategory> categories = productCategoryService.getTopLevelCategories();
        return ResponseEntity.ok(categories);
    }

    // 根据父类目ID获取子类目
    @GetMapping("/children")
    public ResponseEntity<List<ProductCategory>> getChildCategories(
            @RequestParam(required = false) Long parentId
    ) {
        List<ProductCategory> categories = productCategoryService.getChildCategories(parentId);
        return ResponseEntity.ok(categories);
    }

    // 更新类目
    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody ProductCategory category) {
        boolean result = productCategoryService.updateById(category);
        return result ?
                ResponseEntity.ok("类目更新成功") :
                ResponseEntity.badRequest().body("类目更新失败");
    }

    // 删除类目
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        boolean result = productCategoryService.removeById(id);
        return result ?
                ResponseEntity.ok("类目删除成功") :
                ResponseEntity.badRequest().body("类目删除失败");
    }
}