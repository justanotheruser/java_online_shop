package com.onlineshop.onlineshop.services;

import entity.Order;
import entity.OrderItem;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order, List<OrderItem> orderItems);
}
