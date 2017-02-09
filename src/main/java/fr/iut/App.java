package fr.iut;

import fr.iut.view.AutorisationsView;
import fr.iut.view.ConnectionView;
import fr.iut.view.MainView;
import fr.iut.view.MapCreatorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

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

        File mapFile = new File("map.png");
        File delimiters = new File("map_components.xml");

        Scene sceneToDisplay;

        if(mapFile.exists() && delimiters.exists()) //Si la map et la délimitation des differentes zones existe, on lance le progiciel
            sceneToDisplay = new MainView(this, username);
        else
            sceneToDisplay = new MapCreatorView(this); //Sinon on lance la création de la map

        stage.setScene(sceneToDisplay);
        getStage().setMaximized(true);
        getStage().setWidth(App.SCREEN_W);
        getStage().setHeight(App.SCREEN_H);
        stage.centerOnScreen();
    }

    public Stage getStage() {
        return stage;
    }
}