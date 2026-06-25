package com.guidev.pproduct.controllers;

import com.guidev.pproduct.dto.CreateProductRequest;
import com.guidev.pproduct.dto.ProductResponse;
import com.guidev.pproduct.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductResponse> findAll(

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            String brand,

            @RequestParam(required = false)
            Boolean featured,

            @RequestParam(required = false)
            Long categoryId,

            Pageable pageable
    ) {
        return productService.findAll(
                name,
                brand,
                featured,
                categoryId,
                pageable
        );
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @RequestBody @Valid CreateProductRequest request
    ) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @RequestBody @Valid CreateProductRequest request
    ) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}