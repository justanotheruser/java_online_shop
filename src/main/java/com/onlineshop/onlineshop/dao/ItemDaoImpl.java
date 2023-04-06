package com.onlineshop.onlineshop.dao;

import entity.Item;

public class ItemDaoImpl extends JpaDaoImpl<Item, Integer> implements ItemDao {
    private static ItemDaoImpl itemDao;

    private ItemDaoImpl() {}

    public static ItemDao getInstance() {
        if(itemDao == null) {
            itemDao = new ItemDaoImpl();
        }
        return itemDao;
    }

}