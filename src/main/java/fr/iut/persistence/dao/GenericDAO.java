package fr.iut.persistence.dao;

import fr.iut.persistence.entities.EntityModel;

import javax.persistence.EntityManager;
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
     * Hibernate entity manager.
     */
    protected EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();

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
    @Transactional
    public T save(T entity) {
        em.getTransaction().begin();

        em.persist(entity);
        em.flush();

        em.getTransaction().commit();
        return entity;
    }

    /**
     * Updates an entity in database
     *
     * @param entity entity to update
     * @return entity updated
     */
    @Transactional
    public T update(T entity) {

        em.getTransaction().begin();

        em.merge(entity);
        em.flush();

        em.getTransaction().commit();

        return entity;
    }

    /**
     * Removes an existing entity from database.
     *
     * @param entity Entity to remove.
     */
    @Transactional
    public void remove(T entity) {

        em.getTransaction().begin();
        em.remove(entity);
        em.flush();
        em.getTransaction().commit();
    }

    /**
     * Removes all entities from database.
     */
    @Transactional
    public void removeAll() {

        em.getTransaction().begin();

        for (T t : findAll()) {
            em.remove(t);
        }

        em.flush();

        em.getTransaction().commit();
    }

    /**
     * Finds an entity by its id.
     *
     * @param id Id of the Entity to find.
     * @return Entity with the matching Id.
     */
    public T findById(Id id) {
        return em.find(persistentClass, id);
    }

    /**
     * @return List of all the entities.
     */
    public List<T> findAll() {
        CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(persistentClass);
        criteria.select(criteria.from(persistentClass));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    protected void finalize() throws Throwable {
        em.close();
        super.finalize();
    }
}
