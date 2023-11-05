package com.abw12.absolutefitness.productcatelog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryDTO {

    private Long productInventoryId;
    private Long stockInTotal;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
}
