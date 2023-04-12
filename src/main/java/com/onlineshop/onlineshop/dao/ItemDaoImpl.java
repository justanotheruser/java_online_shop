package com.onlineshop.onlineshop.dao;

import entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.Collection;
import java.util.Date;

public class ItemDaoImpl extends JpaDaoImpl<Item, Integer> implements ItemDao {
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);
    private static ItemDaoImpl itemDao;

    private ItemDaoImpl() {
    }

    public static ItemDao getInstance() {
        if (itemDao == null) {
            itemDao = new ItemDaoImpl();
        }
        return itemDao;
    }

    @Override
    public Collection<String> getUniqueCategories() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Query query = entityManager.createNativeQuery("SELECT DISTINCT category FROM items");
            return (Collection<String>) query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Item> findByCategory(String category) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Item> query = entityManager.createNamedQuery("Items.byCategory", Item.class);
            query.setParameter(1, category);
            return query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Item> findByPartNumber(String partNumber, String category) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            if (category != null) {
                TypedQuery<Item> query = entityManager.createNamedQuery("Items.byPartNumberAndCategory", Item.class);
                query.setParameter(1, partNumber);
                query.setParameter(2, category);
                return query.getResultList();
            }
            TypedQuery<Item> query = entityManager.createNamedQuery("Items.byPartNumber", Item.class);
            query.setParameter(1, partNumber);
            return query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Item> findWithDescriptionLike(String keyword, String category) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            if (category != null) {
                TypedQuery<Item> query = entityManager.createNamedQuery("Items.byDescriptionAndCategory", Item.class);
                query.setParameter(1, keyword);
                query.setParameter(2, category);
                return query.getResultList();
            }
            TypedQuery<Item> query = entityManager.createNamedQuery("Items.byDescription", Item.class);
            query.setParameter(1, keyword);
            return query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Item> findCreatedAfter(Date date) {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Item> query = entityManager.createNamedQuery("Items.createdAfter", Item.class);
            query.setParameter(1, date);
            return query.getResultList();
        } catch (HibernateException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

}