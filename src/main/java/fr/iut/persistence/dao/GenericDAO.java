package fr.iut.persistence.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sydpy on 2/17/17.
 */
public interface GenericDAO<T, Id extends Serializable> {

    void saveOrUpdate(T entity);

    T findById(Id id);

    List findAll();

    void remove(T entity);

    void removeAll();
}
