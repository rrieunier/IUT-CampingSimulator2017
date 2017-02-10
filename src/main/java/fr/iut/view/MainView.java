package fr.iut.view;

import fr.iut.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by shellcode on 2/6/17.
 */
public class MainView extends Scene {

    private App app;

    public MainView(App app, String username) {
        super(new GridPane(), App.SCREEN_W, App.SCREEN_H);
        this.app = app;

        GridPane components = (GridPane)getRoot();
        components.add(new Text("Bienvenue " + username + " !"), 1, 1);

        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(App.SCREEN_W/2 - 50);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        BorderPane borderPane = new BorderPane();

        Tab tabManagment = new Tab(), tabMap = new Tab();
        buildManagmentTab(tabManagment);
        buildMapTab(tabMap);

        tabPane.getTabs().addAll(tabManagment, tabMap);
        borderPane.setCenter(tabPane);
        borderPane.setMinWidth(App.SCREEN_W);
        components.add(borderPane, 1, 2);
    }

    private void buildManagmentTab(Tab tab) {
        tab.setText("Gestion");

        GridPane container = new GridPane();
        container.setMinSize(App.SCREEN_W, App.SCREEN_H);
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);
        tabPane.setRotateGraphic(true);
        tabPane.setTabMinHeight(100);
        tabPane.setTabMaxHeight(100);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        tabPane.getStyleClass().add("horizontalTabs");

        BorderPane borderPane = new BorderPane();

        for(int i = 0; i < 6; i++) {
            Tab t = new Tab();

            Label l = new Label("Onglet " + (i+1));
            l.setRotate(90);
            StackPane stp = new StackPane(new Group(l));
            stp.setRotate(90);
            t.setGraphic(stp);
            tabPane.getTabs().add(t);
        }

        borderPane.setCenter(tabPane);
        borderPane.setMinWidth(App.SCREEN_W);

        container.add(borderPane, 1, 1);

        tab.setContent(container);
    }

    private void buildMapTab(Tab tab) {
        tab.setText("Carte");
        GridPane container = new GridPane();
        container.setMinSize(App.SCREEN_W, App.SCREEN_H);
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");



        tab.setContent(container);
    }
}
