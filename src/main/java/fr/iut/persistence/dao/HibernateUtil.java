package fr.iut.persistence.dao;
import fr.iut.persistence.entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Sydpy on 2/27/17.
 */
public class HibernateUtil {

    public enum Config {
        PROD, TEST
    }

    private static SessionFactory sessionFactory = null;

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

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

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
