package fr.iut.persistence.dao;

import fr.iut.persistence.dao.exception.InvalidLoginPasswordException;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Log;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sydpy on 2/28/17.
 */
public class EmployeeDAO extends GenericDAO<Employee, Integer> {

    private static Employee connectedUser = null;

    public EmployeeDAO() {
        super(Employee.class);
    }

    @Transactional(rollbackOn = Exception.class)
    public Employee findByLogin(String login) {

        String hql = "from Employee where login = :login";
        Query query = session.createQuery(hql);
        query.setParameter("login", login);

        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Employee> findByFirstName(String firstName) {
        String hql = "from Employee where firstName = :firstName";
        Query query = session.createQuery(hql);
        query.setParameter("firstName", firstName);

        return query.getResultList();
    }

    public List<Employee> findByLastName(String lastName) {
        String hql = "from Employee where lastName = :lastName";
        Query query = session.createQuery(hql);
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }

    @Transactional(rollbackOn = Exception.class)
    public Employee connectUser(String login, String password) throws InvalidLoginPasswordException {

        Employee byLogin = findByLogin(login);

        if (byLogin != null && byLogin.getPassword().equals(password)) {
            connectedUser = byLogin;
            newConnectionLog("CONNECTED");
            return connectedUser;
        } else {
            throw new InvalidLoginPasswordException();
        }
    }

    public void disconnectUser() {

        newConnectionLog("DISCONNECTED");
        connectedUser = null;
    }

    public void newConnectionLog(String action) {

        Log log = new Log();
        log.setAction(action);
        log.setEmployee(EmployeeDAO.getConnectedUser());

        session.saveOrUpdate(log);
    }

    public static Employee getConnectedUser() {
        return connectedUser;
    }
}
