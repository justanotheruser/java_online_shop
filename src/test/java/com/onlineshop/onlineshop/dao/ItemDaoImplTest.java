package com.onlineshop.onlineshop.dao;

import entity.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemDaoImplTest {
    private ItemDao itemDao;

    @BeforeAll
    static void setup() {
    }
    @BeforeEach
    void setUpThis() {
        itemDao = ItemDaoImpl.getInstance();
    }

    @Test
    public void createItemAndFindById() {
        Item item = new Item();
        item.setName("ковырялка для носа");
        item.setPartNumber(1);
        itemDao.save(item);
        int createdItemId = item.getId();
        Item foundItem = itemDao.findById(createdItemId);
        assert item.getName().equals(foundItem.getName());
    }
    @AfterEach
    void tearDownThis() {
        itemDao.clear();
    }
}