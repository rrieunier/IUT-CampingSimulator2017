package fr.iut.persistence.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public interface GenericDAO<T, Id extends Serializable> {

    boolean persist(T entity);

    boolean update(T entity);

    T findById(Id id);

    List findAll();

    boolean delete(T entity);

    boolean deleteAll();
}
