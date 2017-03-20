package fr.iut.persistence.dao;

import fr.iut.persistence.TestUtils;
import fr.iut.persistence.entities.Client;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sydpy on 3/6/17.
 */
public class GenericDAOTest {

    @Test
    public void testPersistence() throws Exception {

        Client client = new Client();
        client.setId(12345678);
        client.setFirstname(TestUtils.randomString());
        client.setLastname(TestUtils.randomString());

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
        client.setFirstname(TestUtils.randomString());
        client.setLastname(TestUtils.randomString());

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);

        dao.save(client);

        Client saved = dao.findById(client.getId());

        saved.setFirstname(TestUtils.randomString());
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
        client.setFirstname(TestUtils.randomString());
        client.setLastname(TestUtils.randomString());

        clientDao.save(client);

        assertEquals(initialCount + 1, clientDao.findAll().size());

        Client client1 = new Client();
        client1.setFirstname(TestUtils.randomString());
        client1.setLastname(TestUtils.randomString());

        clientDao.save(client1);

        assertEquals(initialCount + 2, clientDao.findAll().size());

        clientDao.remove(client);

        assertNull(clientDao.findById(client.getId()));

        clientDao.removeAll();

        assertEquals(0, clientDao.count());
    }
}