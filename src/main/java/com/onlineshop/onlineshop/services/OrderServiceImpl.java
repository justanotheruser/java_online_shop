package com.onlineshop.onlineshop.services;

import com.onlineshop.onlineshop.dao.OrderDao;
import com.onlineshop.onlineshop.dao.OrderDaoImpl;
import entity.Order;
import entity.OrderItem;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl orderService;
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    @Override
    @Transactional
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            order.getOrderItems().add(orderItem);
        }
        orderDao.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
    }

    @Override
    public Collection<Order> getCustomerOrders(int userId) {
        return orderDao.getUsersOrders(userId);
    }

    @Override
    public Collection<OrderItem> getFullOrderInfo(int orderId) {
        Order order = orderDao.findById(orderId);
        if (order == null) {
            return null;
        }
        return order.getOrderItems();
    }
}
