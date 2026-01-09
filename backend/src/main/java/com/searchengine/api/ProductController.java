package com.searchengine.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.searchengine.api.dto.CreateProductRequest;
import com.searchengine.model.Product;
import com.searchengine.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    static {
        System.out.println("🔥 ProductController LOADED");
    }

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> bulkCreate(
            @RequestBody List<CreateProductRequest> requests) {

        int inserted = productService.bulkCreate(requests);

        return ResponseEntity.ok(
                Map.of("inserted", inserted)
        );
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                productService.getAll(page, size)
        );
    }
}
