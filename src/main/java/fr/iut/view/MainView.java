package fr.iut.view;

import fr.iut.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
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
        row2.setValignment(VPos.BOTTOM);
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

        decoButton.setOnAction(actionEvent -> app.logout());

        VBox.setMargin(welcome_text, new Insets(0, 0, 0, 30));
        VBox.setMargin(decoButton, new Insets(0, 0, 0, 30));

        vboxUser.getChildren().addAll(welcome_text, decoButton);
        components.addRow(1, vboxUser);
    }

    private void buildManagmentTab(Tab tab) {
        tab.setText("Gestion");

        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");

        VBox verticalTabs = new VBox();
        verticalTabs.getStylesheets().add(new File("res/style.css").toURI().toString());
        verticalTabs.getStyleClass().add("horizontalTabs");
        verticalTabs.setSpacing(30);
        BorderPane.setMargin(verticalTabs, new Insets(50, 0, 0, 80));

        BorderPane borderPane = new BorderPane();

        String tabsValue[] = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Statistiques"};

        ToggleGroup buttonsGroup = new ToggleGroup();

        for(int i = 0; i < 6; i++) {
            ToggleButton newTab = new ToggleButton();

            if(i == 0)
                newTab.setSelected(true);

            newTab.setToggleGroup(buttonsGroup);
            newTab.setText(tabsValue[i]);
            newTab.getStylesheets().add(new File("res/style.css").toURI().toString());
            newTab.getStyleClass().add("buttonTab");
            newTab.setMinWidth(App.SCREEN_W / 10);
            newTab.setMinHeight(App.SCREEN_H / 10);
            verticalTabs.getChildren().add(newTab);

            int finalI = i;
            newTab.setOnAction(actionEvent -> {

                SubScene subScene = null;

                switch (finalI) {
                    case 0: break;
                    case 1: break;
                    case 2: break;
                    case 3: break;
                    case 4: subScene = new ProductManagerView(app); break;
                    case 5: break;
                }

                borderPane.setCenter(subScene);
            });
        }

        borderPane.setLeft(verticalTabs);

        container.getChildren().add(borderPane);

        tab.setContent(container);
    }

    private void buildMapTab(Tab tab) {
        tab.setText("Carte");

        GridPane container = new GridPane();
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");


        //TODO : ajouter la carte au GridPane

        tab.setContent(container);
    }
}
