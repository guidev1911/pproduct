package com.guidev.pproduct.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String sku;

    @Size(max = 100)
    private String barcode;

    @Size(max = 2000)
    private String description;

    @Size(max = 100)
    private String brand;

    @NotNull
    private Long categoryId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private BigDecimal discountPrice;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    @NotNull
    @Min(0)
    private Integer minimumStock;

    @Size(max = 500)
    private String imageUrl;

    private Boolean featured;
}