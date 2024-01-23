package com.abw12.absolutefitness.productcatalog.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {
    @Id
    private String variantId;
    private String productId;
    private String variantName; //flavour like coffee,etc.
    private String variantValue; //weight in kg or lbs
    private String variantType; //veg or non-veg
    private String imagePath;
    private BigDecimal buyPrice;
    private BigDecimal onSalePrice;
    private String about;
    private String benefits;
    private String nutritionFacts;
    private String usageDose;
    private String manufacturerDetails;
    private Integer numberOfServings;
    private ProductInventoryDTO inventoryData;
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private String expiryDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private String mfdDate;
    private String variantCreatedAt;
    private String variantModifiedAt;
}
