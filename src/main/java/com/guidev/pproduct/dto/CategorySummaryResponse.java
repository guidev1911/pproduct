package com.guidev.pproduct.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryResponse {

    private Long id;

    private String name;

    private String slug;
}