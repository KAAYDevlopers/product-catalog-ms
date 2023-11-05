package com.abw12.absolutefitness.productcatelog.persistance;

import com.abw12.absolutefitness.productcatelog.entity.ProductCategoryDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatelog.repository.ProductCategoryRepository;
import com.abw12.absolutefitness.productcatelog.repository.ProductRepository;
import com.abw12.absolutefitness.productcatelog.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductPersistanceLayer {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductDAO getProductById(Long productId){

        return productRepository.getProductById(productId).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find product by Id : %s",productId)));
    }
    public List<ProductDAO> getProductByName(String productName){
        return productRepository.getProductByName(productName);
    }

    public List<ProductVariantDAO> getVariantsByProductId(Long productId){
        return productVariantRepository.getVariantsByProductId(productId);
    }

    public ProductCategoryDAO getCategoryById(Long categoryId){
        return productCategoryRepository.getProductCategory(categoryId).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find category by categoryId : %s",categoryId)));
    }

    public Optional<ProductCategoryDAO> getCategoryByName(String categoryName){
        return productCategoryRepository.getProductCategoryByName(categoryName);
    }

    public ProductDAO upsertProductData(ProductDAO inputProductEntity){
        return productRepository.save(inputProductEntity);
    }

    public List<ProductVariantDAO> upsertVariantData(List<ProductVariantDAO> inputVariantEntity){
        return productVariantRepository.saveAll(inputVariantEntity);
    }

    public ProductCategoryDAO upsertCategoryData(ProductCategoryDAO inputCategoryEntity){
        return productCategoryRepository.save(inputCategoryEntity);
    }

    public List<ProductDAO> getProductByCategoryId(Long categoryId){
        return productRepository.getProductsByCategoryID(categoryId);
    }
    
    
}
