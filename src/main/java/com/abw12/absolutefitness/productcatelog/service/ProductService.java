package com.abw12.absolutefitness.productcatelog.service;

import com.abw12.absolutefitness.productcatelog.dto.ProductCategoryDTO;
import com.abw12.absolutefitness.productcatelog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatelog.dto.ProductVariantDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductCategoryDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatelog.mappers.ProductCategoryMapper;
import com.abw12.absolutefitness.productcatelog.mappers.ProductMapper;
import com.abw12.absolutefitness.productcatelog.mappers.ProductVariantMapper;
import com.abw12.absolutefitness.productcatelog.persistance.ProductPersistanceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductPersistanceLayer persistanceLayer;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    public ProductDTO getProductById(Long productId) {

        ProductDAO productEntity = persistanceLayer.getProductById(productId);
        Long categoryId = productEntity.getCategoryId();
        List<ProductVariantDAO> variantsList = persistanceLayer.getVariantsByProductId(productId);
        ProductCategoryDAO category = persistanceLayer.getCategoryById(categoryId);

        ProductDTO productDTO = productMapper.entityToDto(productEntity);
        List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                .map(variant -> productVariantMapper.entityToDto(variant))
                .toList();
        ProductCategoryDTO pCategory = productCategoryMapper.entityToDto(category);

        productDTO.setProductVariants(pVariantDTOList);
        productDTO.setProductCategory(pCategory);
        return  productDTO;

    }

    public List<ProductDTO> getProductByName(String productName) {

        List<ProductDAO> productEntityList = persistanceLayer.getProductByName(productName);

        return productEntityList.stream()
                .map(product -> {
                    Long productId = product.getProductId();
                    Long categoryId = product.getCategoryId();
                    List<ProductVariantDAO> variantsList = persistanceLayer.getVariantsByProductId(productId);
                    ProductCategoryDAO category = persistanceLayer.getCategoryById(categoryId);
                    ProductDTO productDTO = productMapper.entityToDto(product);
                    List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                            .map(variant -> productVariantMapper.entityToDto(variant))
                            .toList();
                    ProductCategoryDTO pCategory = productCategoryMapper.entityToDto(category);
                    productDTO.setProductVariants(pVariantDTOList);
                    productDTO.setProductCategory(pCategory);
                    return productDTO;
                })
                .toList();
    }
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return persistanceLayer.getProductByCategoryId(categoryId).stream()
                .map(productDAO -> {
                    ProductDTO product = productMapper.entityToDto(productDAO);

                    Long productId = productDAO.getProductId();
                    List<ProductVariantDTO> variantList =  persistanceLayer.getVariantsByProductId(productId)
                            .stream().map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                            .toList();
                    ProductCategoryDTO productCategoryDTO = productCategoryMapper.entityToDto(persistanceLayer.getCategoryById(productDAO.getCategoryId()));
                    product.setProductCategory(productCategoryDTO);
                    product.setProductVariants(variantList);
                    return product;
                })
                .collect(Collectors.toList());
    }

    public ProductDTO insertProduct(ProductDTO productDTO){

        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        ProductCategoryDAO productCategory = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());
        //check if there is any existing category or not
        ProductCategoryDTO productCategoryDTO = checkIfCategoryExist(productCategory.getCategoryName(), productCategory);
        productEntity.setCategoryId(productCategoryDTO.getProductCategoryId()); //to maintain foreign key in product table for categoryId

        ProductDTO storedProductResponse = productMapper.entityToDto(persistanceLayer.upsertProductData(productEntity));

        Long productId = storedProductResponse.getProductId();
        List<ProductVariantDAO> productVariantsList = productDTO.getProductVariants().stream()
                .map(productVariantDTO -> {
                    ProductVariantDAO tempVariantDao = productVariantMapper.DtoToEntity(productVariantDTO);
                    tempVariantDao.setProductId(productId);
                    return tempVariantDao;
                })
                .toList();

        List<ProductVariantDTO> variantDBResponse = persistanceLayer.upsertVariantData(productVariantsList).stream()
                .map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                .toList();

        storedProductResponse.setProductVariants(variantDBResponse);
        storedProductResponse.setProductCategory(productCategoryDTO);
        return storedProductResponse;
    }

    public ProductCategoryDTO checkIfCategoryExist(String categoryName,ProductCategoryDAO productCategory){
        Optional<ProductCategoryDAO> categoryObj = persistanceLayer.getCategoryByName(categoryName);
        if(categoryObj.isPresent()){
            return productCategoryMapper.entityToDto(categoryObj.get());
        }else{
            return productCategoryMapper.entityToDto(persistanceLayer.upsertCategoryData(productCategory));
        }
    }

    public ProductDTO updateProduct(ProductDTO productDTO){

        if(productDTO.getProductId() == null)
            throw new RuntimeException("productId cannot be NULL");

        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        List<ProductVariantDAO> productVariantsList = productDTO.getProductVariants().stream()
                .map(productVariantDTO -> {
                    if(productVariantDTO.getVariantId() == null)
                        throw new RuntimeException("variantId cannot be NULL");

                    return productVariantMapper.DtoToEntity(productVariantDTO);
                })
                .toList();

        if(productDTO.getProductCategory().getProductCategoryId() == null)
            throw new RuntimeException("product category categoryId cannot be NULL");
        ProductCategoryDAO productCategoryEntity = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());

        ProductDTO productResponse =productMapper.entityToDto(persistanceLayer.upsertProductData(productEntity));
        ProductCategoryDTO productCategoryResponse = productCategoryMapper.entityToDto(persistanceLayer.upsertCategoryData(productCategoryEntity));
        List<ProductVariantDTO> productVariantsResponse = persistanceLayer.upsertVariantData(productVariantsList).stream()
                .map(productVariantDAO -> productVariantMapper.entityToDto(productVariantDAO))
                .toList();

        productResponse.setProductVariants(productVariantsResponse);
        productResponse.setProductCategory(productCategoryResponse);
        return productResponse;
    }


}
