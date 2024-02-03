package com.abw12.absolutefitness.productcatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageMetaDataDTO {

    private String imageUrl;
    private String fileName;
    private String fileSize;
    private String contentType;
}
