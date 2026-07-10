package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CreateProductRequest;
import com.guidev.pproduct.dto.ProductResponse;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.entity.Product;
import com.guidev.pproduct.exceptions.BusinessException;
import com.guidev.pproduct.exceptions.ResourceNotFoundException;
import com.guidev.pproduct.exceptions.ValidationException;
import com.guidev.pproduct.mapper.ProductMapper;
import com.guidev.pproduct.repository.CategoryRepository;
import com.guidev.pproduct.repository.ProductRepository;
import com.guidev.pproduct.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public Page<ProductResponse> findAll(
            String name,
            String brand,
            Boolean featured,
            Long categoryId,
            Pageable pageable
    ) {

        Specification<Product> spec =
                ProductSpecification.isActive(true);

        if (name != null && !name.isBlank()) {
            spec = spec.and(
                    ProductSpecification.hasName(name)
            );
        }

        if (brand != null && !brand.isBlank()) {
            spec = spec.and(
                    ProductSpecification.hasBrand(brand)
            );
        }

        if (featured != null) {
            spec = spec.and(
                    ProductSpecification.isFeatured(featured)
            );
        }

        if (categoryId != null) {
            spec = spec.and(
                    ProductSpecification.hasCategory(categoryId)
            );
        }

        return productRepository
                .findAll(spec, pageable)
                .map(productMapper::toResponse);
    }

    public ProductResponse findById(Long id) {

        return productMapper.toResponse(findEntityById(id));
    }

    public ProductResponse create(CreateProductRequest request) {

        if (request.getDiscountPrice() != null
                && request.getDiscountPrice().compareTo(request.getPrice()) > 0) {

            throw new ValidationException(
                    "Discount price cannot be greater than price"
            );
        }

        if (productRepository.existsBySku(request.getSku())) {
            throw new BusinessException(
                    "SKU already exists"
            );
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .description(request.getDescription())
                .brand(request.getBrand())
                .category(category)
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .stockQuantity(request.getStockQuantity())
                .minimumStock(request.getMinimumStock())
                .imageUrl(request.getImageUrl())
                .featured(request.getFeatured())
                .build();

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    public ProductResponse update(Long id, CreateProductRequest request) {

        if (request.getDiscountPrice() != null
                && request.getDiscountPrice().compareTo(request.getPrice()) > 0) {

            throw new ValidationException(
                    "Discount price cannot be greater than price"
            );
        }

        Product product = findEntityById(id);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setMinimumStock(request.getMinimumStock());
        product.setImageUrl(request.getImageUrl());
        product.setFeatured(request.getFeatured());

        Product updated = productRepository.save(product);

        return productMapper.toResponse(updated);
    }

    public void delete(Long id) {

        Product product = findEntityById(id);

        product.setActive(false);

        productRepository.save(product);
    }

    private Product findEntityById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}