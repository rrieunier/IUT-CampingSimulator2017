package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.HomeController;
import fr.iut.controller.NotificationsController;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Employee;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

/**
 * Created by shellcode on 2/6/17.
 */
public class HomeView extends Scene {

    private HomeController controller;
    private Employee connectedEmployee;
    private BorderPane components;

    public static double TAB_CONTENT_W = App.SCREEN_W * 5 / 6;
    public static double TAB_CONTENT_H = App.SCREEN_H * 7 / 9.45;

    public static final int LEFT_PADDING_TAB = 50;

    private ToggleButton selectedTab = null;
    private Timeline blinkAnimation;
    private Text notification_text;

    public HomeView(HomeController controller, Employee connectedEmployee) {
        super(new BorderPane(), App.SCREEN_W, App.SCREEN_H);
        this.controller = controller;
        this.connectedEmployee = connectedEmployee;

        components = (BorderPane) getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        TabPane tabPane = new TabPane();
        tabPane.setTabMinWidth(App.SCREEN_W/2 - 57);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        Tab tabManagment = new Tab(), tabMap = new Tab();
        buildManagmentTab(tabManagment);
        buildMapAndReservationsTab(tabMap);

        tabPane.getTabs().addAll(tabManagment, tabMap);
        components.setCenter(tabPane);

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

        BorderPane borderPane = new BorderPane();

        String tabsValue[] = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Statistiques"};

        ToggleGroup buttonsGroup = new ToggleGroup();
        ToggleButton defaultSelected = null;

        for(int i = 0; i < 6; i++) {
            ToggleButton newTab = new ToggleButton();

            newTab.setToggleGroup(buttonsGroup);
            newTab.setText(tabsValue[i]);
            newTab.getStylesheets().add(new File("res/style.css").toURI().toString());
            newTab.getStyleClass().add("buttonTab");
            newTab.setMinWidth(App.SCREEN_W / 10);
            newTab.setMinHeight(App.SCREEN_H / 10);

            if(i == 0) {
                if(!connectedEmployee.hasPermission(Authorization.CLIENT_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

            else if(i == 1) {
                if(!connectedEmployee.hasPermission(Authorization.PROBLEM_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

            else if(i == 2) {
                if(!connectedEmployee.hasPermission(Authorization.EMPLOYEE_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

            else if(i == 3) {
                if(!connectedEmployee.hasPermission(Authorization.SUPPLIER_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

            else if(i == 4) {
                if(!connectedEmployee.hasPermission(Authorization.PRODUCT_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

            else if(i == 5) {
                if(!connectedEmployee.hasPermission(Authorization.STATISTICS_READ))
                    newTab.setDisable(true);
                else if(defaultSelected == null)
                    defaultSelected = newTab;
            }

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
                    case 0: subScene = controller.getClientsController().getView(); break;
                    case 1: subScene = controller.getIncidentsController().getView(); break;
                    case 2: subScene = controller.getEmployeesController().getView(); break;
                    case 3: subScene = controller.getSupplierController().getView(); break;
                    case 4: subScene = controller.getProductController().getView(); break;
                    case 5: subScene = controller.getStatiscticsController().getView(); break;
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

        if(defaultSelected != null)
            defaultSelected.fire(); //click button
    }


    private void buildMapAndReservationsTab(Tab tab) {
        tab.setText("Carte & Réservations");

        BorderPane container = new BorderPane();
        MapCreatorView mapCreatorView = (MapCreatorView)controller.getMapController().getView();

        VBox reservationsWrapper = new VBox();
        reservationsWrapper.setAlignment(Pos.CENTER);

        Text reservationsText = new Text("Réservations :");
        reservationsText.setFont(new Font(20));
        reservationsText.setFill(Color.WHITE);
        reservationsWrapper.getChildren().add(reservationsText);

        ReservationsView reservationsView = (ReservationsView) controller.getReservationsController().getView();
        reservationsView.setMinWidth(TAB_CONTENT_W / 4);
        BorderPane.setMargin(reservationsWrapper, new Insets(20));
        reservationsWrapper.getChildren().add(reservationsView);

        Button newResaButton = new Button("Nouvelle réservation");
        newResaButton.getStyleClass().add("record-sales");
        newResaButton.setOnAction(actionEvent -> {
            ReservationManagerDialog reservationManagerDialog = new ReservationManagerDialog(controller.getReservationsController());
            reservationManagerDialog.showAndWait();
            reservationsView.refresh();
        });
        reservationsWrapper.getChildren().add(newResaButton);

        reservationsWrapper.setSpacing(10);

        container.setLeft(reservationsWrapper);
        container.setCenter(mapCreatorView);
        tab.setContent(container);
    }

    private void buildFooter() {
        BorderPane footerWrapper = new BorderPane();
        VBox.setVgrow(footerWrapper, Priority.ALWAYS);

        VBox vboxUser = new VBox();
        vboxUser.setAlignment(Pos.CENTER);

        Text welcome_text = new Text("Bienvenue " + connectedEmployee.getLogin() + " !");
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

        NotificationsController notificationsController = controller.getNotificationsController();
        notificationsController.setOnUnsolvedNotificationsCountChangedListener(count -> {

            notification_text.setText("Vous avez " + count + " notifications en attente.");

            if(count > 0 && blinkAnimation == null) {
                blinkAnimation = createBlinker(notification_text);
                blinkAnimation.play();
            }

            else if(count == 0 && blinkAnimation != null) {
                stopBlinkAnimation();
                notification_text.setOpacity(1); //Because de blink animation can let the notification_text vanished
            }
        });

        notification_text = new Text("Vous avez " + notificationsController.getNotificationsCount() + " notifications en attente.");

        if(notificationsController.getNotificationsCount() > 0 && blinkAnimation == null) {
            blinkAnimation = createBlinker(notification_text);
            blinkAnimation.play();
        }

        notification_text.setFont(new Font(20));
        notification_text.setFill(Color.WHITE);
        notification_text.applyCss();

        HBox notification_wrapper = new HBox();

        notification_wrapper.setOnMouseClicked(mouseEvent -> {
            if(notificationsController.getNotificationsCount() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Aucune notification à consulter.");
                Platform.runLater(alert::showAndWait);
                return;
            }

            controller.getNotificationsController().getView().showAndWait();
            notification_text.setText("Vous avez " + notificationsController.getNotificationsCount() + " notifications en attente.");
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

    public void stopBlinkAnimation() {
        if(blinkAnimation != null) {
            blinkAnimation.stop();
            blinkAnimation = null;
        }
    }
}
