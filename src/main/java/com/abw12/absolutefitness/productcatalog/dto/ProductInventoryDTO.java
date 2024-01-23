package com.abw12.absolutefitness.productcatalog.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryDTO {
    @Id
    private String variantInventoryId;
    private String variantId;
    private Long quantity;
    private String sku;
    private Long reservedQuantity;
    private boolean isReserved;
    private String createdAt;
    private String modifiedAt;
}
