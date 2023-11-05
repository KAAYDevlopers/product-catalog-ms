package com.abw12.absolutefitness.productcatelog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {

    private Long variantId;
    private String variantName;
    private String variantValue;
    private String sku;
    private String imagePath;
    private Double buyPrice;
    private Double onSalePrice;
    private String about;
    private String benefits;
    private String nutritionFacts;
    private String usageDose;
    private String manufacturerDetails;
    //    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private OffsetDateTime expiryDate;
    //    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private OffsetDateTime mfdDate;
    private OffsetDateTime variantCreatedAt;
    private OffsetDateTime variantModifiedAt;
}
