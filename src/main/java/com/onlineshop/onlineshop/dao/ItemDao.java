package com.onlineshop.onlineshop.dao;

import entity.Item;

import java.util.Collection;

public interface ItemDao extends JpaDao<Item, Integer> {
    Collection<String> getUniqueCategories();
}
