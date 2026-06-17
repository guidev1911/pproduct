package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CategoryResponse;
import com.guidev.pproduct.dto.CreateCategoryRequest;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.exceptions.ResourceNotFoundException;
import com.guidev.pproduct.mapper.CategoryMapper;
import com.guidev.pproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Page<CategoryResponse> findAll(Pageable pageable) {

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    public CategoryResponse create(CreateCategoryRequest request) {

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .slug(request.getName().toLowerCase().replace(" ", "-"))
                .build();

        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }

    private Category findEntityById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Category not found"
                        )
                );
    }

    public CategoryResponse findById(Long id) {

        return categoryMapper.toResponse(
                findEntityById(id)
        );
    }

    public CategoryResponse update(
            Long id,
            CreateCategoryRequest request
    ) {

        Category category = findEntityById(id);

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setSlug(
                request.getName()
                        .toLowerCase()
                        .replace(" ", "-")
        );

        Category updated =
                categoryRepository.save(category);

        return categoryMapper.toResponse(updated);
    }
}