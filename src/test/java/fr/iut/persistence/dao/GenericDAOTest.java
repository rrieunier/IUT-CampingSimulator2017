package fr.iut.persistence.dao;

import fr.iut.persistence.dao.exception.InvalidLoginPasswordException;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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
            employeeDAO.save(employee);

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

        dao.save(client);
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

        dao.save(client);

        Client saved = dao.findById(client.getId());

        saved.setFirstname(DAOTestUtils.randomString());
        dao.update(saved);

        Client updated = dao.findById(saved.getId());
        
        assertNotNull(updated);
        assertNotNull(updated.getFirstname());
        assertNotNull(updated.getId());
        assertEquals(saved.getFirstname(), updated.getFirstname());
        assertEquals(saved.getId(), updated.getId());

    }

    @Test
    public void testRemove() throws Exception {

        GenericDAO<Client, Integer> clientDao = new GenericDAO<>(Client.class);
        int initialCount = clientDao.findAll().size();

        Client client = new Client();
        client.setFirstname(DAOTestUtils.randomString());
        client.setLastname(DAOTestUtils.randomString());

        clientDao.save(client);

        assertEquals(initialCount + 1, clientDao.findAll().size());

        Client client1 = new Client();
        client1.setFirstname(DAOTestUtils.randomString());
        client1.setLastname(DAOTestUtils.randomString());

        clientDao.save(client1);

        assertEquals(initialCount + 2, clientDao.findAll().size());

        clientDao.remove(client);

        assertNull(clientDao.findById(client.getId()));

        clientDao.removeAll();

        assertEquals(0, clientDao.findAll().size());
    }
}