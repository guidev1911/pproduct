package com.guidev.pproduct.specification;

import com.guidev.pproduct.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {

        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Product> hasBrand(String brand) {

        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("brand")),
                        "%" + brand.toLowerCase() + "%"
                );
    }

    public static Specification<Product> isFeatured(Boolean featured) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("featured"),
                        featured
                );
    }

    public static Specification<Product> hasCategory(Long categoryId) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("category").get("id"),
                        categoryId
                );
    }

    public static Specification<Product> isActive(Boolean active) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("active"),
                        active
                );
    }
}