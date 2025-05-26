package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private String orderId;
    private LocalDateTime orderDate;
    private String status;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private List<OrderProductDTO> products;

    @Getter
    @Setter
    public static class OrderProductDTO {
        private long productId;
        private long orderProductId;
        private String productName;
        private String category;
        private int quantity;
        private String status;
        private String imageUrl;
        private int companyId;
    }
}