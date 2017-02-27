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
        GenericDAOImpl<Product, java.io.Serializable> dao = new GenericDAOImpl<>(Product.class);

        return (ArrayList<Product>) dao.findAll();
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
