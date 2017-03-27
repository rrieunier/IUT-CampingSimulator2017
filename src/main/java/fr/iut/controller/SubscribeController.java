package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Employee;
import fr.iut.view.SubscribeView;
import javafx.scene.Scene;

/**
 * Created by theo on 13/03/17.
 */
public class SubscribeController {

    private App app;
    private EmployeeDAO dao = new EmployeeDAO();

    public SubscribeController(App app){
        this.app = app;
    }

    public Scene getView() {
        return new SubscribeView(this);
    }

    /**
     * @return a boolean which is true if no employee is present in the database
     */
    public boolean isFirstSuscribe() {
        return dao.findAll().size() == 0;
    }

    public void finish(Employee employee) {
        dao.save(employee);

        ConnectionController connectionController = new ConnectionController(app);
        connectionController.tryLogin(employee.getLogin(), employee.getPassword());
        app.switchState(State.CONNECTION);
    }
}
