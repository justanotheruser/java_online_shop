package com.onlineshop.onlineshop.services;

import entity.User;

public interface UserService {
    void save(User user) throws UserServiceException;
}
