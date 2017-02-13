package fr.iut.dao;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Sydpy on 2/13/17.
 */
public class CampingDAO<T> {

    private final Class<T> entityClass;
    private Session currentSession;
    private Transaction currentTransaction;

    public CampingDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Session openSession(){
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public void closeSession(){
        if (currentSession == null || !currentSession.isOpen())
            throw new SessionException("Session is not opened");

        currentSession.close();
    }

    public Transaction beginTransaction(){
        if (currentSession == null || !currentSession.isOpen())
            throw new SessionException("Session is not opened");

        currentTransaction = currentSession.beginTransaction();
        return currentTransaction;
    }

    public void commitTransaction(){
        if (currentTransaction == null || !currentTransaction.isActive())
            throw new TransactionException("Transaction has not began");

        currentTransaction.commit();
    }

    public void rollbackTransaction(){
        if (currentTransaction == null || !currentTransaction.isActive())
            throw new TransactionException("Transaction has not began");

        currentTransaction.rollback();
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    public T findById(int id) {
        if (currentSession == null || !currentSession.isOpen())
            throw new SessionException("Session is not opened");

        return currentSession.get(entityClass, id);
    }

    public List<T> findAll() {
        if (currentSession == null || !currentSession.isOpen())
            throw new SessionException("Session is not opened");

        return currentSession.createQuery(entityClass.getSimpleName()).list();
    }

    public void persist(T entity) {
        if (currentTransaction == null || !currentTransaction.isActive())
            throw new TransactionException("Transaction has not began");

        currentSession.persist(entity);
    }

    public void update(T entity) {
        if (currentTransaction == null || !currentTransaction.isActive())
            throw new TransactionException("Transaction has not began");

        currentSession.update(entity);
    }

    public void delete(T entity){
        if (currentTransaction == null || !currentTransaction.isActive())
            throw new TransactionException("Transaction has not began");

        currentSession.delete(entity);
    }
}
