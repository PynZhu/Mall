package com.mall.backend.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.Product;
import com.mall.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    // 分页查询商品
    @GetMapping("/list")
    public ResponseEntity<Page<Product>> getProductList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> productPage = productService.page(new Page<>(pageNum, pageSize));
        return ResponseEntity.ok(productPage);
    }

    // 上架/下架商品
    @PostMapping("/status/{productId}")
    public ResponseEntity<?> updateProductStatus(
            @PathVariable Long productId,
            @RequestParam Integer status
    ) {
        Product product = productService.getById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        product.setStatus(status);
        boolean result = productService.updateById(product);

        return result ?
                ResponseEntity.ok("商品状态更新成功") :
                ResponseEntity.badRequest().body("商品状态更新失败");
    }
}