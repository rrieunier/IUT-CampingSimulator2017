package fr.iut;

import fr.iut.view.ConnectionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        primaryStage.setTitle("Camping Simulator 2017");
        Scene scene = new Scene(new ConnectionView(this), SCREEN_WIDTH, SCREEN_HEIGHT, false);
        primaryStage.setScene(scene);

        primaryStage.setHeight(SCREEN_HEIGHT);
        primaryStage.setWidth(SCREEN_WIDTH);
        primaryStage.show();
    }

    @SuppressWarnings("unused")
    public boolean tryLogin(String username, String password) {
        //TODO : demander à la base de données si les identifiants sont bons, s'il ne le sont pas, la fonction retourne false et ConnectionView affiche un message d'erreur
        return false;
    }

    public void GO_MAMENE_GOOOOOOOOOO() {

        //TODO : Ici on fait du sale genre on affiche la bonne grosse fenetre de gestion du camping à l'ancienne tmtc
    }
}
