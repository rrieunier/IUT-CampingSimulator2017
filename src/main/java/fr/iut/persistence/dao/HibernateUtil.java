package fr.iut.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Sydpy on 2/27/17.
 */
public class HibernateUtil {

    /**
     * Configs that can be used.
     */
    public enum Config {
        PROD, TEST
    }

    /**
     * Hibernate's Session.
     */
    private static Session session = null;

    /**
     * Initialize a sessionFactory.
     * @param config Config to use.
     * @return the sessionFactory created.
     */
   private static SessionFactory buildSessionFactory(Config config) {
        try {
            if (config == Config.TEST) {
                return new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
            } else if (config == Config.PROD) {
                return new Configuration().configure().buildSessionFactory();
            }
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        return null;
    }

    public static Session openSession(Config config){
        session = buildSessionFactory(config).openSession();
        return session;
    }

    public static Session getSession(){
        return session;
    }
}
