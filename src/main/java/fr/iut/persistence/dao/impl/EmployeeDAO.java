package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Employee;
import org.hibernate.query.Query;

import javax.transaction.Transactional;

/**
 * Created by Sydpy on 2/28/17.
 */
public class EmployeeDAO extends GenericDAOImpl<Employee, Integer> {

    public EmployeeDAO() {
        super(Employee.class);
    }

    @Transactional(rollbackOn = Exception.class)
    public Employee findByLogin(String login) {

        String hql = "from Employee where login = :login";
        Query query = session.createQuery(hql);
        query.setParameter("login", login);

        return (Employee) query.getSingleResult();
    }
}
