package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcOfferResponse {

    private String msg;
    private String statusCode;
    private BigDecimal onSalePrice;
}
