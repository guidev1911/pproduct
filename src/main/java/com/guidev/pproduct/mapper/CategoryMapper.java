package com.guidev.pproduct.mapper;

import com.guidev.pproduct.dto.CategoryResponse;
import com.guidev.pproduct.dto.CategorySummaryResponse;
import com.guidev.pproduct.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    CategorySummaryResponse toSummaryResponse(Category category);
}