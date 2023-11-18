package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFiltersDTO {

    private Long categoryId;
    private String brandName;
    private String variantName;
    private String variantValue;
    private Integer numberOfServings;
    private String variantType;
    private Double minOnSalePrice;
    private Double maxOnSalePrice;
}
