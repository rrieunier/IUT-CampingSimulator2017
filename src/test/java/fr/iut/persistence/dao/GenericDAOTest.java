package fr.iut.persistence.dao;

import fr.iut.persistence.TestUtils;
import fr.iut.persistence.entities.Client;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sydpy on 3/6/17.
 */
public class GenericDAOTest {

    @BeforeClass
    public static void setUp() {
        if (HibernateUtil.getEntityManagerFactory() == null)
            HibernateUtil.loadPersistenceUnit("campingTestUnit");
    }

    @Test
    public void testPersistence() throws Exception {

        Client client = new Client();
        client.setFirstname(TestUtils.randomString());
        client.setLastname(TestUtils.randomString());

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);

        dao.save(client);
        assertEquals(1, dao.findAll().size());

        Client saved = dao.findAll().get(0);

        assertNotNull(saved);
        assertNotNull(saved.getFirstname());
        assertNotNull(saved.getLastname());
        assertEquals(client.getFirstname(), saved.getFirstname());
        assertEquals(client.getLastname(), saved.getLastname());

        dao.removeAll();
    }

    @Test
    public void testUpdate() throws Exception {

        Client client = new Client();
        client.setFirstname(TestUtils.randomString());
        client.setLastname(TestUtils.randomString());

        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);

        int initialCount = dao.findAll().size();

        dao.save(client);

        assertEquals(initialCount + 1, dao.findAll().size());

        Client saved = dao.findAll().get(0);

        saved.setFirstname(TestUtils.randomString());
        dao.update(saved);

        assertEquals(initialCount + 1, dao.findAll().size());

        Client updated = dao.findAll().get(0);

        assertNotNull(updated);
        assertNotNull(updated.getFirstname());
        assertNotNull(updated.getId());
        assertEquals(saved.getFirstname(), updated.getFirstname());
        assertEquals(saved.getId(), updated.getId());

        dao.removeAll();
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

        assertEquals(initialCount + 1, clientDao.findAll().size());

        clientDao.removeAll();

        assertEquals(initialCount, clientDao.findAll().size());

        clientDao.removeAll();
    }
}