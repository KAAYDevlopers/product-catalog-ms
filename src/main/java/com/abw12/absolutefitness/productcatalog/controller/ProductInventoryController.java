package com.abw12.absolutefitness.productcatalog.controller;

import com.abw12.absolutefitness.productcatalog.dto.InventoryValidationReq;
import com.abw12.absolutefitness.productcatalog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatalog.mappers.InventoryValidationDTOMapper;
import com.abw12.absolutefitness.productcatalog.service.ProductInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/catalog/inventory")
public class ProductInventoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductInventoryController.class);

    @Autowired
    private ProductInventoryService inventoryService;

    @GetMapping("/getVariantInventory/{variantId}")
    public ResponseEntity<?> getVariantInventoryData(@PathVariable String variantId){
        logger.info("Inside getVariantInventoryData rest call: {} " , variantId);
        try{
            return new  ResponseEntity<>(inventoryService.getVariantById(variantId), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while fetching variant inventory data with variantId : {}", e.getMessage());
            throw e; //re-throw exception handle at global level
        }
    }

    @PostMapping("/saveInventoryData")
    public ResponseEntity<?> updateVariantInventory(@RequestBody ProductInventoryDTO inventoryDTO){
        logger.info("Inside updateVariantInventory rest call: {} " , inventoryDTO);
        try{
            return new ResponseEntity<>(inventoryService.updateVariantInventoryData(inventoryDTO), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while Inserting/updating variant inventory data with variantId : {}", e.getMessage());
            throw e;
        }
    }
    @GetMapping("/checkStockStatus")
    public ResponseEntity<?> getVariantInventoryStatus(@RequestParam Map<String,Object> params){
        logger.info("Inside checkStockStatus rest call : {}",params);
        InventoryValidationReq request = InventoryValidationDTOMapper.mapRequestParamsToDTO(params);
        try{
            return new ResponseEntity<>(inventoryService.variantsValidation(request), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Exception while validating availability of variants: {} => {}", e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    @RequestMapping(method = RequestMethod.PATCH,value = "/updateInventoryData")
    public ResponseEntity<?> patchUpdateInventoryData(@RequestBody Map<String,Object> params){
        logger.info("Inside patchUpdateInventoryData rest call :: {}",params);
        try{
            Map<String, Object> responseMap = inventoryService.patchVariantInventoryData(params);
            if(responseMap.containsKey("SuccessMsg"))
                return new ResponseEntity<>(responseMap,HttpStatus.OK);
            else
                return new ResponseEntity<>(responseMap,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.error("Exception while validating availability of variants: {} => {}", e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

}
