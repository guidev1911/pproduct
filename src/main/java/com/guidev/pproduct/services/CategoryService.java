package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CreateCategoryRequest;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category create(CreateCategoryRequest request) {

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .slug(
                        request.getName()
                                .toLowerCase()
                                .replace(" ", "-")
                )
                .build();

        return categoryRepository.save(category);
    }
}