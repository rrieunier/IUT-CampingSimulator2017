package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Employee;
import fr.iut.view.InscriptionView;
import javafx.scene.Scene;

/**
 * Created by theo on 13/03/17.
 */
public class InscriptionController {

    private App app;
    private InscriptionView inscriptionView = new InscriptionView(this);

    public InscriptionController(App app){
        this.app = app;
    }

    public Scene getView() {
        return inscriptionView;
    }

    public void finish(Employee employee) {
        GenericDAO<Employee, Integer> daoEmployee = new GenericDAO<>(Employee.class);
        daoEmployee.save(employee);
        ConnectionController connectionController = new ConnectionController(app);
        connectionController.tryLogin(employee.getLogin(), employee.getPassword());
        app.switchState(State.HOME);
    }
}
