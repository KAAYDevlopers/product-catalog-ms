package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryDTO {

    private Long productInventoryId;
    private Long variantId;
    private Long quantity;
    private String sku;
    private Boolean inStockStatus;
    private String createdAt;
    private String modifiedAt;
}
