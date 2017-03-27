package fr.iut.persistence.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Sydpy on 2/27/17.
 */
public class HibernateUtil {

    /**
     * Hibernate's entity manager factory.
     */
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("campingUnit");


    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void shutdown() {
        entityManagerFactory.close();
    }
}
