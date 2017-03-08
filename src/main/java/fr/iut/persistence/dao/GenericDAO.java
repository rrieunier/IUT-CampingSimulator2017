package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;
import fr.iut.persistence.entities.Log;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public class GenericDAO<T extends EntityModel, Id> {

    /**
     * Entity class handled by this instance.
     */
    private Class<T> persistentClass;
    /**
     * Hibernate session.
     */
    protected Session session = null;

    /**
     * Constructor.
     * @param persistentClass Entity class to handle byt he instance.
     */
    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Opens a new session.
     */
    public void open() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    /**
     * Closes the session.
     */
    public void close() {

        session.close();
    }

    /**
     * Inserts or update an entity in database.
     * @param entity already existing or new entity.
     */
    @Transactional(rollbackOn = Exception.class)
    public void saveOrUpdate(T entity) {
        String action = "INSERTED or UPDATED";

        session.saveOrUpdate(entity);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog(entity, action);
        }

    }

    /**
     * Removes an existing entity from database.
     * @param entity Entity to remove.
     */
    @Transactional(rollbackOn = Exception.class)
    public void remove(T entity) {
        session.remove(entity);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog(entity, "REMOVED");
        }
    }

    /**
     * Removes all entities from database.
     */
    @Transactional(rollbackOn = Exception.class)
    public void removeAll() {
        List<T> all = findAll();
        for (T i : all)
            session.remove(i);

        if (EmployeeDAO.getConnectedUser() != null) {
            newLog("REMOVED ALL ENTITIES");
        }

    }

    /**
     * Finds an entity by its id.
     * @param id Id of the Entity to find.
     * @return Entity with the matching Id.
     */
    @Transactional(rollbackOn = Exception.class)
    public T findById(Id id) {
        return session.find(persistentClass, id);
    }

    /**
     * @return List of all the entities.
     */
    @Transactional(rollbackOn = Exception.class)
    public List<T> findAll() {
        CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(persistentClass);
        criteria.select(criteria.from(persistentClass));

        return session.createQuery(criteria).getResultList();
    }

    /**
     * Creates a new log concerning an entity.
     * @param entity Entity concerned.
     * @param action Action applied to this entity (e.g. : INSERTED, UPDATED, ...).
     */
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

    /**
     * Creates a new log.
     * @param action Action to log (e.g. : REMOVED ALL, ....)
     */
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
