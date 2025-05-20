package com.lluc.backend.shopapp.shopapp.services.interfaces;

import com.lluc.backend.shopapp.shopapp.models.entities.Order;
import com.lluc.backend.shopapp.shopapp.models.request.OrderRequest;

public interface OrderService {
    Order createOrder(String username, OrderRequest orderRequest);
}