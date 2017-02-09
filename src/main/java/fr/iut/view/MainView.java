package fr.iut.view;

import fr.iut.App;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Created by shellcode on 2/6/17.
 */
public class MainView extends Scene {

    public static final double WIDTH = App.SCREEN_W / 2;
    public static final double HEIGHT = App.SCREEN_H / 2;

    private App app;

    public MainView(App app, String username) {
        super(new GridPane(), WIDTH, HEIGHT);
        this.app = app;

        GridPane components = (GridPane)getRoot();
        components.add(new Text("Interface d'accueil !"), 1, 1);
    }
}
