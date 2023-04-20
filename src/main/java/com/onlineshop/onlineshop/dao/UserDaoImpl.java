package com.onlineshop.onlineshop.dao;

import entity.User;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserDaoImpl extends JpaDaoImpl<User, Integer> implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static UserDaoImpl userDao;

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public List<User> findByRole(String role) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            TypedQuery<User> userByUsernameAndPass = entityManager.createNamedQuery("User.byRole", User.class);
            userByUsernameAndPass.setParameter(1, role);
            List<User> foundUsers = userByUsernameAndPass.getResultList();
            transaction.commit();
            return foundUsers;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> userByUsername = entityManager.createNamedQuery("User.byUsername", User.class);
            userByUsername.setParameter(1, username);
            List<User> foundUsers = userByUsername.getResultList();
            if (foundUsers.size() > 0) {
                return foundUsers.get(0);
            }
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public User findByEmail(String email) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> userByEmail = entityManager.createNamedQuery("User.byEmail", User.class);
            userByEmail.setParameter(1, email);
            List<User> foundUsers = userByEmail.getResultList();
            if (foundUsers.size() > 0) {
                return foundUsers.get(0);
            }
            return null;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}