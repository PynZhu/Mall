package com.mall.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.Product;
import com.mall.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // 分页查询商品
    @GetMapping("/list")
    public ResponseEntity<Page<Product>> getProductList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> productPage = productService.getProductPage(pageNum, pageSize);
        return ResponseEntity.ok(productPage);
    }

    // 根据类目查询商品
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    // 获取推荐商品
    @GetMapping("/recommended")
    public ResponseEntity<List<Product>> getRecommendedProducts() {
        List<Product> products = productService.getRecommendedProducts();
        return ResponseEntity.ok(products);
    }

    // 商品搜索
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }

    // 添加商品
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        boolean result = productService.addProduct(product);
        return result ?
                ResponseEntity.ok("商品添加成功") :
                ResponseEntity.badRequest().body("商品添加失败");
    }

    // 更新商品
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        boolean result = productService.updateProductInfo(product);
        return result ?
                ResponseEntity.ok("商品更新成功") :
                ResponseEntity.badRequest().body("商品更新失败");
    }

    // 获取商品详情
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductDetail(@PathVariable Long id) {
        Product product = productService.getById(id);
        return product != null ?
                ResponseEntity.ok(product) :
                ResponseEntity.notFound().build();
    }

}