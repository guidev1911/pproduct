package com.guidev.pproduct.controllers;

import com.guidev.pproduct.dto.CategoryResponse;
import com.guidev.pproduct.dto.CreateCategoryRequest;
import com.guidev.pproduct.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryResponse> findAll(
            @PageableDefault(
                    size = 10,
                    sort = "name"
            ) Pageable pageable) {

        return categoryService.findAll(pageable);
    }
    @PostMapping
    public CategoryResponse create(@RequestBody @Valid CreateCategoryRequest request) {
        return categoryService.create(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(
            @PathVariable Long id
    ) {
        return categoryService.findById(id);
    }
}
