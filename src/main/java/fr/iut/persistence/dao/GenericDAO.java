package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;
import fr.iut.persistence.entities.Log;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public class GenericDAO<T extends EntityModel, Id> {

    private Class<T> persistentClass;
    protected Session session = null;

    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public void open() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void close() {

        session.close();
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveOrUpdate(T entity) {
        String action = "INSERTED or UPDATED";

        session.saveOrUpdate(entity);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog(entity, action);
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public void remove(T entity) {
        session.remove(entity);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog(entity, "REMOVED");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeAll() {
        List<T> all = findAll();
        for (T i : all)
            session.remove(i);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog("REMOVED ALL ENTITIES");
        }

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

    protected void newLog(T entity, String action) {
        StringBuilder logSB = new StringBuilder();
        logSB.append(action).append(" ")
                .append(" on ").append(persistentClass.toString())
                .append(" entity with id ").append(entity.getId());

        Log log = new Log();
        log.setAction(logSB.toString());
        log.setEmployee(EmployeeDAO.getConnectedUser());

        session.saveOrUpdate(log);

        System.out.println(log.getAction());
    }

    protected void newLog(String action) {
        StringBuilder logSB = new StringBuilder();
        logSB.append(action).append(" ")
                .append(" on ").append(persistentClass.toString());

        Log log = new Log();
        log.setAction(logSB.toString());
        log.setEmployee(EmployeeDAO.getConnectedUser());

        session.saveOrUpdate(log);

        System.out.println(log.getAction());
    }
}
