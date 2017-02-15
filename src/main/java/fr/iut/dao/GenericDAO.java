package fr.iut.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sydpy on 2/14/17.
 */
public interface GenericDAO<T, Id extends Serializable> {

    /**
     * @return List of all entities T
     */
    public List findAll();

    /**
     * @param id
     * @return return entity T wirh the ID id
     */
    public T findById(Id id);

    /**
     * @param entity to be stored in database
     */
    public void persist(T entity);

    /**
     * @param entity to be updated in database
     */
    public void update(T entity);

    /**
     * @param entity to be deleted in daba
     */
    public void delete(T entity);

}
