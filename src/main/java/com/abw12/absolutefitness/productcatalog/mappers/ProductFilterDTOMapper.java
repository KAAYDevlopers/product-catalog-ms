package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

public class ProductFilterDTOMapper {

    public static ProductFiltersDTO mapRequestParamsToDTO(Map<String,String> params){
        ProductFiltersDTO filtersDTO = new ProductFiltersDTO();

        if (params.containsKey("categoryId")) {
            filtersDTO.setCategoryId(params.get("categoryId"));
        }

        if (params.containsKey("brandName")) {
            filtersDTO.setBrandName(Arrays.asList(params.get("brandName").split(",")));
        }

        if (params.containsKey("variantName")) {
            filtersDTO.setVariantName(Arrays.asList(params.get("variantName").split(",")));
        }

        if (params.containsKey("variantValue")) {
            filtersDTO.setVariantValue(Arrays.asList(params.get("variantValue").split(",")));
        }

        if (params.containsKey("numberOfServings")) {
            try {
                filtersDTO.setNumberOfServings(Integer.parseInt(params.get("numberOfServings")));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
        }

        if (params.containsKey("variantType")) {
            filtersDTO.setVariantType(params.get("variantType"));
        }

        if (params.containsKey("minOnSalePrice")) {
            try {
                filtersDTO.setMinOnSalePrice(new BigDecimal(params.get("minOnSalePrice")));
            } catch (NumberFormatException | NullPointerException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
        }

        if (params.containsKey("maxOnSalePrice")) {
            try {
                filtersDTO.setMaxOnSalePrice(new BigDecimal(params.get("maxOnSalePrice")));
            } catch (NumberFormatException | NullPointerException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
        }
        return filtersDTO;
    }
}
