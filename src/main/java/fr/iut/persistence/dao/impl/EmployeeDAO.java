package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Employee;
import org.hibernate.query.Query;

/**
 * Created by Sydpy on 2/28/17.
 */
public class EmployeeDAO extends GenericDAOImpl<Employee, Integer> {

    public EmployeeDAO() {
        super(Employee.class);
    }

    public Employee findByLogin(String login){

        session.beginTransaction();

        Employee entity = null;

        try {
            String hql = "from Employee where login = :login";
            Query query = session.createQuery(hql);
            query.setParameter("login", login);
            entity = (Employee) query.getSingleResult();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        return entity;
    }
}
