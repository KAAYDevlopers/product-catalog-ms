package com.abw12.absolutefitness.productcatalog.helper;

import com.abw12.absolutefitness.productcatalog.constants.CommonConstants;
import com.abw12.absolutefitness.productcatalog.dto.*;
import com.abw12.absolutefitness.productcatalog.gateway.interfaces.OfferMgmtClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class Utils {
    @Autowired
    private OfferMgmtClient offerMgmtClient;
    @Autowired
    private ObjectMapper objectMapper;


    private static final Logger logger = LoggerFactory.getLogger(Utils.class);


    public static DateTimeFormatter dateFormat(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    public CalcOfferRequest prepareCalcOfferRequest(ProductCategoryDTO productCategoryDTO, ProductDTO productDTO, ProductVariantDTO variantDTO) {
            CalcOfferRequest request =new CalcOfferRequest();
            if(variantDTO.getOffer()!=null && !StringUtils.isEmpty(variantDTO.getOffer().getOfferId()))
                request.setOfferId(variantDTO.getOffer().getOfferId());

            if(!StringUtils.isEmpty(variantDTO.getVariantName()))
                request.setVariantName(variantDTO.getVariantName());

            if(variantDTO.getBuyPrice()!=null)
                request.setBuyPrice(variantDTO.getBuyPrice());

            if(!StringUtils.isEmpty(productDTO.getProductName()))
                request.setProductName(productDTO.getProductName());

            if(!StringUtils.isEmpty(productDTO.getBrandName()))
                request.setBrandName(productDTO.getBrandName());

            if(!StringUtils.isEmpty(productCategoryDTO.getCategoryName()))
                request.setCategoryName(productCategoryDTO.getCategoryName());
            return request;
    }

    public CalcOfferResponse calcOfferApi(CalcOfferRequest request,String variantName){
        logger.info("calling offer-mgmt-ms API to calculate onSalePrice for variantName={}",variantName);
        ResponseEntity<Map<String, Object>> response = offerMgmtClient.calculateDiscount(request);
        if(response.getStatusCode().is2xxSuccessful() && response.hasBody()){
            CalcOfferResponse calcOfferResponse = objectMapper.convertValue(response.getBody(), CalcOfferResponse.class);
            logger.info("Response: offer-mgmt-ms API to calculate discount = {} => {}",variantName,calcOfferResponse.getMsg());
            return calcOfferResponse;
        }else {
            throw new RuntimeException(String.format("Exception while calling offer-mgmt-ms API to calculate discount :: %s => %s",
                    response.getStatusCode(),response.getBody()));
        }
    }

    public void mapVariantIdToOffer(String variantId, String offerId) {
        logger.info("calling offer-mgmt-ms API to map variantId={} to offerId={}",variantId,offerId);
        ResponseEntity<Map<String, Object>> response = offerMgmtClient.mapVariantIdToOffer(Map.of(CommonConstants.VARIANT_ID, variantId, CommonConstants.OFFER_ID, offerId));
        if(response.getStatusCode().is2xxSuccessful() && response.hasBody()){
            logger.info("Response: offer-mgmt-ms API to map variantId to offer is successful => {}",response.getBody());
        }else {
            throw new RuntimeException(String.format("Exception while calling offer-mgmt-ms API to map variantId to offer :: %s => %s",
                    response.getStatusCode(),response.getBody()));
        }
    }

    public OfferDTO fetchOfferDetails(String offerId){
        logger.info("Retrieve Offer Details calling offer-mgmt-ms getOfferDetails API with offerId={}",offerId);
        ResponseEntity<Map<String, Object>> response = offerMgmtClient.getOfferDetails(offerId);
        if(response.getStatusCode().is2xxSuccessful() && response.hasBody()){
            logger.info("Response: offer-mgmt-ms getOfferDetails API with offerId={} => {}",offerId,response.getBody());
            return objectMapper.convertValue(response.getBody(), OfferDTO.class);
        }else {
            throw new RuntimeException(String.format("Exception while calling offer-mgmt-ms getOfferDetails API with offerId=%s :: %s => %s",offerId,
                    response.getStatusCode(),response.getBody()));
        }
    }
}
