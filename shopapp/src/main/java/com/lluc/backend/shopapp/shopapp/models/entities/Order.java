package com.lluc.backend.shopapp.shopapp.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private String orderId; // Identificador único del pedido

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter private User user; // Cliente que realiza el pedido

    @Getter @Setter private String street;
    @Getter @Setter private String city;
    @Getter @Setter private String state;
    @Getter @Setter private String postalCode;
    @Getter @Setter private String country;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private List<OrderProduct> products; // Productos en el pedido

    @Getter @Setter private LocalDateTime orderDate; // Fecha del pedido

    @Getter @Setter private String status; // Estado del pedido (e.g., "PENDING", "COMPLETED")
}