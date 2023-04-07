package com.onlineshop.onlineshop.dao;

import java.util.Collection;

public interface JpaDao<T, ID> {
    T findById(ID id);
    Collection<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    void clear();
}