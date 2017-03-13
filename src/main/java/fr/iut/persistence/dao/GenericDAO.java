package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;
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
    protected Session session = HibernateUtil.getSession();

    /**
     * Constructor.
     * @param persistentClass Entity class to handle by the instance.
     */
    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Inserts an entity in database.
     * @param entity new entity.
     * @return entity inserted
     */
    @Transactional(rollbackOn = Exception.class)
    public T save(T entity) {

        session.save(entity);

        return entity;
    }

    /**
     * Updates an entity in database
     * @param entity entity to update
     * @return entity updated
     */
    @Transactional(rollbackOn = Exception.class)
    public T update(T entity) {

        session.merge(entity);

        return entity;
    }

    /**
     * Removes an existing entity from database.
     * @param entity Entity to remove.
     */
    @Transactional(rollbackOn = Exception.class)
    public void remove(T entity) {

        session.beginTransaction();

        try{
            session.delete(entity);
            session.getTransaction().commit();

        } catch (Exception e) {

            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    /**
     * Removes all entities from database.
     */     
    public void removeAll() {

        session.beginTransaction();

        try {
            List<T> all = findAll();
            all.forEach(session::delete);
            session.getTransaction().commit();

        } catch(Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
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
}
