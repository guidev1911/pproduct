package com.guidev.pproduct.dto;
import lombok.*;
import java.math.BigDecimal;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private String sku;

    private String barcode;

    private String description;

    private String brand;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer stockQuantity;

    private Boolean featured;

    private CategorySummaryResponse category;
}