package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
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
     *
     * @param persistentClass Entity class to handle by the instance.
     */
    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Inserts an entity in database.
     *
     * @param entity new entity.
     * @return entity inserted
     */
    public T save(T entity) {

        session.save(entity);

        return entity;
    }

    /**
     * Updates an entity in database
     *
     * @param entity entity to update
     * @return entity updated
     */
    public T update(T entity) {

        session.update(entity);

        return entity;
    }

    /**
     * Removes an existing entity from database.
     *
     * @param entity Entity to remove.
     */
    public void remove(T entity) {

        session.beginTransaction();

        try {
            session.remove(entity);
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
            for (T t : findAll()) {
                session.remove(t);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

    }

    /**
     * Finds an entity by its id.
     *
     * @param id Id of the Entity to find.
     * @return Entity with the matching Id.
     */
    public T findById(Id id) {
        return session.find(persistentClass, id);
    }

    /**
     * @return List of all the entities.
     */
    public List<T> findAll() {
        CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(persistentClass);
        criteria.select(criteria.from(persistentClass));

        return session.createQuery(criteria).getResultList();
    }

    /**
     * @return the number of entities T in database
     */
    public int count() {

        String query = "select count(e.id) from " + persistentClass.getName() + " e";

        return session.createQuery(query)
                .getFirstResult();
    }
}
