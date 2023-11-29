package com.abw12.absolutefitness.productcatalog.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Id
    private String productId;
    @NotBlank
    private String productName;
    private String productDescription;
    private String categoryId;
    @NotBlank
    private String brandName;
    @NotNull
    private ProductCategoryDTO productCategory;
    private List<ProductVariantDTO> productVariants;
    private String productCreatedAt;
    private String productModifiedAt;
}
