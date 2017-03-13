package fr.iut.persistence.dao;

import fr.iut.persistence.dao.exception.InvalidLoginPasswordException;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Log;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sydpy on 3/6/17.
 */
public class GenericDAOTest {


    public GenericDAOTest() throws InvalidLoginPasswordException {

        HibernateUtil.setConfig(HibernateUtil.Config.TEST);

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.open();
        assert employeeDAO.connectUser("test","test") != null;
        employeeDAO.close();
    }

    @Test
    public void testPersistence() throws Exception {

        Client client = new Client();
        client.setId(12345678);
        client.setFirstname("Patrick");
        client.setLastname("Felix");

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);
        dao.open();

        dao.saveOrUpdate(client);
        Client saved = dao.findById(client.getId());

        assertNotNull(saved);
        assertNotNull(saved.getFirstname());
        assertNotNull(saved.getLastname());
        assertEquals(client.getFirstname(), saved.getFirstname());
        assertEquals(client.getLastname(), saved.getLastname());

        dao.close();
    }

    @Test
    public void testUpdate() throws Exception {

        HibernateUtil.setConfig(HibernateUtil.Config.TEST);

        Client client = new Client();
        client.setId(67906);
        client.setFirstname("Hervé");
        client.setLastname("Bebert");

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);
        dao.open();

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

        dao.close();
    }

    @Test
    public void testLogCreation() throws Exception {

        GenericDAO<Log, Integer> logDao = new GenericDAO<>(Log.class);
        logDao.open();
        int initialLogCount = logDao.findAll().size();
        logDao.close();

        Client client = new Client();
        client.setFirstname("azertyu");
        client.setLastname("azertyu");

        GenericDAO<Client, Integer> clientDao = new GenericDAO<>(Client.class);
        clientDao.open();
        clientDao.saveOrUpdate(client);
        clientDao.close();

        logDao.open();
        assertEquals(initialLogCount + 1, logDao.findAll().size());
        logDao.close();
    }
}