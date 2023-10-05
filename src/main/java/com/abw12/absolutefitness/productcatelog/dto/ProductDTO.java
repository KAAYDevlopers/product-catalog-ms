package com.abw12.absolutefitness.productcatelog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
//    private String id;
    private Long productId;
    @NotBlank
    private String productName;
    @NotBlank
    private String brand;
    @NotNull
    private List<ProductCatagoryDTO> productCatagory;
    private List<ProductVariantDTO> productVariants;
    private ProductDescriptionDTO productDescription;
    private List<ProductDiscountsDTO> productDiscounts;
    private ProductInventoryDTO inventoryInfo;
    private Date createdDate;
    private Date modifiedDate;
}
