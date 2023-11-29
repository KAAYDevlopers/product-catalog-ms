package com.abw12.absolutefitness.productcatalog.controller;


import com.abw12.absolutefitness.productcatalog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatalog.dto.ProductVariantDTO;
import com.abw12.absolutefitness.productcatalog.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId){
        logger.info("Inside getProductById rest call: {} " , productId);
        try{
            return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);

        }catch (Exception e){
            logger.error("Exception while fetching product by Id : {}",e.getMessage());
            return new ResponseEntity<>("Exception while fetching product by Id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProductByName/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName){
        logger.info("Inside getProductByName rest call: {} " , productName);
        try{

            return new ResponseEntity<>(productService.getProductByName(productName),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Exception while fetching the product by product name : {} :: Error Message= {}",productName,e.getMessage());
            return new ResponseEntity<>("could not fetch product with name!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{categoryID}")
    public ResponseEntity<?> getProductByCategoryID(@PathVariable String categoryID){
        logger.info("Inside getProductByCategoryID rest call: {} " , categoryID);
        try{
            return  new ResponseEntity<>(productService.getProductsByCategoryId(categoryID),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Exception while fetching the product by catLogId : {} :: Error Message= {}",categoryID,e.getMessage());
            return new ResponseEntity<>("Could not fetch product with categoryID!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getVariantData/{variantId}")
    public ResponseEntity<?> getVariantData(@PathVariable String variantId){
        logger.info("Inside getVariantData rest call");
        try{
            return new ResponseEntity<>(productService.getVariantDataByVariantId(variantId),HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while fetching the variant data with variantId:{} :: Error Message= {}",variantId,e.getMessage());
            return new ResponseEntity<>("Exception while fetching the variant data",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/filterProduct")
    public ResponseEntity<?> productFilters(@RequestBody ProductFiltersDTO productFilters){
        logger.info("Inside filterProduct rest call");
        try{
            return new ResponseEntity<>(productService.filterProduct(productFilters),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Error processing request for filters :: Error Message= {}",productFilters.toString());
            return new ResponseEntity<>("Exception while filtering the products! ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertProduct")
    public ResponseEntity<?> insertProduct(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Inside saveProduct rest call");
        try {
            return new ResponseEntity<>(productService.insertProduct(productDTO),HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception while inserting the product with productId:{} :: Error Message= {}",productDTO.getProductId(),e.getMessage());
            return new ResponseEntity<>("Exception while inserting the product!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        logger.info("Inside updateProduct rest call");
        try {
            return new ResponseEntity<>(productService.updateProduct(productDTO),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while updating the product with productId:{} :: Error Message= {}",productDTO.getProductId(),e.getMessage());
            return new ResponseEntity<>("Exception while updating the product!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertVariantByProductId/{productId}")
    public ResponseEntity<?> upsertVariantByProductId(@PathVariable String productId , @RequestBody  List<ProductVariantDTO> variantDTOList) {
        logger.info("Inside insertVariantByProductId rest call");
        try {
            return new ResponseEntity<>(productService.upsertVariantByProductId(productId,variantDTOList),HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception while inserting/updating the variant list with productId:{} :: Error Message= {}",productId,e.getMessage());
            return new ResponseEntity<>("Exception while inserting/updating the variant list!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
