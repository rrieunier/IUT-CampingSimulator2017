package fr.iut.persistence.dao.impl;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Override
    public boolean saveOrUpdate(T entity) {
        session.beginTransaction();

        try {
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }

        return true;
    }

    @Override
    public T findById(Id id) {
        session.beginTransaction();

        T entity = null;

        try {
            entity = session.find(persistentClass, id);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return entity;
    }

    @Override
    public List<T> findAll() {
        session.beginTransaction();

        List<T> entities = new ArrayList<T>();
        try {
            CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(persistentClass);
            criteria.select(criteria.from(persistentClass));

            entities = session.createQuery(criteria).getResultList();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return entities;
    }

    @Override
    public boolean remove(T entity) {
        session.beginTransaction();

        try {
            session.remove(entity);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }

        return true;
    }

    @Override
    public boolean removeAll() {

        session.beginTransaction();

        try{
            List<T> all = findAll();
            for(T i : all)
                session.remove(i);

            session.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }


        return true;
    }

    public void open(){

        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close(){
        session.close();
    }
}
