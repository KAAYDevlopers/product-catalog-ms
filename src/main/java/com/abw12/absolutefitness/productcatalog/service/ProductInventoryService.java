package com.abw12.absolutefitness.productcatalog.service;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.constants.CommonConstants;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationReq;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationRes;
import com.abw12.absolutefitness.productcatalog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.mappers.ProductInventoryDAOMapper;
import com.abw12.absolutefitness.productcatalog.mappers.ProductInventoryMapper;
import com.abw12.absolutefitness.productcatalog.persistence.ProductInventoryPersistenceLayer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryPersistenceLayer pInventoryPersistenceLayer;
    @Autowired
    private ProductInventoryMapper inventoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductInventoryService.class);

    public ProductInventoryDTO getVariantById(String variantId){
        logger.info("Getting inventory data with variantId :: {}",variantId);
        return inventoryMapper.entityToDto(pInventoryPersistenceLayer.getVariantData(variantId));
    }
    @Transactional
    public ProductInventoryDTO updateVariantInventoryData(ProductInventoryDTO inventoryDto){
        if(StringUtils.isEmpty(inventoryDto.getVariantId())) throw new InvalidDataRequestException("Invalid request VariantId cannot be Null/Empty..");
        String variantId = inventoryDto.getVariantId();
        logger.info("Saving inventory data with variantId :: {} => {}",variantId,inventoryDto);
        ProductInventoryDAO productInventoryDAO = pInventoryPersistenceLayer.saveVariantInventoryData(inventoryMapper.DtoToEntity(inventoryDto));
        logger.info("Updated inventory data for variantId :: {} => {}",variantId,productInventoryDAO);
        return inventoryMapper.entityToDto(productInventoryDAO);
    }
    @Transactional(readOnly = true)
    public InventoryValidationRes variantsValidation(InventoryValidationReq reqData){
        if(StringUtils.isEmpty(reqData.getVariantId())) throw new RuntimeException("Invalid request VariantId cannot be Null/Empty..");
        String variantId = reqData.getVariantId();
        logger.info("Checking variant inventory status with variantId :: {}",reqData.getVariantId());
        InventoryValidationRes response = new InventoryValidationRes();
        ProductInventoryDAO existingData = pInventoryPersistenceLayer.getVariantData(variantId);
        Long reservedQuantity=0L;
        if(existingData.isReserved())
            reservedQuantity=existingData.getReservedQuantity();

        String availabilityStatus = reqData.getQuantityRequested() <= (existingData.getQuantity() - reservedQuantity)
                ? CommonConstants.IN_STOCK
                : CommonConstants.OUT_OF_STOCK;

        response.setVariantId(reqData.getVariantId());
        response.setVariantInventoryId(existingData.getVariantInventoryId());
        response.setQuantityRequested(reqData.getQuantityRequested());

        Long quantityAvailable= existingData.isReserved() ? existingData.getQuantity() - reservedQuantity : existingData.getQuantity();
        response.setQuantityAvailable(quantityAvailable);

        response.setStockStatus(availabilityStatus);
        logger.info("variant inventory status with variantId :: {} => {} :: available quantity = {}",variantId,availabilityStatus,existingData.getQuantity());
        return response;
    }

    @Transactional
    public Integer deleteInventoryData(List<String> variantIdList){
        logger.info("Deleting inventory data for all variant in variantIdList={}",variantIdList);
        Integer deleteCount = pInventoryPersistenceLayer.deleteVariantInventoryByVariantId(variantIdList);
        logger.info("Deleted inventory data for all variant in variantIdList={}, delete count={}",variantIdList,deleteCount);
        return deleteCount;
    }

    @Transactional
    public Map<String,Object> patchVariantInventoryData(Map<String,Object> params){
        if(!params.containsKey("variantInventoryId")){
            logger.error("variantInventoryId cannot be Null/Empty for the patch request :: request => {}",params);
            return Map.of("ErrMsg","variantInventoryId cannot be Null/Empty for the patch request");
        }
        String variantInventoryId = (String) params.get("variantInventoryId");
        ProductInventoryDAO dataToUpdate = pInventoryPersistenceLayer.getVariantInventoryById(variantInventoryId);
        ProductInventoryDAOMapper.mapToProductInventoryDAO(variantInventoryId,params,dataToUpdate);
        pInventoryPersistenceLayer.saveVariantInventoryData(dataToUpdate);
        logger.info("Patch request is completed for variant Inventory data with variantInventoryId={}",variantInventoryId);
        return Map.of("SuccessMsg","Successfully Updated the Variant Inventory Data");
    }
}
