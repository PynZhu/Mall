package com.mall.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.backend.model.entity.ProductComment;
import com.mall.backend.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class ProductCommentController {
    @Autowired
    private ProductCommentService productCommentService;

    // 添加商品评论
    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody ProductComment comment) {
        boolean result = productCommentService.addComment(comment);
        return result ?
                ResponseEntity.ok("评论添加成功") :
                ResponseEntity.badRequest().body("评论添加失败");
    }

    // 分页查询商品评论
    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ProductComment>> getProductComments(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<ProductComment> comments = productCommentService.getProductComments(productId, pageNum, pageSize);
        return ResponseEntity.ok(comments);
    }

    // 获取用户评论历史
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductComment>> getUserComments(@PathVariable Long userId) {
        List<ProductComment> comments = productCommentService.getUserComments(userId);
        return ResponseEntity.ok(comments);
    }
}