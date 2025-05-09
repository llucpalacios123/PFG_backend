package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PricingValueRequest {

    private String categoryValue; 
    private BigDecimal price; 
    private BigDecimal cost; 
    private Integer stock; 
}