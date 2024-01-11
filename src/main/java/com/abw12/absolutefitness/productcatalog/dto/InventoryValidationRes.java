package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryValidationRes {

    private String variantId;
    private String variantInventoryId;
    private String stockStatus;
    private Long quantityRequested;
    private Long quantityAvailable;
}
