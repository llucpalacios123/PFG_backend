package com.lluc.backend.shopapp.shopapp.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long addressId; // ID de la dirección seleccionada
    private List<ProductRequest> products; // Lista de productos en el pedido

    @Getter
    @Setter
    public static class ProductRequest {
        private Long productId; // ID del producto
        private String category; // Categoría del producto (e.g., talla o tipo)
        private int quantity; // Cantidad del producto
    }
}