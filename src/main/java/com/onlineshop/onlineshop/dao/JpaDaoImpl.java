package com.onlineshop.onlineshop.dao;

import jakarta.persistence.Id;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.lang.reflect.Field;

public abstract class JpaDaoImpl<T, ID> implements JpaDao<T, ID> {
    protected static EntityManagerFactory entityManagerFactory;
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        catch (Throwable e) {
            while (e.getCause() != null) {
                logger.error(e.getCause());
                e = e.getCause();
            }
        }
    }

    private final String FIND_ALL;
    private final Class<T> persistentClass;

    public JpaDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        FIND_ALL = "select e from " + persistentClass.getSimpleName() + " e";
    }

    @Override
    public T findById(ID id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T entity = entityManager.find(persistentClass, id);
        entityManager.close();
        return entity;
    }

    @Override
    public Collection<T> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Collection<T> entities = entityManager.createQuery(FIND_ALL, persistentClass).getResultList();
        entityManager.close();
        return entities;
    }

    @Override
    public T save(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            if (getterId(entity) == null) {
                entityManager.persist(entity);
            } else {
                entityManager.merge(entity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }

        entityManager.close();

        return entity;
    }

    @Override
    public T update(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            if (getterId(entity) == null) {
                entityManager.merge(entity);
            } else {
                entityManager.merge(entity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }

        entityManager.close();

        return entity;
    }

    @Override
    public void delete(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }

        entityManager.close();

    }

    //use a forEach loop and not a delete query because JPA not use the cascade in the query
    @Override
    public void clear() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Collection<T> all = findAll();
            all.stream().map(entityManager::merge)
                    .forEach(entityManager::remove);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    //get the value of the field annotated with @Id
    private ID getterId(T entity) {
        try {
            Field id = Arrays.stream(persistentClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(Id.class))
                    .findAny()
                    .orElse(null);

            assert id != null;
            id.setAccessible(true);

            return (ID) id.get(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
