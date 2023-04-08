package com.onlineshop.onlineshop.dao;

import entity.Order;

public class OrderDaoImpl extends JpaDaoImpl<Order, Integer> implements OrderDao {
    private static OrderDaoImpl orderDao;

    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        if (orderDao == null) {
            orderDao = new OrderDaoImpl();
        }
        return orderDao;
    }
}