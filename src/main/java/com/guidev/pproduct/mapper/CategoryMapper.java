package com.guidev.pproduct.mapper;

import com.guidev.pproduct.dto.CategorySummaryResponse;
import com.guidev.pproduct.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategorySummaryResponse toSummaryResponse(Category category);
}