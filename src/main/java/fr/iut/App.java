package fr.iut;

import fr.iut.view.ConnectionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

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

    public void GO_MAMENE_GOOOOOOOOOO() {

        //TODO : Ici on fait du sale genre on affiche la bonne grosse fenetre de gestion du camping à l'ancienne tmtc
    }
}
