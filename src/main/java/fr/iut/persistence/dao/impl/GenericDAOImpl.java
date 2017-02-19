package fr.iut.persistence.dao.impl;

import fr.iut.persistence.dao.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public class GenericDAOImpl<T, Id extends Serializable> implements GenericDAO<T, Id> {

    private Class<T> persistentClass;
    private EntityManager entityManager;

    public GenericDAOImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CampingUnit");
        entityManager = emf.createEntityManager();
    }


    @Override
    public void persist(T entity) {
        entityManager.getTransaction().begin();

        try {
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }

    }

    @Override
    public void update(T entity) {

        entityManager.getTransaction().begin();

        try {
            entityManager.refresh(entity);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        };
    }

    @Override
    public T findById(Id id) {
        entityManager.getTransaction().begin();

        T entity = null;

        try {
            entity = entityManager.find(persistentClass, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }

        return entity;
    }

    @Override
    public List<T> findAll() {
        entityManager.getTransaction().begin();

        List<T> entities = new ArrayList<T>();
        try {
            CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(persistentClass);
            criteria.select(criteria.from(persistentClass));

            entities = entityManager.createQuery(criteria).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }

        return entities;
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
