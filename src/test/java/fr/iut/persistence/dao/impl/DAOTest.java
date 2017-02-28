package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Employee;
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
        dao.open();

        Client client = new Client();
        client.setFirstname("azerztery");
        client.setLastname("azerztery");

        assertTrue(dao.saveOrUpdate(client));
        assertNotNull(dao.findById(client.getId()));
        assertTrue(dao.remove(client));

        dao.close();
    }

    public void testRelationsPerist() throws Exception {

        GenericDAOImpl<Authorization, String> daoAuth =
                new GenericDAOImpl<>(Authorization.class);

        daoAuth.open();

        Authorization authorization = new Authorization();
        authorization.setLabel("auth");

        assertTrue(daoAuth.saveOrUpdate(authorization));
        assertNotNull(daoAuth.findById("auth"));

        daoAuth.close();

        GenericDAOImpl<Employee, Integer> daoEmp =
                new GenericDAOImpl<>(Employee.class);
        daoEmp.open();

        Employee employee = new Employee();
        employee.setLastName("ertrzezrtr");
        employee.setFirstName("zaergdsfsgtrq");
        employee.setLogin("aereterez");
        employee.setPassword("zerzegfsf");
        employee.getAuthorizations().add(authorization);

        assertTrue(daoEmp.saveOrUpdate(employee));
        assertNotNull(daoEmp.findById(employee.getId()));
        assertTrue(daoEmp.saveOrUpdate(employee));

        assertTrue(daoEmp.findById(employee.getId()).getAuthorizations().contains(authorization));

        daoEmp.close();
    }
}
