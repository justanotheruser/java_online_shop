package com.onlineshop.onlineshop.dao;

import entity.Item;

import java.util.Collection;
import java.util.Date;

public interface ItemDao extends JpaDao<Item, Integer> {
    Collection<String> getUniqueCategories();
    Collection<Item> findByCategory(String category);
    Collection<Item> findWithZeroOrLessQuantity();
    Collection<Item> findByPartNumber(String partNumber, String category);
    Collection<Item> findWithDescriptionLike(String keyword, String category);
    Collection<Item> findCreatedAfter(Date date);
}
