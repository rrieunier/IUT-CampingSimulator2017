package fr.iut.view;

import fr.iut.App;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Created by shellcode on 2/6/17.
 */
public class MainView extends Scene {

    public static final double WIDTH = App.SCREEN_W / 2;
    public static final double HEIGHT = App.SCREEN_H / 2;

    private App app;

    public MainView(App app, String username) {
        super(new Group(), WIDTH, HEIGHT);
        this.app = app;

        Group components = (Group)getRoot();

    }
}
