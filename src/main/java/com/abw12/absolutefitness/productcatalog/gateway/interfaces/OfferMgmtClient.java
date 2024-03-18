package com.abw12.absolutefitness.productcatalog.gateway.interfaces;

import com.abw12.absolutefitness.productcatalog.dto.CalcOfferRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "offermgmt-offer-controller")
public interface OfferMgmtClient {

    @PostMapping("/calculateDiscount")
    ResponseEntity<Map<String,Object>> calculateDiscount(@RequestBody CalcOfferRequest request);

    @PostMapping("/mapVariantIdToOffer")
    ResponseEntity<Map<String,Object>> mapVariantIdToOffer(@RequestBody Map<String,Object> request);

    @GetMapping("/getOfferDetails/{offerId}")
    ResponseEntity<Map<String,Object>> getOfferDetails(@PathVariable String offerId);
}
