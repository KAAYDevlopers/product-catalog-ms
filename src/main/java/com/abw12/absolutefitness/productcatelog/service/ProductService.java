package com.abw12.absolutefitness.productcatelog.service;

import com.abw12.absolutefitness.productcatelog.dto.*;
import com.abw12.absolutefitness.productcatelog.entity.ProductCategoryDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatelog.mappers.ProductCategoryMapper;
import com.abw12.absolutefitness.productcatelog.mappers.ProductMapper;
import com.abw12.absolutefitness.productcatelog.mappers.ProductVariantMapper;
import com.abw12.absolutefitness.productcatelog.persistence.ProductPersistenceLayer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductPersistenceLayer persistenceLayer;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductInventoryService inventoryService;
    public ProductDTO getProductById(Long productId) {

        ProductDAO productEntity = persistenceLayer.getProductById(productId);
        Long categoryId = productEntity.getCategoryId();
        List<ProductVariantDAO> variantsList = persistenceLayer.getVariantsByProductId(productId);
        ProductCategoryDAO category = persistenceLayer.getCategoryById(categoryId);

        ProductDTO productDTO = productMapper.entityToDto(productEntity);
        List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                .map(variant -> {
                    ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                    ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                    variantDTO.setInventoryData(inventoryDTO);
                    return variantDTO;
                })
                .toList();
        ProductCategoryDTO pCategory = productCategoryMapper.entityToDto(category);

        productDTO.setProductVariants(pVariantDTOList);
        productDTO.setProductCategory(pCategory);
        return  productDTO;
    }

    public List<ProductDTO> getProductByName(String productName) {

        List<ProductDAO> productEntityList = persistenceLayer.getProductByName(productName);

        return productEntityList.stream()
                .map(product -> {
                    Long productId = product.getProductId();
                    Long categoryId = product.getCategoryId();
                    List<ProductVariantDAO> variantsList = persistenceLayer.getVariantsByProductId(productId);
                    ProductCategoryDAO category = persistenceLayer.getCategoryById(categoryId);
                    ProductDTO productDTO = productMapper.entityToDto(product);
                    List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                            .map(variant -> {
                                ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                                ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                                variantDTO.setInventoryData(inventoryDTO);
                                return variantDTO;
                            })
                            .toList();
                    ProductCategoryDTO pCategory = productCategoryMapper.entityToDto(category);
                    productDTO.setProductVariants(pVariantDTOList);
                    productDTO.setProductCategory(pCategory);
                    return productDTO;
                })
                .toList();
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return persistenceLayer.getProductByCategoryId(categoryId).stream()
                .map(productDAO -> {
                    ProductDTO product = productMapper.entityToDto(productDAO);

                    Long productId = productDAO.getProductId();
                    List<ProductVariantDTO> variantList =  persistenceLayer.getVariantsByProductId(productId)
                            .stream().map(variant -> {
                                ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                                ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                                variantDTO.setInventoryData(inventoryDTO);
                                return variantDTO;
                            })
                            .toList();
                    ProductCategoryDTO productCategoryDTO = productCategoryMapper.entityToDto(persistenceLayer.getCategoryById(productDAO.getCategoryId()));
                    product.setProductCategory(productCategoryDTO);
                    product.setProductVariants(variantList);
                    return product;
                })
                .collect(Collectors.toList());
    }
    @Transactional
    public ProductDTO insertProduct(ProductDTO productDTO){

        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        ProductCategoryDAO productCategory = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());
        //check if there is any existing category or not
        ProductCategoryDTO productCategoryDTO = checkIfCategoryExist(productCategory.getCategoryName(), productCategory);
        productEntity.setCategoryId(productCategoryDTO.getProductCategoryId()); //to maintain foreign key in product table for categoryId

        ProductDTO storedProductResponse = productMapper.entityToDto(persistenceLayer.upsertProductData(productEntity));

        Long productId = storedProductResponse.getProductId();
        List<ProductVariantDAO> productVariantsList = productDTO.getProductVariants().stream()
                .map(productVariantDTO -> {
                    ProductVariantDAO tempVariantDao = productVariantMapper.DtoToEntity(productVariantDTO);
                    tempVariantDao.setProductId(productId);
                    return tempVariantDao;
                })
                .toList();

        List<ProductVariantDTO> variantDBResponse = persistenceLayer.upsertVariantData(productVariantsList).stream()
                .map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                .toList();

        storedProductResponse.setProductVariants(variantDBResponse);
        storedProductResponse.setProductCategory(productCategoryDTO);
        return storedProductResponse;
    }

    public ProductCategoryDTO checkIfCategoryExist(String categoryName,ProductCategoryDAO productCategory){
        Optional<ProductCategoryDAO> categoryObj = persistenceLayer.getCategoryByName(categoryName);
        if(categoryObj.isPresent()){
            return productCategoryMapper.entityToDto(categoryObj.get());
        }else{
            return productCategoryMapper.entityToDto(persistenceLayer.upsertCategoryData(productCategory));
        }
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO){
        if(productDTO.getProductId() == null)
            throw new RuntimeException("productId cannot be NULL");

        Long productId = productDTO.getProductId();
        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        List<ProductVariantDAO> productVariantsList = productDTO.getProductVariants().stream()
                .map(productVariantDTO -> {
                    if(productVariantDTO.getVariantId() == null)
                        throw new RuntimeException("variantId cannot be NULL");
                    ProductVariantDAO variant = productVariantMapper.DtoToEntity(productVariantDTO);
                    variant.setProductId(productId);
                    return variant;
                })
                .toList();

        if(productDTO.getProductCategory().getProductCategoryId() == null)
            throw new RuntimeException("product category categoryId cannot be NULL");
        ProductCategoryDAO productCategoryEntity = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());

        ProductDTO productResponse =productMapper.entityToDto(persistenceLayer.upsertProductData(productEntity));
        ProductCategoryDTO productCategoryResponse = productCategoryMapper.entityToDto(persistenceLayer.upsertCategoryData(productCategoryEntity));
        List<ProductVariantDTO> productVariantsResponse = persistenceLayer.upsertVariantData(productVariantsList).stream()
                .map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                .toList();

        productResponse.setProductVariants(productVariantsResponse);
        productResponse.setProductCategory(productCategoryResponse);
        return productResponse;
    }

    public List<ProductDTO> filterProduct(ProductFiltersDTO filtersDTO){
        if(filtersDTO.getCategoryId() == null)
            throw new RuntimeException("categoryId cannot be NULL");
        //product list for current category
        List<ProductDTO> productList = persistenceLayer.getProductsByFilters(filtersDTO).stream()
                .map(productDAO -> productMapper.entityToDto(productDAO))
                .toList();

        return productList.stream()
                .peek(productDTO -> {
                    List<ProductVariantDTO> productVariants = persistenceLayer.getVariantsByFilters(productDTO.getProductId(), filtersDTO).stream()
                            .map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                            .toList();
                    productDTO.setProductVariants(productVariants);
                    productDTO.setProductCategory(productCategoryMapper.entityToDto(persistenceLayer.getCategoryById(filtersDTO.getCategoryId())));
                })
                .filter(productDTO -> productDTO.getProductVariants().size() > 0)
                .toList();
    }

}
