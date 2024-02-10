package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationReq;

import java.util.Map;

public class InventoryValidationDTOMapper {

    public static InventoryValidationReq mapRequestParamsToDTO(Map<String ,Object> params){
        InventoryValidationReq inventoryValidationReq = new InventoryValidationReq();
        if(params == null)
            return inventoryValidationReq;

        if(params.containsKey("variantId")){
            inventoryValidationReq.setVariantId((String)params.get("variantId"));
        }
        if(params.containsKey("quantityRequested")){
            try {
                inventoryValidationReq.setQuantityRequested(Long.valueOf((String)params.get("quantityRequested")));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
        }
        return inventoryValidationReq;
    }
}
