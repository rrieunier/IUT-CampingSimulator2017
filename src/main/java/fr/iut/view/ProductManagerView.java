package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.HomeController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;

public class ProductManagerView extends SubScene {

    public static double PRODUCT_MANAGER_W = App.SCREEN_W * 5/6;
    public static double PRODUCT_MANAGER_H = App.SCREEN_H * 7/9;

    HomeController controller;

    public ProductManagerView (HomeController controller) {
        super(new AnchorPane(), PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        this.controller = controller;

        final StackPane[] lastClicked = {null};

        AnchorPane components = (AnchorPane)getRoot();

        VBox wrapper = new VBox();
        wrapper.setMaxSize(PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        wrapper.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        wrapper.setPadding(new Insets(20));
        wrapper.setLayoutY(33);

        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des stocks");

        HBox body = new HBox();
        body.setMinWidth(PRODUCT_MANAGER_W * 19/20);

        VBox products_box = new VBox();
        products_box.setPrefSize(PRODUCT_MANAGER_W / 4, PRODUCT_MANAGER_H * 8/10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(products_box);
        scrollPane.setMinSize(products_box.getPrefWidth(), products_box.getPrefHeight());

        VBox details = new VBox();
        details.getStylesheets().add(new File("res/style.css").toURI().toString());
        details.getStyleClass().add("product-detail");
        details.setPrefWidth(3 * PRODUCT_MANAGER_W / 4);
        details.setMaxHeight(17 * PRODUCT_MANAGER_H / 19);

        HeaderView details_header = new HeaderView("Détails du produit");
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.getStylesheets().add(new File("res/style.css").toURI().toString());
        grid.getStyleClass().add("stock-grid");

        String[] labels = {"Nom", "Lieu", "Responsable", "Quantité", "Quantité critique", "Livré le"};
        for (int i = 0 ; i < 2 ; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPrefWidth(details.getPrefWidth() / 2);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0 ; i < labels.length ; i++) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPrefHeight(details.getMaxHeight() / 11);
            grid.getRowConstraints().add(constraint);
        }
        for (int i = 0 ; i < labels.length ; i++) {
            Label label  = new Label(labels[i] + ":");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-label");
            grid.addRow(i, label);
            grid.setMargin(label, new Insets(0 ,0, 0, 30));
        }
        for (int i = 0 ; i < labels.length ; i++) {
            TextField value  = new TextField("VALUE FROM DATABASE");
            value.setEditable(false);
            value.setFocusTraversable(false);
            value.setPrefHeight(details.getMaxHeight() / 12);
            value.setAlignment(Pos.CENTER);
            value.getStylesheets().add(new File("res/style.css").toURI().toString());
            value.getStyleClass().add("grid-stock-field");
            grid.add(value, 1, i);
            grid.setMargin(value, new Insets(0 ,30, 0, 30));
        }


        details.getChildren().addAll(details_header, grid);

        for (String s : controller.getProductsList()) { //a modifier après la génération des entités
            StackPane pane = new StackPane();
            pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))));
            VBox product_wrapper = new VBox();
            Text name = new Text(s);
            name.setFill(Color.WHITESMOKE);
            HBox informations = new HBox();
            Text stock = new Text("Restant: 0");
            stock.setFill(Color.WHITESMOKE);
            Text price = new Text("Prix: 50€");
            price.setFill(Color.WHITESMOKE);

            informations.setSpacing(10);
            informations.setAlignment(Pos.CENTER);
            informations.getChildren().addAll(stock, price);
            product_wrapper.getChildren().addAll(name, informations);
            product_wrapper.setPadding(new Insets(0, 10, 10, 10));
            product_wrapper.setMargin(name, new Insets(15, 0, 5, 0));
            product_wrapper.setMargin(informations, new Insets(0, 0, 15, 0));

            pane.getChildren().add(product_wrapper);
            if (products_box.getChildren().size() % 2 == 0)
                pane.setStyle("-fx-background-color: #336699");
            else
                pane.setStyle("-fx-background-color: #0F355C");

            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (lastClicked[0] != null) {
                        if (products_box.getChildren().indexOf(lastClicked[0]) % 2 == 0)
                            lastClicked[0].setStyle("-fx-background-color: #336699");
                        else
                            lastClicked[0].setStyle("-fx-background-color: #0F355C");
                    }
                    pane.setStyle("-fx-background-color: #903b12");
                    lastClicked[0] = pane;
                }
            });

            products_box.getChildren().add(pane);
        }

        lastClicked[0] = (StackPane) products_box.getChildren().get(0);
        lastClicked[0].setStyle("-fx-background-color: #a34313");

        body.getChildren().addAll(scrollPane, details);
        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);
    }

}
