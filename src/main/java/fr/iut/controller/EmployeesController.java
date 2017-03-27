package fr.iut.controller;

import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Problem;
import fr.iut.view.EmployeeManagerView;
import javafx.scene.SubScene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by shellcode on 2/17/17.
 */
public class EmployeesController {

    private EmployeeDAO daoEmployee = new EmployeeDAO();
    private HomeController homeController;
    private ArrayList<Employee> employees = new ArrayList<>();


    public EmployeesController(HomeController homeController) {
        this.homeController = homeController;
    }

    public SubScene getView() {
        return new EmployeeManagerView(this);
    }

    public void createEmployees() {
        employees = (ArrayList<Employee>) daoEmployee.findAll();
    }

    public void sortEmployees(int sort_options){
        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                double result = 0;
                switch (sort_options){
                    case 1:
                        result = o2.getLastName().toLowerCase().compareTo(o1.getLastName().toLowerCase());
                        break;
                    case 2:
                        result = o1.getFirstName().compareTo(o2.getFirstName());
                        break;
                    case 3:
                        result = o2.getFirstName().compareTo(o1.getFirstName());
                        break;
                    default:
                        result = o1.getLastName().toLowerCase().compareTo(o2.getLastName().toLowerCase());
                        break;
                }
                return (int) result;
            }
        });
    }

    public void updateAuthorizations(Employee employee, ArrayList<Boolean> permissions){
        HashSet<Authorization> authorizations = new HashSet<>();
        for (int i = 0; i < permissions.size(); i++) {
            if (permissions.get(i))
                authorizations.add(Authorization.values()[i]);
        }
        employee.setAuthorizations(authorizations);
        daoEmployee.update(employee);
    }

    public void saveEmployee(Employee employee) {
        daoEmployee.save(employee);
    }

    public void updateEmployee(Employee employee) {
        daoEmployee.update(employee);
    }

    public void eraseEmployee(Employee employee) {
        daoEmployee.remove(employee);
    }

    public ArrayList<Employee> getEmployees(){ return employees; }
}
