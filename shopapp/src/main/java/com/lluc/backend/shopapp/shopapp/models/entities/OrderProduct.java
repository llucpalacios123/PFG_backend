package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @Getter @Setter private Order order; // Pedido al que pertenece el producto

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Getter @Setter private Product product; // Producto comprado

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @Getter @Setter private Company company; // Compañía propietaria del producto

    @Getter @Setter private int quantity; // Cantidad del producto

    @Getter @Setter private String status; // Estado del producto (e.g., "PENDING", "SHIPPED", "DELIVERED")

    @Getter @Setter private String category; // Categoría del producto
}