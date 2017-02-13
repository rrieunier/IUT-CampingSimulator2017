package fr.iut;

import fr.iut.view.ConnectionView;
import fr.iut.view.MainView;
import fr.iut.view.MapCreatorView;
import fr.iut.view.ProductManagerView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class App extends Application {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final double SCREEN_W = SCREEN_SIZE.getWidth();
    public static final double SCREEN_H = SCREEN_SIZE.getHeight();

    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Camping Simulator 2017");
        Scene scene = new ConnectionView(this);
        scene = new MainView(this, "TestUser"); //TODO : remove
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    @SuppressWarnings("unused")
    public boolean tryLogin(String username, String password) {
        //TODO : demander à la base de données si les identifiants sont bons, s'il ne le sont pas, la fonction retourne false et ConnectionView affiche un message d'erreur
        return true;
    }

    public void start(String username) {

        boolean mapIsAlreadyCreated = false;

        if(username.equals("dev")) //TODO : remove
            mapIsAlreadyCreated = true;

        //TODO : query DB

        Scene sceneToDisplay;

        if(mapIsAlreadyCreated)
            sceneToDisplay = new MainView(this, username);
        else
            sceneToDisplay = new MapCreatorView(this, username);

        stage.setScene(sceneToDisplay);
        getStage().setMaximized(true);
        getStage().setWidth(App.SCREEN_W);
        getStage().setHeight(App.SCREEN_H);
        stage.centerOnScreen();
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<String> getProductsList() { // codé en dur en attendant les entités générées (JE TE POINTE DU DOIGTS OPEL MOCCKO)
        ArrayList<String> products = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++) {
            products.add("Produit n°" + String.valueOf(i));
        }
        return products;
    }
}