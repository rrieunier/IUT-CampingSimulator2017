package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.entities.Product;
import fr.iut.view.HomeView;
import javafx.scene.Scene;

import java.util.ArrayList;


public class HomeController implements ControllerInterface {

    private App app;
    private HomeView homeView;
    private NotificationsController notificationsController = new NotificationsController(this);

    private StatisticsController statisticsController;

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
        statisticsController = new StatisticsController(app, this);
    }

    public ArrayList<Product> getProductsList() {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            // findAll quand DAO ok
            Product product = new Product();
            product.setName("Produit n° " + i);
            product.setSellPrice(i * 2);
            product.setStock(i);
            products.add(product);
        }
        return products;
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

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
