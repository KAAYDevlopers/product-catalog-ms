package com.abw12.absolutefitness.productcatelog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCatagoryDTO {

    private String id;
    private String catagoryName;
    private String catagoryDesc;
    private Date createdDate;
    private Date modifiedDate;

}
