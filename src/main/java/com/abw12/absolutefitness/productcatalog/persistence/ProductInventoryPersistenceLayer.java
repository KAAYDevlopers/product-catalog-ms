package com.abw12.absolutefitness.productcatalog.persistence;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.repository.ProductInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInventoryPersistenceLayer {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductInventoryPersistenceLayer.class);


    public ProductInventoryDAO getVariantData(String variantId){
        return productInventoryRepository.getVariantDataById(variantId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find inventory data for variant by variantId=%s in Inventory",variantId)));
    }
    public ProductInventoryDAO saveVariantInventoryData(ProductInventoryDAO inventoryData){
        logger.debug("creating new entry in variant inventory table for variantId={}",inventoryData.getVariantId());
        return productInventoryRepository.save(inventoryData);
    }

    public Integer deleteVariantInventoryByVariantId(List<String> variantIdList){
        return productInventoryRepository.deleteVariantInventoryByVariantId(variantIdList).orElseThrow(() ->
                new InvalidDataRequestException("Error while deleting inventory data for variants"));
    }

    public ProductInventoryDAO getVariantInventoryById(String variantInventoryId){
        return productInventoryRepository.findById(variantInventoryId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find inventory data by variantInventoryId=%s in Inventory",variantInventoryId)));
    }
}
