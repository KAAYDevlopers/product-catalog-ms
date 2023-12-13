package com.abw12.absolutefitness.productcatalog.persistence;

import com.abw12.absolutefitness.productcatalog.advice.InvalidDataRequestException;
import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductCategoryDAO;
import com.abw12.absolutefitness.productcatalog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatalog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatalog.repository.ProductCategoryRepository;
import com.abw12.absolutefitness.productcatalog.repository.ProductRepository;
import com.abw12.absolutefitness.productcatalog.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductPersistenceLayer {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductDAO getProductById(String productId){

        return productRepository.getProductById(productId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find product by Id : %s",productId)));
    }
    public List<ProductDAO> getProductByName(String productName){
        return productRepository.getProductByName(productName);
    }

    public List<ProductVariantDAO> getVariantsByProductId(String productId){
        return productVariantRepository.getVariantsByProductId(productId);
    }

    public ProductCategoryDAO getCategoryById(String categoryId){
        return productCategoryRepository.getProductCategory(categoryId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find category by categoryId : %s",categoryId)));
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

    public List<ProductDAO> getProductByCategoryId(String categoryId){
        return productRepository.getProductsByCategoryID(categoryId);
    }

    public ProductVariantDAO getProductVariantDataById(String variantId){
        return productVariantRepository.getVariantData(variantId).orElseThrow(() ->
                new InvalidDataRequestException(String.format("Cannot find variant by variantId : %s",variantId)));
    }

    public List<ProductDAO> getProductsByFilters(ProductFiltersDTO filtersDTO){
        return productRepository.getProductByFilters(filtersDTO);
    }

    public List<ProductVariantDAO> getVariantsByFilters(String productId ,ProductFiltersDTO filtersDTO){
        return productVariantRepository.getVariantsByFilters(productId,filtersDTO);
    }

    public List<ProductVariantDAO> upsertVariantsByProductId(List<ProductVariantDAO> listOfVariants){
        return productVariantRepository.saveAll(listOfVariants);
    }
    public ProductVariantDAO upsertVariant(ProductVariantDAO variant){
        return productVariantRepository.save(variant);
    }
}
