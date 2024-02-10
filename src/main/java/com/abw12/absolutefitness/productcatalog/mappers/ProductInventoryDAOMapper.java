package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.helper.Utils;

import java.time.OffsetDateTime;
import java.util.Map;

public class ProductInventoryDAOMapper {

    public static void mapToProductInventoryDAO(String variantInventoryId,Map<String, Object> params,ProductInventoryDAO existingData) {

        existingData.setVariantInventoryId(variantInventoryId);

        if(params.containsKey("variantId"))
            existingData.setVariantId((String) params.get("variantId"));

        if(params.containsKey("updateQuantity")){
            long updateQuantity;
            try{
                updateQuantity = Long.parseLong( (String)params.get("updateQuantity"));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
            if(existingData.getQuantity() !=null && existingData.getQuantity() < updateQuantity)
                throw new InvalidDataRequestException(
                        String.format("Requested Quantity to update is greater then the current quantity for variantInventoryId=%s :: CurrentQuantity=%s :: RequestedQuantity=%s",
                        variantInventoryId,existingData.getQuantity(),updateQuantity));

            Long updatedQuantity = existingData.getQuantity() - updateQuantity;
            existingData.setQuantity(updatedQuantity);
        }

        if(params.containsKey("sku"))
            existingData.setSku((String) params.get("sku"));

        if(params.containsKey("reservedQuantity")){
            long quantityToReserve;
            try{
                quantityToReserve  = Long.parseLong((String) params.get("reservedQuantity"));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }
            if(existingData.getReservedQuantity()!=null && existingData.getReservedQuantity() > 0L){
                Long updateReserveQuantity = existingData.getReservedQuantity() + quantityToReserve ; //add to current reserved number of quantity
                existingData.setReservedQuantity(updateReserveQuantity);
            }else {
                existingData.setReservedQuantity(quantityToReserve);
            }
        }

        if(params.containsKey("releaseQuantity") && !params.containsKey("reservedQuantity")){
            long releaseQuantity;
            try{
                releaseQuantity  = Long.parseLong((String) params.get("releaseQuantity"));
            } catch (NumberFormatException e) {
                throw new InvalidDataRequestException(e.getMessage());
            }

            if(existingData.getReservedQuantity()!=null && existingData.getReservedQuantity() > 0L){
                Long updateReserveQuantity = existingData.getReservedQuantity() - releaseQuantity ; //quantity to remove from the current reserved number of quantity
                existingData.setReservedQuantity(updateReserveQuantity);
            }
        }

        if(params.containsKey("isReserved"))
            existingData.setReserved(Boolean.parseBoolean((String) params.get("isReserved")));

        existingData.setModifiedAt(OffsetDateTime.parse(OffsetDateTime.now().format(Utils.dateFormat())));
    }
}
