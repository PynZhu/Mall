package com.mall.backend.controller;

import com.mall.backend.model.entity.ProductSpec;
import com.mall.backend.service.ProductSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-spec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    // 获取商品的所有规格
    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductSpec>> getProductSpecs(@PathVariable Long productId) {
        List<ProductSpec> specs = productSpecService.getProductSpecs(productId);
        return ResponseEntity.ok(specs);
    }

    // 添加单个商品规格
    @PostMapping("/add")
    public ResponseEntity<?> addProductSpec(@RequestBody ProductSpec spec) {
        boolean result = productSpecService.addProductSpec(spec);
        return result ?
                ResponseEntity.ok("规格添加成功") :
                ResponseEntity.badRequest().body("规格添加失败");
    }

    // 批量添加商品规格
    @PostMapping("/add-batch")
    public ResponseEntity<?> addProductSpecs(@RequestBody List<ProductSpec> specs) {
        boolean result = productSpecService.addProductSpecs(specs);
        return result ?
                ResponseEntity.ok("规格批量添加成功") :
                ResponseEntity.badRequest().body("规格批量添加失败");
    }

    // 更新规格库存
    @PutMapping("/update-stock/{specId}")
    public ResponseEntity<?> updateSpecStock(
            @PathVariable Long specId,
            @RequestParam Integer stock
    ) {
        boolean result = productSpecService.updateSpecStock(specId, stock);
        return result ?
                ResponseEntity.ok("库存更新成功") :
                ResponseEntity.badRequest().body("库存更新失败");
    }

    // 检查规格库存
    @GetMapping("/check-stock/{specId}")
    public ResponseEntity<Boolean> checkSpecStock(@PathVariable Long specId) {
        boolean hasStock = productSpecService.checkSpecStock(specId);
        return ResponseEntity.ok(hasStock);
    }
}