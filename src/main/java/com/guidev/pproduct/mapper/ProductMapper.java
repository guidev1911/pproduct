package com.guidev.pproduct.mapper;

import com.guidev.pproduct.dto.ProductResponse;
import com.guidev.pproduct.entity.Product;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = CategoryMapper.class
)
public interface ProductMapper {

    ProductResponse toResponse(Product product);
}