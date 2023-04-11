package com.onlineshop.onlineshop.dao;

import entity.Item;

import java.util.Collection;

public interface ItemDao extends JpaDao<Item, Integer> {
    Collection<String> getUniqueCategories();
    Collection<Item> findByCategory(String category);
    Collection<Item> findByPartNumber(String partNumber, String category);
    Collection<Item> findWithDescriptionLike(String keyword, String category);
}
