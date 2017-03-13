package fr.iut.persistence.dao;

import fr.iut.persistence.dao.exception.InvalidLoginPasswordException;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sydpy on 3/6/17.
 */
public class GenericDAOTest {


    @BeforeClass
    public static void initDaoIfNeeded() throws InvalidLoginPasswordException {
        if(HibernateUtil.getSession() == null){
            HibernateUtil.openSession(HibernateUtil.Config.TEST);

            Employee employee = new Employee();
            employee.setFirstName(DAOTestUtils.randomString());
            employee.setLastName(DAOTestUtils.randomString());
            employee.setLogin(DAOTestUtils.randomString());
            employee.setPassword(DAOTestUtils.randomString());

            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.saveOrUpdate(employee);

            assert employeeDAO.connectUser(employee.getLogin(), employee.getPassword())!= null;
        }
    }

    public GenericDAOTest() throws InvalidLoginPasswordException {

    }

    @Test
    public void testPersistence() throws Exception {

        Client client = new Client();
        client.setId(12345678);
        client.setFirstname(DAOTestUtils.randomString());
        client.setLastname(DAOTestUtils.randomString());

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);

        dao.saveOrUpdate(client);
        Client saved = dao.findById(client.getId());

        assertNotNull(saved);
        assertNotNull(saved.getFirstname());
        assertNotNull(saved.getLastname());
        assertEquals(client.getFirstname(), saved.getFirstname());
        assertEquals(client.getLastname(), saved.getLastname());

    }

    @Test
    public void testUpdate() throws Exception {

        Client client = new Client();
        client.setId(67906);
        client.setFirstname(DAOTestUtils.randomString());
        client.setLastname(DAOTestUtils.randomString());

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);

        dao.saveOrUpdate(client);

        Client saved = dao.findById(client.getId());

        saved.setFirstname("Patrick");
        dao.saveOrUpdate(saved);

        Client updated = dao.findById(saved.getId());
        
        assertNotNull(updated);
        assertNotNull(updated.getFirstname());
        assertNotNull(updated.getId());
        assertEquals(saved.getFirstname(), updated.getFirstname());
        assertEquals(saved.getId(), updated.getId());

    }

    @Test
    public void testLogCreation() throws Exception {

        GenericDAO<Log, Integer> logDao = new GenericDAO<>(Log.class);
        int initialLogCount = logDao.findAll().size();

        Client client = new Client();
        client.setFirstname(DAOTestUtils.randomString());
        client.setLastname(DAOTestUtils.randomString());

        GenericDAO<Client, Integer> clientDao = new GenericDAO<>(Client.class);
        clientDao.saveOrUpdate(client);

        assertEquals(initialLogCount + 1, logDao.findAll().size());
    }
}