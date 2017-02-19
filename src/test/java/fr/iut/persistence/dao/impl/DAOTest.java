package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Client;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Created by Sydpy on 2/13/17.
 */
public class DAOTest extends TestCase{

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DAOTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static junit.framework.Test suite()
    {
        return new TestSuite( DAOTest.class );
    }

    public void testPersist() throws Exception {

        GenericDAOImpl<Client, Integer> dao = new GenericDAOImpl<>(Client.class);

        Client client = new Client();
        client.setFirstname("azerztery");
        client.setLastname("azerztery");

        dao.persist(client);
        assertEquals(1, dao.findAll().size());
        dao.delete(client);
        assertEquals(0, dao.findAll().size());
    }
}
