package com.onlineshop.onlineshop.services;


import com.onlineshop.onlineshop.dao.UserDao;
import com.onlineshop.onlineshop.dao.UserDaoImpl;
import entity.User;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    private final UserDao userDao = UserDaoImpl.getInstance();

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public void save(User user) throws UserServiceException {
        User existingUser = userDao.findByUsername(user.getUsername());
        if (existingUser != null) {
            String errorMessageBuilder = "Пользователь с именем '" + user.getUsername() + "' уже существует";
            throw new UserServiceException(errorMessageBuilder);
        }
        existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser != null) {
            String errorMessageBuilder = "Пользователь с почтой '" + user.getEmail() + "' уже существует";
            throw new UserServiceException(errorMessageBuilder);
        }
        userDao.save(user);
    }
}
