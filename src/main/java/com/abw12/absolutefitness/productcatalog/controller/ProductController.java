package com.abw12.absolutefitness.productcatalog.controller;


import com.abw12.absolutefitness.productcatalog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatalog.dto.ProductVariantDTO;
import com.abw12.absolutefitness.productcatalog.mappers.ProductFilterDTOMapper;
import com.abw12.absolutefitness.productcatalog.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalog/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/listproducts")
    public ResponseEntity<?> listProducts(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        logger.info("Inside List Product Rest API...");
        try{
            return new ResponseEntity<>(productService.ListProduct(PageRequest.of(page, size)),HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while fetching product by Id : {}",e.getMessage());
            throw e;
        }
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId){
        logger.info("Inside getProductById rest call: {} " , productId);
        try{
            return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception while fetching product by Id : {}",e.getMessage());
            throw e;
        }
    }

    @GetMapping("/getProductByName/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName){
        logger.info("Inside getProductByName rest call: {} " , productName);
        try{

            return new ResponseEntity<>(productService.getProductByName(productName),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Exception while fetching the product by product name : {} :: Error Message= {}",productName,e.getMessage());
            throw e;
        }
    }

    @GetMapping("/categories/{categoryID}")
    public ResponseEntity<?> getProductByCategoryID(@PathVariable String categoryID){
        logger.info("Inside getProductByCategoryID rest call: {} " , categoryID);
        try{
            return  new ResponseEntity<>(productService.getProductsByCategoryId(categoryID),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Exception while fetching the product by catLogId : {} :: Error Message= {}",categoryID,e.getMessage());
            throw e;
        }
    }

    @GetMapping("/getVariantData/{variantId}")
    public ResponseEntity<?> getVariantData(@PathVariable String variantId){
        logger.info("Inside getVariantData rest call");
        try{
            return new ResponseEntity<>(productService.getVariantDataByVariantId(variantId),HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while fetching the variant data with variantId:{} :: Error Message= {}",variantId,e.getMessage());
            throw e;
        }
    }
    @GetMapping("/filterProduct")
    public ResponseEntity<?> productFilters(@RequestParam Map<String,String> params){
        logger.info("Inside filterProduct rest call : {}" ,params);
        ProductFiltersDTO productFilters = ProductFilterDTOMapper.mapRequestParamsToDTO(params);
        try{
            return new ResponseEntity<>(productService.filterProduct(productFilters),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Error processing request for filters :: Error Message= {}",e.getMessage());
            throw e;
        }
    }

    @PostMapping("/insertProduct")
    public ResponseEntity<?> insertProduct(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Inside saveProduct rest call");
        try {
            return new ResponseEntity<>(productService.insertProduct(productDTO),HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception while inserting the product with productId:{} :: Error Message= {}",productDTO.getProductId(),e.getMessage());
            throw e;
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Inside updateProduct rest call");
        try {
            return new ResponseEntity<>(productService.updateProduct(productDTO),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while updating the product with productId:{} :: Error Message= {}",productDTO.getProductId(),e.getMessage());
            throw e;
        }
    }

    @PostMapping("/insertVariantByProductId/{productId}")
    public ResponseEntity<?> upsertVariantByProductId(@PathVariable String productId , @RequestBody  List<ProductVariantDTO> variantDTOList) {
        logger.info("Inside insertVariantByProductId rest call");
        try {
            return new ResponseEntity<>(productService.upsertVariantByProductId(productId,variantDTOList),HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception while inserting/updating the variant list with productId:{} :: Error Message= {}",productId,e.getMessage());
            throw e;
        }
    }




}
