package com.onlineshop.onlineshop.dao;

import entity.User;

import java.util.List;

public interface UserDao extends JpaDao<User, Integer> {
    List<User> findByRole(String role);
    User findByUsername(String username);

    User findByEmail(String email);
}
