package fr.iut.persistence.dao.impl;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public class GenericDAOImpl<T, Id extends Serializable> implements GenericDAO<T, Id> {

    private Class<T> persistentClass;
    protected Session session = null;

    public GenericDAOImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveOrUpdate(T entity) {
        session.saveOrUpdate(entity);
    }

    @Transactional(rollbackOn = Exception.class)
    public T findById(Id id) {
        return session.find(persistentClass, id);
    }

    @Transactional(rollbackOn = Exception.class)
    public List<T> findAll() {
        CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(persistentClass);
        criteria.select(criteria.from(persistentClass));

        return session.createQuery(criteria).getResultList();
    }

    @Transactional(rollbackOn = Exception.class)
    public void remove(T entity) {
        session.remove(entity);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeAll() {

        List<T> all = findAll();
        for (T i : all)
            session.remove(i);

    }

    public void open() {

        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {
        session.close();
    }
}
