package com.guidev.pproduct.specification;

import com.guidev.pproduct.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    private CategorySpecification() {
    }

    public static Specification<Category> isActive(Boolean active) {
        return (root, query, cb) ->
                cb.equal(root.get("active"), active);
    }
}