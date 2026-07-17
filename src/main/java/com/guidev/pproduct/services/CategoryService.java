package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CategoryResponse;
import com.guidev.pproduct.dto.CreateCategoryRequest;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.exceptions.BusinessException;
import com.guidev.pproduct.exceptions.ResourceNotFoundException;
import com.guidev.pproduct.mapper.CategoryMapper;
import com.guidev.pproduct.repository.CategoryRepository;
import com.guidev.pproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Page<CategoryResponse> findAll(Pageable pageable) {

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    public CategoryResponse create(CreateCategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new BusinessException(
                    "Category name already exists"
            );
        }

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

        Category existing = categoryRepository
                .findByName(request.getName())
                .orElse(null);

        if (existing != null && !existing.getId().equals(id)) {
            throw new BusinessException(
                    "Category name already exists"
            );
        }

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

    public void delete(Long id) {

        Category category = findEntityById(id);

        if (productRepository.existsByCategoryId(id)) {
            throw new BusinessException(
                    "Category cannot be deleted because it has associated products"
            );
        }

        category.setActive(false);

        categoryRepository.save(category);
    }
}