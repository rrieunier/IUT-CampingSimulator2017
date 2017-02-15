package fr.iut.dao.impl;

import fr.iut.dao.GenericDAO;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Sydpy on 2/14/17.
 */
public class GenericDAOImpl<T, Id extends Serializable> implements GenericDAO<Object,Id> {

    private EntityManager entityManager = Persistence
            .createEntityManagerFactory("camping-unit")
            .createEntityManager();

    /**
     * Class of the entity to be managed
     */
    private Class<T> entityType;
    /**
     * Class of the ID of the entity
     */
    private Class<Id> idType;

    /**
     * @param entityType
     * @param idType
     */
    public GenericDAOImpl(Class<T> entityType, Class<Id> idType) {

        this.entityType = entityType;
        this.idType = idType;
    }

    /**
     * @return list of all the entities T
     */
    @Override
    public List findAll() {
        List<T> objects = null;
        try {
            Query query = entityManager.createQuery("from " + entityType.getClass());
            objects = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return objects;
    }

    /**
     * @param i
     * @return entity T with ID i
     */
    @Override
    public Object findById(Id i) {
        return entityManager.find(entityType, i);
    }

    /**
     * @param entity to be stored in database
     */
    @Override
    public void persist(Object entity) {
        entityManager.persist(entity);
    }

    /**
     * @param entity to be updated in database
     */
    @Override
    public void update(Object entity) {
        entityManager.refresh(entity);
    }

    /**
     * @param entity to be deleted in database
     */
    @Override
    public void delete(Object entity) {
        entityManager.remove(entity);
    }
}
