package com.onlineshop.onlineshop.services;

import com.onlineshop.onlineshop.dao.ItemDao;
import com.onlineshop.onlineshop.dao.ItemDaoImpl;

import java.util.Collection;

public class ItemServiceImpl implements ItemService {
    private static ItemServiceImpl itemService;
    private final ItemDao itemDao = ItemDaoImpl.getInstance();

    public static ItemService getInstance() {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        return itemService;
    }

    @Override
    public Collection<String> getCategories() {
        return itemDao.getUniqueCategories();
    }
}
