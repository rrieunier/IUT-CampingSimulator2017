package fr.iut.persistence.entities;

import fr.iut.persistence.TestUtils;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.dao.GenericDAO;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sydpy on 3/22/17.
 */
public class ProblemListenerTest {
    @Test
    public void postPersistUpdate() throws Exception {

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

        GenericDAO<Notification, Integer> notificationDAO = new GenericDAO<>(Notification.class);
        assertEquals(1, notificationDAO.count());
    }

}