package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationReq;

import java.util.Map;

public class InventoryValidationDTOMapper {

    public static InventoryValidationReq mapRequestParamsToDTO(Map<String ,String> params){
        InventoryValidationReq inventoryValidationReq = new InventoryValidationReq();
        if(params == null)
            return inventoryValidationReq;

        if(params.containsKey("variantId")){
            inventoryValidationReq.setVariantId(params.get("variantId"));
        }
        if(params.containsKey("quantityRequested")){
            try {
                inventoryValidationReq.setQuantityRequested(Long.valueOf(params.get("quantityRequested")));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
        }
        return inventoryValidationReq;
    }
}
