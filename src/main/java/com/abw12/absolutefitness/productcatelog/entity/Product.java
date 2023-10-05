package com.abw12.absolutefitness.productcatelog.entity;

import com.abw12.absolutefitness.productcatelog.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Product {
    private Long productId;
    private String productName;
    private String brand;
    private List<ProductCatagoryDTO> productCatagory;
    private List<ProductVariantDTO> productVariants;
    private ProductDescriptionDTO productDescription;
    private List<ProductDiscountsDTO> productDiscounts;
    private ProductInventoryDTO inventoryInfo;
    private Date createdDate;
    private Date modifiedDate;

}
