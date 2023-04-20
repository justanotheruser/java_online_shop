package com.onlineshop.onlineshop.services;

import com.onlineshop.onlineshop.dao.*;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl orderService;
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    @Override
    @Transactional
    public void saveOrder(Order order, List<OrderItem> orderItems) {
        order.setUser(userDao.findById(order.getUserId()));
        for (OrderItem orderItem : orderItems) {
            order.getOrderItems().add(orderItem);
        }
        orderDao.save(order);
        for (OrderItem orderItem : orderItems) {
            Item item = itemDao.findById(orderItem.getItem().getId());
            int quantity = item.getQuantity();
            quantity -= orderItem.getQuantity();
            if (quantity < 0) {
                quantity = 0;
            }
            item.setQuantity(quantity);
            itemDao.update(item);
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
