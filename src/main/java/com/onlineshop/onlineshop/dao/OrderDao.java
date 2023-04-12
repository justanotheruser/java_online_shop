package com.onlineshop.onlineshop.dao;

import entity.Order;

import java.util.Collection;

public interface OrderDao extends JpaDao<Order, Integer> {
    Collection<Order> getUsersOrders(int userId);
    Collection<Order> getAllWithUsers();
}
