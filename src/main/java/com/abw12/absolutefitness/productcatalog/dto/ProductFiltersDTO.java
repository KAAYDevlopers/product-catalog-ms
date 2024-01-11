package com.abw12.absolutefitness.productcatalog.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFiltersDTO {
    @Id
    private String categoryId;
    private List<String> brandName;
    private List<String> variantName; //eg flavours
    private List<String> variantValue; // weight in kg/lbs
    private Integer numberOfServings;
    private String variantType; //veg/non-veg
    private BigDecimal minOnSalePrice;
    private BigDecimal maxOnSalePrice;
}
