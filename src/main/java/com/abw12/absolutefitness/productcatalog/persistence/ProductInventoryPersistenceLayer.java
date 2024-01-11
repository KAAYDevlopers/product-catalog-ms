package com.abw12.absolutefitness.productcatalog.persistence;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInventoryPersistenceLayer {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    public ProductInventoryDAO getVariantData(String variantId){
        return productInventoryRepository.getVariantDataById(variantId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find variant by variantId=%s in Inventory",variantId)));
    }
    public ProductInventoryDAO updateVariantInventoryData(ProductInventoryDAO inventoryData){
        return  productInventoryRepository.save(inventoryData);
    }

}
