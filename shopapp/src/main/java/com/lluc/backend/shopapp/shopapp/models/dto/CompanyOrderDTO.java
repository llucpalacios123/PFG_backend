package com.lluc.backend.shopapp.shopapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyOrderDTO {
    private String orderId; // ID del pedido
    private String orderStatus; // Estado del pedido
    private List<OrderProductDTO> products; // Lista de productos en el pedido

    @Getter
    @Setter
    public static class OrderProductDTO {
        private long productId; // ID del producto
        private String productName;
        private String category;
        private int quantity;
        private String status;
    }
}