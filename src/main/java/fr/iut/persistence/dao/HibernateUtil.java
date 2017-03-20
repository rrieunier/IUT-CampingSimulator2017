package fr.iut.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Sydpy on 2/27/17.
 */
public class HibernateUtil {

    /**
     * Hibernate's Session.
     */
    private static Session session = buildSessionFactory().openSession();

    /**
     * Initialize a sessionFactory.
     *
     * @return the sessionFactory created.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return session;
    }

    public static void shutdown() {
        session.close();
    }
}
