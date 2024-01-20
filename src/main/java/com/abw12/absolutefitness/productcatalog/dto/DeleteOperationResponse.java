package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOperationResponse {

    private String status;
    private String message;
}
