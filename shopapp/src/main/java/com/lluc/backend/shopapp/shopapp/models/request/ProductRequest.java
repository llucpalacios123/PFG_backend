package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductRequest {

    private Map<String, String> productName; 
    private Map<String, String> description; 
    private BigDecimal taxes; 
    private Long category; 
    private List<Long> sustainableCategory; 
    private List<PricingRequest> pricing; 
    private List<String> photos; 
}