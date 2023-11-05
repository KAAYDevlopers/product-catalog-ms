package com.abw12.absolutefitness.productcatelog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank
    private String productName;
    private String productDescription;
    private Long categoryId;
    @NotBlank
    private String brandName;
    @NotNull
    private ProductCategoryDTO productCategory;
    private List<ProductVariantDTO> productVariants;
    private OffsetDateTime productCreatedAt;
    private OffsetDateTime productModifiedAt;
}
