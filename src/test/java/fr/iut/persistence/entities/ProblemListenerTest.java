package fr.iut.persistence.entities;

import fr.iut.persistence.TestUtils;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.HibernateUtil;
import fr.iut.persistence.dao.NotificationDAO;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sydpy on 3/22/17.
 */
public class ProblemListenerTest {

    @BeforeClass
    public static void setUp() {
        if (HibernateUtil.getEntityManagerFactory() == null)
            HibernateUtil.loadPersistenceUnit("campingTestUnit");
    }

    @Test
    public void postPersist() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName(TestUtils.randomString());
        employee.setLastName(TestUtils.randomString());
        employee.setLogin(TestUtils.randomString());
        employee.setPassword(TestUtils.randomString());
        employee.setAuthorizations(new HashSet<>(Arrays.asList(Authorization.PROBLEM_READ)));

        EmployeeDAO employeeDAO = new EmployeeDAO();
        employeeDAO.save(employee);

        Problem problem = new Problem();
        problem.setDescription(TestUtils.randomString());
        problem.setAppearanceDatetime(new Timestamp(1000));

        GenericDAO<Problem, Integer> problemDAO = new GenericDAO<>(Problem.class);
        problemDAO.save(problem);
        assertEquals(1, problemDAO.findAll().size());

        NotificationDAO notificationDAO = new NotificationDAO();
        assertEquals(1, notificationDAO.findAll().size());

        employeeDAO.removeAll();
        notificationDAO.removeAll();
        problemDAO.removeAll();
    }

}