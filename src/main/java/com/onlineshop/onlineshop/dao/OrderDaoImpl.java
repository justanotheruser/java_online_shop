package com.onlineshop.onlineshop.dao;

import entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.Collection;
import java.util.List;

public class OrderDaoImpl extends JpaDaoImpl<Order, Integer> implements OrderDao {
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);
    private static OrderDaoImpl orderDao;


    private OrderDaoImpl() {
    }

    public static OrderDao getInstance() {
        if (orderDao == null) {
            orderDao = new OrderDaoImpl();
        }
        return orderDao;
    }

    @Override
    public Collection<Order> getUsersOrders(int userId) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Order> query = entityManager.createNamedQuery("Orders.byUser", Order.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Order> getAllWithUsers() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Order> q = em.createQuery("SELECT o FROM Order o LEFT JOIN FETCH o.user", Order.class);
        List<Order> orders = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return orders;
    }
}