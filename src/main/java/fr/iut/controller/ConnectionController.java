package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Employee;
import fr.iut.view.ConnectionView;
import javafx.scene.Scene;

import java.util.List;


public class ConnectionController implements ControllerInterface {

    private App app;
    private ConnectionView connectionView = new ConnectionView(this);
    private String connectedUser = null;

    private GenericDAO<Employee, Integer> daoEmployee = new GenericDAO<>(Employee.class);

    public ConnectionController(App app) {
        this.app = app;
    }

    @Override
    public Scene getView() {
        return connectionView;
    }

    public boolean tryLogin(String username, String password) {
        if(username.length() == 0 || password.length() == 0)
            return false;

        List<Employee> employees = daoEmployee.findAll();

        Employee employee = null;

        for(Employee e : employees) {
            if (e.getLogin() != null && e.getLogin().equals(username)) {
                employee = e;
                break;
            }
        }

        if(employee == null || !employee.getPassword().equals(password))
            return false;

        connectedUser = employee.getLogin();

        return true;
    }

    @Override
    public void finish() {
        //TODO REMOVE COMMENTS
        /*
        if(app.doINeedToShowMapEditor())
            app.switchState(State.MAP_CREATOR);
        else
        */
            app.switchState(State.HOME);
    }

    public String getConnectedUser() {
        return connectedUser;
    }

    public void logout() {
        connectedUser = null;
        connectionView = new ConnectionView(this);
    }
}
