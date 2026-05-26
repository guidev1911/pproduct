package com.guidev.pproduct.services;

import com.guidev.pproduct.dto.CategorySummaryResponse;
import com.guidev.pproduct.dto.CreateProductRequest;
import com.guidev.pproduct.dto.ProductResponse;
import com.guidev.pproduct.entity.Category;
import com.guidev.pproduct.entity.Product;
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product create(CreateProductRequest request) {

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

        return productRepository.save(product);
    }

    public Product update(Long id, CreateProductRequest request) {

        Product product = findById(id);

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

        return productRepository.save(product);
    }

    public void delete(Long id) {

        Product product = findById(id);

        productRepository.delete(product);
    }
    private ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .barcode(product.getBarcode())
                .description(product.getDescription())
                .brand(product.getBrand())
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .stockQuantity(product.getStockQuantity())
                .featured(product.getFeatured())

                .category(
                        CategorySummaryResponse.builder()
                                .id(product.getCategory().getId())
                                .name(product.getCategory().getName())
                                .slug(product.getCategory().getSlug())
                                .build()
                )
                .build();
    }
}