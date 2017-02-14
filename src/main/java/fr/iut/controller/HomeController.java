package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.model.Product;
import fr.iut.view.HomeView;
import javafx.scene.Scene;

import java.util.ArrayList;


public class HomeController implements ControllerInterface {

    private App app;
    private HomeView homeView;

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    public ArrayList<Product> getProductsList() {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            // findAll quand DAO ok
            Product product = new Product();
            product.setId(i);
            product.setLabel("Produit n° " + i);
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
}
