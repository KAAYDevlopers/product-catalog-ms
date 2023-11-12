package com.abw12.absolutefitness.productcatelog.service;

import com.abw12.absolutefitness.productcatelog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatelog.mappers.ProductInventoryMapper;
import com.abw12.absolutefitness.productcatelog.persistence.ProductInventoryPersistenceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryPersistenceLayer pInventoryPersistenceLayer;

    @Autowired
    private ProductInventoryMapper inventoryMapper;

    public ProductInventoryDTO getVariantById(Long variantId){
        return inventoryMapper.entityToDto(pInventoryPersistenceLayer.getVariantData(variantId));
    }

    public ProductInventoryDTO updateVariantInventoryData(ProductInventoryDTO inventoryDto){
        return inventoryMapper.entityToDto(pInventoryPersistenceLayer.updateVariantInventoryData(inventoryMapper.DtoToEntity(inventoryDto)));
    }

}
