package com.abw12.absolutefitness.productcatalog.service;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.constants.CommonConstants;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationReq;
import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationRes;
import com.abw12.absolutefitness.productcatalog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.mappers.ProductInventoryMapper;
import com.abw12.absolutefitness.productcatalog.persistence.ProductInventoryPersistenceLayer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ProductInventoryDTO updateVariantInventoryData(ProductInventoryDTO inventoryDto){
        if(StringUtils.isEmpty(inventoryDto.getVariantId())) throw new InvalidDataRequestException("Invalid request VariantId cannot be Null/Empty..");
        String variantId = inventoryDto.getVariantId();
        logger.info("Saving inventory data with variantId :: {} => {}",variantId,inventoryDto);
        ProductInventoryDAO productInventoryDAO = pInventoryPersistenceLayer.updateVariantInventoryData(inventoryMapper.DtoToEntity(inventoryDto));
        logger.info("Updated inventory data for variantId :: {} => {}",variantId,productInventoryDAO);
        return inventoryMapper.entityToDto(productInventoryDAO);
    }
    public InventoryValidationRes variantsValidation(InventoryValidationReq reqData){
        if(StringUtils.isEmpty(reqData.getVariantId())) throw new RuntimeException("Invalid request VariantId cannot be Null/Empty..");
        String variantId = reqData.getVariantId();
        logger.info("Checking variant inventory status with variantId :: {}",reqData.getVariantId());
        InventoryValidationRes response = new InventoryValidationRes();
        ProductInventoryDAO inventoryData = pInventoryPersistenceLayer.getVariantData(variantId);
        String availabilityStatus = reqData.getQuantityRequested() <= inventoryData.getQuantity()
                ? CommonConstants.IN_STOCK
                : CommonConstants.OUT_OF_STOCK;

        response.setVariantId(reqData.getVariantId());
        response.setVariantInventoryId(inventoryData.getVariantInventoryId());
        response.setQuantityRequested(reqData.getQuantityRequested());
        response.setQuantityAvailable(inventoryData.getQuantity());
        response.setStockStatus(availabilityStatus);
        logger.info("variant inventory status with variantId :: {} => {} :: available quantity = {}",variantId,availabilityStatus,inventoryData.getQuantity());
        return response;
    }
}
