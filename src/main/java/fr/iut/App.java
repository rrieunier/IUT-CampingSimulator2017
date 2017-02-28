package fr.iut;

import fr.iut.controller.ConnectionController;
import fr.iut.controller.HomeController;
import fr.iut.controller.MapController;
import fr.iut.view.ConnectionView;
import fr.iut.view.HomeView;
import fr.iut.view.MapCreatorView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class App extends Application {

    private static final GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static final double SCREEN_W = SCREEN_SIZE.getWidth() / monitors.length;
    public static final double SCREEN_H = SCREEN_SIZE.getHeight();

    private Stage stage;

    //Controllers, il doit y avoir un état (classe State) par controller
    private ConnectionController connectionController = new ConnectionController(this);
    private MapController mapController = new MapController(this);
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

        if(connectionController == null && state != State.CONNECTION) {
            System.out.println("You are not connected.");
            System.exit(1);
        }

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
                MapCreatorView scene = (MapCreatorView)mapController.getView();
                stage.setScene(scene);
                scene.showInfo();
                break;

            case HOME:
                homeController = new HomeController(this, connectionController.getConnectedUser());
                stage.setOnCloseRequest(windowEvent -> homeController.OnWindowIsClosing());
                stage.setScene(homeController.getView());
                break;
        }

        stage.centerOnScreen();
    }

    public boolean doINeedToShowMapEditor() {
        return !mapController.isMapAlreadyCreated();
    }
}