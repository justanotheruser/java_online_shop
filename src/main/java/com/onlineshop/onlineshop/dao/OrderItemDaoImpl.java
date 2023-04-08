package com.onlineshop.onlineshop.dao;

import entity.Item;
import entity.OrderItem;

public class OrderItemDaoImpl extends JpaDaoImpl<OrderItem, Integer> implements OrderItemDao {
    private static OrderItemDaoImpl orderItemDao;

    private OrderItemDaoImpl() {}

    public static OrderItemDao getInstance() {
        if(orderItemDao == null) {
            orderItemDao = new OrderItemDaoImpl();
        }
        return orderItemDao;
    }
}
