package com.onlineshop.onlineshop.services;

import com.onlineshop.onlineshop.dao.OrderDao;
import com.onlineshop.onlineshop.dao.OrderDaoImpl;
import com.onlineshop.onlineshop.dao.OrderItemDao;
import com.onlineshop.onlineshop.dao.OrderItemDaoImpl;
import entity.Order;
import entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final OrderItemDao orderItemDao = OrderItemDaoImpl.getInstance();
    private static OrderServiceImpl orderService;

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    @Override
    @Transactional
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        orderDao.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItemDao.save(orderItem);
        }
    }
}
