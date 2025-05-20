package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;
import com.lluc.backend.shopapp.shopapp.repositories.OrderProductRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.OrderProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    @Transactional
    public OrderProduct updateProductStatus(Long orderProductId, String status) {
        // Buscar el producto del pedido por su ID
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId)
                .orElseThrow(() -> new RuntimeException("OrderProduct not found with ID: " + orderProductId));

        // Actualizar el estado del producto
        orderProduct.setStatus(status);

        // Guardar los cambios en la base de datos
        return orderProductRepository.save(orderProduct);
    }
}