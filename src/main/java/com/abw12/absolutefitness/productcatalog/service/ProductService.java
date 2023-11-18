package com.abw12.absolutefitness.productcatalog.service;

import com.abw12.absolutefitness.productcatalog.dto.*;
import com.abw12.absolutefitness.productcatalog.entity.ProductCategoryDAO;
import com.abw12.absolutefitness.productcatalog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatalog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatalog.mappers.ProductCategoryMapper;
import com.abw12.absolutefitness.productcatalog.mappers.ProductMapper;
import com.abw12.absolutefitness.productcatalog.mappers.ProductVariantMapper;
import com.abw12.absolutefitness.productcatalog.persistence.ProductPersistenceLayer;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductDTO getProductById(Long productId) {
        logger.debug("Fetching data for productId: {}" , productId);
        ProductDAO productEntity = persistenceLayer.getProductById(productId);
        Long categoryId = productEntity.getCategoryId();
        List<ProductVariantDAO> variantsList = persistenceLayer.getVariantsByProductId(productId);
        logger.debug("Fetching category data with categoryId: {}",categoryId);
        ProductCategoryDAO category = persistenceLayer.getCategoryById(categoryId);


        ProductDTO productDTO = productMapper.entityToDto(productEntity);
        logger.debug("Fetched data for productId: {} => {}" , productId,productDTO);
        List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                .map(variant -> {
                    ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                    ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                    variantDTO.setInventoryData(inventoryDTO);
                    return variantDTO;
                })
                .toList();
        logger.debug("Fetched variants data for productId: {} => {}",productId,pVariantDTOList);
        ProductCategoryDTO pCategory = productCategoryMapper.entityToDto(category);
        logger.debug("Fetched category data with categoryId: {} => {}",categoryId,pCategory);
        productDTO.setProductVariants(pVariantDTOList);
        productDTO.setProductCategory(pCategory);
        logger.info("Fetched product data with productId:{} =>{}",productId,productDTO);
        return  productDTO;
    }

    public List<ProductDTO> getProductByName(String productName) {

        logger.debug("Fetching product data with productName: {}",productName);
        List<ProductDAO> productEntityList = persistenceLayer.getProductByName(productName);
        logger.debug("Fetched product data with productName: {} => {}",productName,productEntityList);

        return productEntityList.stream()
                .map(product -> {
                    Long productId = product.getProductId();
                    Long categoryId = product.getCategoryId();
                    logger.debug("Fetching variants data with productId: {}",productId);
                    List<ProductVariantDAO> variantsList = persistenceLayer.getVariantsByProductId(productId);
                    logger.debug("Fetched variants data with productId: {} => {}",productId,variantsList);

                    logger.debug("Fetching category data for productId: {} with categoryId: {}",productId,categoryId);
                    ProductCategoryDAO category = persistenceLayer.getCategoryById(categoryId);
                    logger.debug("Fetched category data for productId: {} with categoryId: {} => {}",productId,categoryId,category);
                    ProductDTO productDTO = productMapper.entityToDto(product);

                    List<ProductVariantDTO> pVariantDTOList = variantsList.stream()
                            .map(variant -> {
                                ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                                logger.debug("Fetching inventory data with variantId: {}",variant.getVariantId());
                                ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                                logger.debug("Fetched inventory data with variantId: {} => {} ",variant.getVariantId(),inventoryDTO);
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
        logger.debug("Fetching product data with categoryId: {}",categoryId);
        return persistenceLayer.getProductByCategoryId(categoryId).stream()
                .map(productDAO -> {
                    ProductDTO product = productMapper.entityToDto(productDAO);
                    logger.debug("Fetched product with categoryId: {} => {}",categoryId,productDAO);
                    Long productId = productDAO.getProductId();
                    logger.debug("Fetching variant data with productId: {}",productId);
                    List<ProductVariantDTO> variantList =  persistenceLayer.getVariantsByProductId(productId).stream()
                            .map(variant -> {
                                ProductVariantDTO variantDTO = productVariantMapper.entityToDto(variant);
                                logger.debug("Fetching inventory data with variantId: {}",variantDTO.getVariantId());
                                ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantDTO.getVariantId());
                                logger.debug("Fetched inventory data with variantId: {} => {} ",variantDTO.getVariantId(),inventoryDTO);
                                variantDTO.setInventoryData(inventoryDTO);
                                return variantDTO;
                            })
                            .toList();
                    logger.debug("Fetched variants data with productId: {} => {}",productId,variantList);

                    logger.debug("Fetching category data for productId: {} with categoryId: {}",productId,categoryId);
                    ProductCategoryDTO productCategoryDTO = productCategoryMapper.entityToDto(persistenceLayer.getCategoryById(productDAO.getCategoryId()));
                    logger.debug("Fetched category data for productId: {} with categoryId: {} => {}",productId,categoryId,productCategoryDTO);
                    product.setProductCategory(productCategoryDTO);
                    product.setProductVariants(variantList);
                    return product;
                })
                .collect(Collectors.toList());
    }

    public ProductVariantDTO getVariantDataByVariantId(Long variantId){
        logger.info("Fetching variant data with variantId :  {}",variantId);
        ProductVariantDTO variantDTO = productVariantMapper.entityToDto(persistenceLayer.getProductVariantDataById(variantId));
        //fetch inventory data for the variant
        logger.debug("Fetching Inventory data with variantId: {}",variantId);
        ProductInventoryDTO inventoryDTO = inventoryService.getVariantById(variantId);
        logger.debug("Fetched Inventory data with variantId: {} => {}",variantId,inventoryDTO);
        variantDTO.setInventoryData(inventoryDTO);
        logger.debug("Fetched variant data with variantId : {} => {}",variantId,variantDTO);
        return variantDTO;
    }
    @Transactional
    public ProductDTO insertProduct(ProductDTO productDTO){

        logger.info("Inserting new product data...");
        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        ProductCategoryDAO productCategory = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());
        //check if there is any existing category or not
        ProductCategoryDTO productCategoryDTO = checkIfCategoryExist(productCategory.getCategoryName(), productCategory);
        productEntity.setCategoryId(productCategoryDTO.getProductCategoryId()); //to maintain foreign key in product table for categoryId

        ProductDTO storedProductResponse = productMapper.entityToDto(persistenceLayer.upsertProductData(productEntity));
        Long productId = storedProductResponse.getProductId();
        List<ProductVariantDTO> productVariantsList = productDTO.getProductVariants().stream()
                .map(variantDTO -> {
                    ProductVariantDAO tempVariantDao = productVariantMapper.DtoToEntity(variantDTO);
                    tempVariantDao.setProductId(productId);
                    //store variant info in db
                    ProductVariantDAO storedVariantData = persistenceLayer.upsertVariant(tempVariantDao);
                    ProductVariantDTO response = productVariantMapper.entityToDto(storedVariantData);
                    logger.debug("variant data with productId:{} stored in db : {}" ,productId,response);
                    //taking variantId after storing the variant in db
                    variantDTO.getInventoryData().setVariantId(response.getVariantId());
                    //save inventory data for each variant in the list
                    ProductInventoryDTO storedInventoryData = inventoryService.updateVariantInventoryData(variantDTO.getInventoryData());
                    logger.debug("inventory data stored with productId: {} => {}",productId,storedInventoryData);
                    response.setInventoryData(storedInventoryData);
                    return response;
                })
                .toList();

        storedProductResponse.setProductVariants(productVariantsList);
        storedProductResponse.setProductCategory(productCategoryDTO);
        logger.info("Product data inserted: {}",storedProductResponse);
        return storedProductResponse;
    }

    public ProductCategoryDTO checkIfCategoryExist(String categoryName,ProductCategoryDAO productCategory){
        Optional<ProductCategoryDAO> categoryObj = persistenceLayer.getCategoryByName(categoryName);
        if(categoryObj.isPresent()){
            logger.debug("product category already exist with categoryId: {}",categoryObj.get().getProductCategoryId());
            return productCategoryMapper.entityToDto(categoryObj.get());
        }else{
            ProductCategoryDTO newCategoryData = productCategoryMapper.entityToDto(persistenceLayer.upsertCategoryData(productCategory));
            logger.debug("created new category with categoryId: {}",newCategoryData.getProductCategoryId());
            return newCategoryData;
        }
    }

    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO){
        if(productDTO.getProductId() == null)
            throw new RuntimeException("productId cannot be NULL");

        logger.info("updating product data with productId: {} => {}",productDTO.getProductId(),productDTO);
        Long productId = productDTO.getProductId();
        ProductDAO productEntity = productMapper.DtoToEntity(productDTO);
        if(productDTO.getProductCategory().getProductCategoryId() == null)
            throw new RuntimeException("product category categoryId cannot be NULL");
        logger.info("updating product category data with productId:{}",productId);
        ProductCategoryDAO productCategoryEntity = productCategoryMapper.DtoToEntity(productDTO.getProductCategory());

        ProductDTO productResponse =productMapper.entityToDto(persistenceLayer.upsertProductData(productEntity));
        logger.debug("updated product data: {}",productResponse);
        ProductCategoryDTO productCategoryResponse = productCategoryMapper.entityToDto(persistenceLayer.upsertCategoryData(productCategoryEntity));
        logger.debug("updated category data with productId:{} => {}",productId,productCategoryResponse);
        List<ProductVariantDTO> productVariantsResponse = productDTO.getProductVariants().stream()
                .map(variantDTO -> {
                    ProductVariantDAO tempVariantDao = productVariantMapper.DtoToEntity(variantDTO);
                    tempVariantDao.setProductId(productId);
                    //store variant info in db
                    ProductVariantDAO storedVariantData = persistenceLayer.upsertVariant(tempVariantDao);
                    ProductVariantDTO response = productVariantMapper.entityToDto(storedVariantData);
                    logger.debug("variant data updated with productId:{} => {}",productId,response);
                    //taking variantId after storing the variant in db
                    variantDTO.getInventoryData().setVariantId(response.getVariantId());
                    //save inventory data for each variant in the list
                    ProductInventoryDTO storedInventoryData = inventoryService.updateVariantInventoryData(variantDTO.getInventoryData());
                    logger.debug("Inventory data updated for variant:{} => {}",response.getVariantId(),storedInventoryData);
                    response.setInventoryData(storedInventoryData);
                    return response;
                })
                .toList();
        productResponse.setProductVariants(productVariantsResponse);
        productResponse.setProductCategory(productCategoryResponse);
        logger.info("completed updating product data with productId:{}",productId);
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

    @Transactional
    public List<ProductVariantDTO> upsertVariantByProductId(Long productId , List<ProductVariantDTO> variantsList){
        if(productId == null)
            throw new RuntimeException("ProductId cannot be null for a variant");

        logger.info("insert/update variants data with productId:{} ",productId);
        return variantsList.stream()
                .map(variantDto ->{
                    ProductVariantDAO variantDAO = productVariantMapper.DtoToEntity(variantDto);
                    variantDAO.setProductId(productId);
                    //store variant info in db
                    ProductVariantDAO storedVariantData = persistenceLayer.upsertVariant(variantDAO);
                    logger.info("stored variant data with productId:{} => {} ",productId,storedVariantData);
                    ProductVariantDTO response = productVariantMapper.entityToDto(storedVariantData);
                    //if variant is new and don't have variantId for it, so we are taking variantId from response in that case
                    variantDto.getInventoryData().setVariantId(response.getVariantId());
                    //save inventory data for each variant in the list
                    ProductInventoryDTO storedInventoryData = inventoryService.updateVariantInventoryData(variantDto.getInventoryData());
                    logger.debug("stored inventory data with variantId:{} => {}",response.getVariantId(),storedInventoryData);
                    response.setInventoryData(storedInventoryData);
                    return response;
                })
                .peek(storedVariantsList -> logger.info("completed : variants list stored"))
                .toList();
    }

}
