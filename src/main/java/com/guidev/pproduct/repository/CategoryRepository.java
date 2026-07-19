package com.guidev.pproduct.repository;

import com.guidev.pproduct.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category>  {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);

    Optional<Category> findByIdAndActiveTrue(Long id);

}