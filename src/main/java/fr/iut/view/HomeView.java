package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.HomeController;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/6/17.
 */
public class HomeView extends Scene {

    private HomeController controller;
    private String username;
    private BorderPane components;

    public static final int LEFT_PADDING_TAB = 50;

    private ToggleButton selectedTab = null;

    public HomeView(HomeController controller, String username) {
        super(new BorderPane(), App.SCREEN_W, App.SCREEN_H);
        this.controller = controller;
        this.username = username;

        components = (BorderPane) getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(App.SCREEN_W/2 - 57);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        Tab tabManagment = new Tab(), tabMap = new Tab();
        buildManagmentTab(tabManagment);
        buildMapTab(tabMap);

        tabPane.getTabs().addAll(tabManagment, tabMap);
        components.setCenter(tabPane);
        //borderPane.setMinWidth(App.SCREEN_W);

        buildFooter();
    }

    private void buildManagmentTab(Tab tab) {
        tab.setText("Gestion");

        StackPane container = new StackPane();
        container.setPadding(new Insets(50, 0, 0, LEFT_PADDING_TAB));
        container.setStyle("-fx-background-color: rgb(12, 27, 51);");

        VBox verticalTabs = new VBox();
        verticalTabs.getStylesheets().add(new File("res/style.css").toURI().toString());
        verticalTabs.getStyleClass().add("horizontalTabs");
        verticalTabs.setSpacing(30);
        //BorderPane.setMargin(verticalTabs, new Insets(50, 0, 0, LEFT_PADDING_TAB));

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

            //Permet d'eviter la déselection d'un bouton déjà séléctionné
            newTab.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                if(selectedTab == newTab)
                    event.consume();
            });

            int finalI = i;
            newTab.setOnAction(actionEvent -> {

                selectedTab = newTab;

                SubScene subScene = null;

                switch (finalI) {
                    case 0: break;
                    case 1: break;
                    case 2: break;
                    case 3: break;
                    case 4: subScene = new ProductManagerView(controller); break;
                    case 5: subScene = new StatisticsView(controller.getStatiscticsController()); break;
                }

                borderPane.setCenter(subScene);

                if(subScene != null) {
                    BorderPane.setAlignment(subScene, Pos.TOP_CENTER);
                }
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

    private void buildFooter() {
        BorderPane footerWrapper = new BorderPane();
        VBox.setVgrow(footerWrapper, Priority.ALWAYS);

        VBox vboxUser = new VBox();
        vboxUser.setAlignment(Pos.CENTER);

        Text welcome_text = new Text("Bienvenue " + username + " !");
        welcome_text.setFont(new Font(20));
        welcome_text.setFill(Color.WHITE);
        welcome_text.applyCss(); //important pour pouvoir récupérer les dimenssions

        Button decoButton = new Button("Deconnexion");
        decoButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        decoButton.getStyleClass().add("record-sales");
        decoButton.setMinWidth(welcome_text.getLayoutBounds().getWidth());

        decoButton.setOnAction(actionEvent -> controller.finish());


        vboxUser.getChildren().addAll(welcome_text, decoButton);
        footerWrapper.setLeft(vboxUser);


        Image img = new Image(new File("res/notification.png").toURI().toString());
        ImageView alertIcon = new ImageView(img);

        double fit_value = App.SCREEN_W / 35;
        alertIcon.setFitHeight(fit_value);
        alertIcon.setFitWidth(fit_value);

        HBox.setMargin(alertIcon, new Insets(0, 20, 0, 0));

        int notifs_count = controller.getNotificationsController().getNotifications().size();
        Text notification_text = new Text("Vous avez " + notifs_count + " notifications en attente.");
        notification_text.setFont(new Font(20));

        if(notifs_count > 0)
            createBlinker(notification_text).play();

        notification_text.setFill(Color.WHITE);
        notification_text.applyCss();

        HBox notification_wrapper = new HBox();

        notification_wrapper.setOnMouseClicked(mouseEvent -> {
            Optional<Integer> result = controller.getNotificationsController().getView().showAndWait();

            result.ifPresent(value -> notification_text.setText("Vous avez " + controller.getNotificationsController().getNotifications().size() + " notifications en attente.")); //TODO
        });

        notification_wrapper.getChildren().addAll(alertIcon, notification_text);


        footerWrapper.setRight(notification_wrapper);
        notification_wrapper.setAlignment(Pos.CENTER);

        BorderPane.setMargin(footerWrapper, new Insets(0, 50, 25, LEFT_PADDING_TAB));
        components.setBottom(footerWrapper);
    }

    private Timeline createBlinker(Node node) {
        Timeline blink = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0.35),
                        new KeyValue(
                                node.opacityProperty(),
                                0,
                                Interpolator.DISCRETE
                        )
                ),
                new KeyFrame(
                        Duration.seconds(0.7),
                        new KeyValue(
                                node.opacityProperty(),
                                1,
                                Interpolator.DISCRETE
                        )
                )
        );
        blink.setCycleCount(Animation.INDEFINITE);

        return blink;
    }
}
