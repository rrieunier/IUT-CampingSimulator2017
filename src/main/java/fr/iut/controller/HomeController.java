package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Product;
import fr.iut.view.HomeView;
import javafx.scene.Scene;

import java.util.ArrayList;


public class HomeController implements ControllerInterface {

    private App app;
    private HomeView homeView;
    private NotificationsController notificationsController = new NotificationsController(this);
    private ClientsController clientsController = new ClientsController(this);
    private StatisticsController statisticsController = new StatisticsController(this);

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    public ArrayList<Product> getProductsList() {
        GenericDAOImpl<Product, Integer> dao = new GenericDAOImpl<Product, Integer>(Product.class);

        dao.open();
        ArrayList<Product> products = (ArrayList<Product>) dao.findAll();
        dao.close();

        return (ArrayList<Product>) products;
    }

    @Override
    public Scene getView() {
        return homeView;
    }

    @Override
    public void finish() {
        app.switchState(State.CONNECTION);
    }

    public NotificationsController getNotificationsController() {
        return notificationsController;
    }

    public StatisticsController getStatiscticsController() {
        return statisticsController;
    }

    public ClientsController getClientsController() {
        return clientsController;
    }

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
