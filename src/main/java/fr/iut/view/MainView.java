package fr.iut.view;

import fr.iut.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by shellcode on 2/6/17.
 */
public class MainView extends Scene {

    private App app;
    private String username;

    public MainView(App app, String username) {
        super(new GridPane(), App.SCREEN_W, App.SCREEN_H);
        this.app = app;
        this.username = username;

        GridPane components = (GridPane)getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPercentHeight(90);
        row2.setPercentHeight(10);
        row2.setValignment(VPos.TOP);
        components.getRowConstraints().addAll(row1, row2);

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

        components.addRow(0, borderPane);

        VBox vboxUser = new VBox();

        Text welcome_text = new Text("Bienvenue " + username + " !");
        welcome_text.setFont(new Font(20));
        welcome_text.setFill(Color.WHITE);
        welcome_text.applyCss();

        Button decoButton = new Button("Deconnexion");
        decoButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        decoButton.getStyleClass().add("record-sales");
        decoButton.setMinWidth(welcome_text.getLayoutBounds().getWidth());

        VBox.setMargin(welcome_text, new Insets(0, 0, 0, 30));
        VBox.setMargin(decoButton, new Insets(0, 0, 0, 30));


        vboxUser.getChildren().addAll(welcome_text, decoButton);
        components.addRow(1, vboxUser);
    }

    private void buildManagmentTab(Tab tab) {
        //TODO : Faire des boutons plutot que des onglets ca sera plus simple...
        tab.setText("Gestion");

        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);
        tabPane.setRotateGraphic(true);
        tabPane.setTabMinHeight(App.SCREEN_H / 10);
        tabPane.setTabMaxHeight(App.SCREEN_H / 10);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        tabPane.getStyleClass().add("horizontalTabs");

        BorderPane borderPane = new BorderPane();

        String tabsValue[] = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Statistiques"};

        for(int i = 0; i < 6; i++) {
            Tab t = new Tab();
            Label l = new Label(tabsValue[i]);
            l.setRotate(90);
            l.setTextFill(Color.WHITE);
            HBox box = new HBox();
            box.getChildren().add(l);
            box.setRotate(90);
            t.setGraphic(box);
            tabPane.getTabs().add(t);

            //TODO : contenu des onglets
            switch (i) {
                case 0: t.setContent(null); break;
                case 1: t.setContent(null); break;
                case 2: t.setContent(null); break;
                case 3: t.setContent(null); break;
                case 4: t.setContent(null); break;
                case 5: t.setContent(null); break;
            }
        }

        borderPane.setCenter(tabPane);

        StackPane.setMargin(borderPane, new Insets(30, 0, 0, 30));
        container.getChildren().add(borderPane);

        tab.setContent(container);
    }

    private void buildMapTab(Tab tab) {
        tab.setText("Carte");
        GridPane container = new GridPane();
//        container.setMinSize(App.SCREEN_W, App.SCREEN_H);
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");



        tab.setContent(container);
    }
}
