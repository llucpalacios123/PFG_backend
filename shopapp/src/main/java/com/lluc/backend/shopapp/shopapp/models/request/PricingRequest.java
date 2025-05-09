package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PricingRequest {

    private String categoryName; 
    private List<PricingValueRequest> values; 
}