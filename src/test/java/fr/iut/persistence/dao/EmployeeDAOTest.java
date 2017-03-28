package fr.iut.persistence.dao;

import fr.iut.persistence.TestUtils;
import fr.iut.persistence.entities.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by Sydpy on 3/8/17.
 */
public class EmployeeDAOTest {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    @BeforeClass
    public static void setUp() {
        if (HibernateUtil.getEntityManagerFactory() == null)
            HibernateUtil.loadPersistenceUnit("campingTestUnit");
    }

    @Test
    public void testFindByLogin() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName(TestUtils.randomString());
        employee.setLastName(TestUtils.randomString());
        employee.setLogin(TestUtils.randomString());
        employee.setPassword(TestUtils.randomString());

        employeeDAO.save(employee);

        Employee found = employeeDAO.findByLogin(employee.getLogin());

        assertNotNull(found);
        assertTrue(Objects.equals(found.getFirstName(), employee.getFirstName()));
        assertTrue(Objects.equals(found.getLastName(), employee.getLastName()));
        assertTrue(Objects.equals(found.getLogin(), employee.getLogin()));
        assertTrue(Objects.equals(found.getPassword(), employee.getPassword()));

        employeeDAO.removeAll();
    }

    @Test
    public void testConnectionDisconnection() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName(TestUtils.randomString());
        employee.setLastName(TestUtils.randomString());
        employee.setLogin(TestUtils.randomString());
        employee.setPassword(TestUtils.randomString());

        employeeDAO.save(employee);

        Employee connected = employeeDAO.connectUser(employee.getLogin(), employee.getPassword());

        assertNotNull(connected);
        assertTrue(Objects.equals(connected.getFirstName(), employee.getFirstName()));
        assertTrue(Objects.equals(connected.getLastName(), employee.getLastName()));
        assertTrue(Objects.equals(connected.getLogin(), employee.getLogin()));
        assertTrue(Objects.equals(connected.getPassword(), employee.getPassword()));

        employeeDAO.removeAll();

    }

    @Test
    public void testFindByFirstName() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName(TestUtils.randomString());
        employee.setLastName(TestUtils.randomString());
        employee.setLogin(TestUtils.randomString());
        employee.setPassword(TestUtils.randomString());

        employeeDAO.save(employee);

        List<Employee> matches = employeeDAO.findByFirstName(employee.getFirstName());
        assertEquals(1, matches.size());

        Employee found = matches.get(0);

        assertNotNull(found);
        assertTrue(Objects.equals(found.getFirstName(), employee.getFirstName()));
        assertTrue(Objects.equals(found.getLastName(), employee.getLastName()));
        assertTrue(Objects.equals(found.getLogin(), employee.getLogin()));
        assertTrue(Objects.equals(found.getPassword(), employee.getPassword()));

        employeeDAO.removeAll();

    }

    @Test
    public void testFindByLastName() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName(TestUtils.randomString());
        employee.setLastName(TestUtils.randomString());
        employee.setLogin(TestUtils.randomString());
        employee.setPassword(TestUtils.randomString());

        employeeDAO.save(employee);

        List<Employee> matches = employeeDAO.findByLastName(employee.getLastName());
        assertEquals(1, matches.size());

        Employee found = matches.get(0);

        assertNotNull(found);
        assertTrue(Objects.equals(found.getFirstName(), employee.getFirstName()));
        assertTrue(Objects.equals(found.getLastName(), employee.getLastName()));
        assertTrue(Objects.equals(found.getLogin(), employee.getLogin()));
        assertTrue(Objects.equals(found.getPassword(), employee.getPassword()));

        employeeDAO.removeAll();

    }
}