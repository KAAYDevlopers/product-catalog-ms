package com.abw12.absolutefitness.productcatelog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {

    private int id;
    private String productId;
    private String productVariantDesc;
    private String sku;
    private String volumn;
    private String imagePath;
    private Double price;
    private  String productFlavour;
    private Date createdDate;
    private Date modifiedDate;
}
