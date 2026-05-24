package com.guidev.pproduct.controllers;

import com.guidev.pproduct.dto.CreateProductRequest;
import com.guidev.pproduct.entity.Product;
import com.guidev.pproduct.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product create(@RequestBody @Valid CreateProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody @Valid CreateProductRequest request
    ) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}