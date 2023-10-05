package com.abw12.absolutefitness.productcatelog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountsDTO {

    private String id;
    private String discountName;
    private double discountPercent;
//    private Boolean isActive;
    private Date validity;
    private Date createdDate;
    private Date modifiedDate;


}
