package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;

import javax.persistence.EntityManager;
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

        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        em.getTransaction().begin();

        em.persist(entity);
        em.flush();
        em.clear();

        em.getTransaction().commit();

        em.close();

        return entity;
    }

    /**
     * Updates an entity in database
     *
     * @param entity entity to update
     * @return entity updated
     */
    public T update(T entity) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        em.getTransaction().begin();

        em.persist(em.merge(entity));
        em.flush();
        em.clear();

        em.getTransaction().commit();

        em.close();

        return entity;
    }

    /**
     * Removes an existing entity from database.
     *
     * @param entity Entity to remove.
     */
    public void remove(T entity) {

        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.flush();
        em.clear();
        em.getTransaction().commit();

        em.close();
    }

    /**
     * Removes all entities from database.
     */
    public void removeAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        em.getTransaction().begin();

        for (T t : findAll()) {
            em.remove(em.merge(t));
        }

        em.flush();
        em.clear();

        em.getTransaction().commit();

        em.close();
    }

    /**
     * Finds an entity by its id.
     *
     * @param id Id of the Entity to find.
     * @return Entity with the matching Id.
     */
    public T findById(Id id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        T t = em.find(persistentClass, id);

        em.close();

        return t;
    }

    /**
     * @return List of all the entities.
     */
    public List<T> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(persistentClass);
        criteria.select(criteria.from(persistentClass));

        List<T> resultList = em.createQuery(criteria).getResultList();

        em.close();

        return resultList;
    }

}
