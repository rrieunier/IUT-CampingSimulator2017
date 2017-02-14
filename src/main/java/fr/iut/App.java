package fr.iut;

import fr.iut.controller.ConnectionController;
import fr.iut.controller.HomeController;
import fr.iut.controller.MapController;
import fr.iut.view.ConnectionView;
import fr.iut.view.HomeView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class App extends Application {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final double SCREEN_W = SCREEN_SIZE.getWidth();
    public static final double SCREEN_H = SCREEN_SIZE.getHeight();

    private Stage stage;

    //Controllers, il doit y avoir un état (classe State) par controller
    private ConnectionController connectionController = new ConnectionController(this);
    private MapController mapController;
    private HomeController homeController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Camping Simulator 2017");

        primaryStage.setScene(connectionController.getView());
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public void switchState(State state) {

        stage.setWidth(SCREEN_W);
        stage.setHeight(SCREEN_H);

        switch (state) {
            case CONNECTION:
                connectionController.logout();
                stage.setScene(connectionController.getView());
                stage.setWidth(ConnectionView.LOGIN_WIDTH);
                stage.setHeight(ConnectionView.LOGIN_HEIGHT);
                break;

            case MAP_CREATOR:
                mapController = new MapController(this);
                stage.setScene(mapController.getView());
                break;

            case HOME:

                if(connectionController == null) {
                    System.out.println("Impossible d'afficher l'interface d'accueil sans avoir été authentifié !!!");
                    System.exit(1);
                }

                homeController = new HomeController(this, connectionController.getConnectedUser());
                stage.setScene(homeController.getView());
                break;
        }

        stage.centerOnScreen();
    }
}