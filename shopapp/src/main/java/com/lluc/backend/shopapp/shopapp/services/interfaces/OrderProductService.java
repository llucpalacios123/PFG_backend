package com.lluc.backend.shopapp.shopapp.services.interfaces;

import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;
@Service
public interface OrderProductService {
    OrderProduct updateProductStatus(Long orderProductId, String status);
}