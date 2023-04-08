package com.onlineshop.onlineshop.dao;

import entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.util.Collection;

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

}