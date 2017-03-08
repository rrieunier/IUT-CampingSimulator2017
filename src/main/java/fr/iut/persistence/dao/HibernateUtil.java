package fr.iut.persistence.dao;
import fr.iut.persistence.entities.Employee;
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
     * Hibernate's SessionFactory.
     */
    private static SessionFactory sessionFactory = null;

    /**
     * Initialize the sessionFactory field.
     * @param config Confiig to use.
     * @return the sessionFactory created.
     */
    private static SessionFactory buildSessionFactory(Config config) {
        try {
            if (config == Config.TEST) {
                return new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
            } else if (config == Config.PROD) {
                return new Configuration().configure().buildSessionFactory();
            }
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        return null;
    }

    /**
     * @return the session factory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Close the session factory.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }

    /**
     * Call buildSessionFactory with a specified config.
     * @param config Config to use.
     */
    public static void setConfig(Config config){
        sessionFactory = buildSessionFactory(config);

        if(config == Config.TEST){
            Session session = sessionFactory.openSession();

            Employee employee = new Employee();
            employee.setFirstName("test");
            employee.setLastName("test");
            employee.setLogin("test");
            employee.setPassword("test");

            session.saveOrUpdate(employee);
            session.close();
        }
    }

}
