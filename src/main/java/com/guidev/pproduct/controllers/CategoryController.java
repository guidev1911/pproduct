package com.guidev.pproduct.controllers;

import com.guidev.pproduct.dto.CategoryResponse;
import com.guidev.pproduct.dto.CreateCategoryRequest;
import com.guidev.pproduct.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(
            @RequestBody @Valid CreateCategoryRequest request
    ) {
        return categoryService.create(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(
            @PathVariable Long id
    ) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @RequestBody @Valid CreateCategoryRequest request
    ) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        categoryService.delete(id);
    }

    @PatchMapping("/{id}/restore")
    public CategoryResponse restore(
            @PathVariable Long id
    ) {
        return categoryService.restore(id);
    }
}
