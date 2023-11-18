package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {

    private Long variantId;
    private Long productId;
    private String variantName;
    private String variantValue;
    //    private String sku;
    private String imagePath;
    private Double buyPrice;
    private Double onSalePrice;
    private String about;
    private String benefits;
    //    private Long quantity;
    private String nutritionFacts;
    private String usageDose;
    private String manufacturerDetails;
    private String variantType;
    private Integer numberOfServings;
    private ProductInventoryDTO inventoryData;
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private String expiryDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private String mfdDate;
    private String variantCreatedAt;
    private String variantModifiedAt;
}
