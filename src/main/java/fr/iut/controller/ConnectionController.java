package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Employee;
import fr.iut.view.ConnectionView;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;


public class ConnectionController implements ControllerInterface {

    private App app;
    private ConnectionView connectionView = new ConnectionView(this);
    private String connectedUser = null;

    private GenericDAOImpl<Employee, Integer> daoEmployee = new GenericDAOImpl<>(Employee.class);

    public ConnectionController(App app) {
        this.app = app;
    }

    @Override
    public Scene getView() {
        return connectionView;
    }

    public boolean tryLogin(String username, String password) {

        return true;/*

        if(username.length() == 0 || password.length() == 0)
            return false;

        daoEmployee.open();
        List<Employee> employees = daoEmployee.findAll();
        daoEmployee.close();

        Employee employee = null;

        for(Employee e : employees) {
            if (e.getLogin().equals(username)) {
                employee = e;
                break;
            }
        }

        if(employee == null || !employee.getPassword().equals(password))
            return false;

        connectedUser = employee.getLogin();

        return true;*/
    }

    @Override
    public void finish() {
        if(app.doINeedToShowMapEditor())
            app.switchState(State.MAP_CREATOR);
        else
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
