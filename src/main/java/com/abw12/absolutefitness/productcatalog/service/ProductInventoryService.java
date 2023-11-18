package com.abw12.absolutefitness.productcatalog.service;

import com.abw12.absolutefitness.productcatalog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.mappers.ProductInventoryMapper;
import com.abw12.absolutefitness.productcatalog.persistence.ProductInventoryPersistenceLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryPersistenceLayer pInventoryPersistenceLayer;
    @Autowired
    private ProductInventoryMapper inventoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductInventoryService.class);

    public ProductInventoryDTO getVariantById(Long variantId){
        return inventoryMapper.entityToDto(pInventoryPersistenceLayer.getVariantData(variantId));
    }
    public ProductInventoryDTO updateVariantInventoryData(ProductInventoryDTO inventoryDto){
        return inventoryMapper.entityToDto(setStockStatusAndSaveVariant(inventoryDto));
    }
    public Map<Long,Boolean> variantsValidation(List<Long> variantIdsList){
        return variantIdsList.stream()
                .map(variantId -> pInventoryPersistenceLayer.getVariantData(variantId))
                .collect(Collectors.toMap(ProductInventoryDAO::getVariantId, ProductInventoryDAO::getInStockStatus, (k1, k2) -> k2));
    }
    public ProductInventoryDAO setStockStatusAndSaveVariant(ProductInventoryDTO inventoryData){
        if(inventoryData.getQuantity()!=null)
            inventoryData.setInStockStatus(inventoryData.getQuantity() > 0);
        return pInventoryPersistenceLayer.updateVariantInventoryData(inventoryMapper.DtoToEntity(inventoryData));
    }

}
