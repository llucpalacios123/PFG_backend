package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private String query;
    private List<Long> sustainableCategories;
    private int page = 0; 
    private int size = 10; 
    private String sort = "fechaAlta,asc"; 
}