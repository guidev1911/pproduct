package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CreateProductRequest;
import com.guidev.pproduct.dto.ProductResponse;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.entity.Product;
import com.guidev.pproduct.mapper.ProductMapper;
import com.guidev.pproduct.repository.CategoryRepository;
import com.guidev.pproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {

        return productMapper.toResponse(findEntityById(id));
    }

    public ProductResponse create(CreateProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

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

        Product product = findEntityById(id);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

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

        productRepository.delete(product);
    }

    private Product findEntityById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}