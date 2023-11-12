package com.abw12.absolutefitness.productcatelog.persistence;

import com.abw12.absolutefitness.productcatelog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatelog.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInventoryPersistenceLayer {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    public ProductInventoryDAO getVariantData(Long variantId){
        return productInventoryRepository.getVariantDataById(variantId).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find variant by Id in Inventory table : %s",variantId)));
    }
    public ProductInventoryDAO updateVariantInventoryData(ProductInventoryDAO inventoryData){
        return  productInventoryRepository.save(inventoryData);
    }


}
