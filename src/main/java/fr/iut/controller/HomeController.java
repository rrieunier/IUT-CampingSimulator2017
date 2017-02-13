package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.view.HomeView;
import javafx.scene.Scene;

import java.util.ArrayList;

/**
 * Created by shellcode on 2/13/17.
 */
public class HomeController implements ControllerInterface {

    private App app;
    private HomeView homeView;

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    public ArrayList<String> getProductsList() {
        ArrayList<String> products = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            products.add("Produit n°" + String.valueOf(i));
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
