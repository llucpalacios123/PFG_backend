package com.lluc.backend.shopapp.shopapp.controllers;

import com.lluc.backend.shopapp.shopapp.models.entities.Order;
import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;
import com.lluc.backend.shopapp.shopapp.models.request.OrderRequest;
import com.lluc.backend.shopapp.shopapp.services.interfaces.OrderProductService;
import com.lluc.backend.shopapp.shopapp.services.interfaces.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private OrderService orderService;

    // Endpoint para actualizar el estado de un producto en un pedido
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateProductStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            OrderProduct updatedOrderProduct = orderProductService.updateProductStatus(id, status);
            return ResponseEntity.ok(updatedOrderProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para crear un pedido (incluye productos)
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Obtener el usuario autenticado
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            // Llamar al servicio para crear el pedido
            Order savedOrder = orderService.createOrder(username, orderRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder.getOrderId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
}