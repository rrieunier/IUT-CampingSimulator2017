package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.HomeController;
import fr.iut.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class ProductManagerView extends SubScene {

    public static double PRODUCT_MANAGER_W = App.SCREEN_W * 5/6;
    public static double PRODUCT_MANAGER_H = App.SCREEN_H * 7/9;

    private HomeController controller;

    private ArrayList<Product> products_list;
    private StackPane lastClicked;
    private Product lastClickedValue;

    private VBox products_box;
    private VBox details = new VBox();
    private GridPane grid = new GridPane();

    public ProductManagerView (HomeController controller) {
        super(new AnchorPane(), PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        this.controller = controller;

        AnchorPane components = (AnchorPane)getRoot();

        VBox wrapper = new VBox();
        wrapper.setMaxSize(PRODUCT_MANAGER_W, PRODUCT_MANAGER_H);
        wrapper.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));
        wrapper.setPadding(new Insets(20));
        wrapper.setLayoutY(33);

        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des stocks");

        HBox body = new HBox();
        body.setMinWidth(PRODUCT_MANAGER_W * 19/20);

        products_box = new VBox();
        products_box.setSpacing(8);
        products_box.setPrefSize(PRODUCT_MANAGER_W / 4, PRODUCT_MANAGER_H * 8/10);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(products_box);
        scrollPane.setMinSize(products_box.getPrefWidth(), products_box.getPrefHeight());

        details.getStylesheets().add(new File("res/style.css").toURI().toString());
        details.getStyleClass().add("product-detail");
        details.setPrefWidth(3 * PRODUCT_MANAGER_W / 4);
        details.setMaxHeight(17 * PRODUCT_MANAGER_H / 19);

        HeaderView details_header = new HeaderView("Détails du produit");
        grid.setGridLinesVisible(true);
        grid.getStylesheets().add(new File("res/style.css").toURI().toString());
        grid.getStyleClass().add("stock-grid");

        HBox buttons = new HBox();
        buttons.setSpacing(PRODUCT_MANAGER_W / 8);
        for(int i = 0 ; i < 2 ; i++) {
            Button button = new Button();
            if (i == 0)
                button.setText("Réaprovisionner");
            else
                button.setText("Modifier");
            button.setPrefWidth(PRODUCT_MANAGER_W / 6);
            button.getStylesheets().add(new File("res/style.css").toURI().toString());
            button.getStyleClass().add("record-sales");
            buttons.getChildren().add(button);
        }

        details.getChildren().addAll(details_header, grid, buttons);
        VBox.setMargin(buttons, new Insets(PRODUCT_MANAGER_H / 15 ,0, 0, PRODUCT_MANAGER_W / 9));

        buildProductsList();

        lastClicked = (StackPane) products_box.getChildren().get(0);
        lastClicked.setStyle("-fx-background-color: #ff6600;");
        lastClickedValue = products_list.get(0);

        body.getChildren().addAll(scrollPane, details);
        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);

        buildDetails();
    }

    private void buildProductsList() {

        products_list = controller.getProductsList();

        for (Product p : products_list) {
            StackPane pane = new StackPane();
            VBox product_wrapper = new VBox();
            HBox informations = new HBox();

            Text name = new Text(p.getLabel());
            Text stock = new Text("Quantité: " + String.valueOf(p.getStock()));
            Text price = new Text("Prix: " + String.valueOf(p.getSellPrice()));

            pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))));

            name.setFill(Color.WHITESMOKE);
            stock.setFill(Color.WHITESMOKE);
            price.setFill(Color.WHITESMOKE);

            informations.setSpacing(10);
            informations.setAlignment(Pos.CENTER);
            informations.getChildren().addAll(stock, price);

            VBox.setMargin(name, new Insets(15, 0, 5, 0));
            VBox.setMargin(informations, new Insets(0, 0, 15, 0));
            product_wrapper.getChildren().addAll(name, informations);
            product_wrapper.setPadding(new Insets(0, 10, 10, 10));

            pane.getChildren().add(product_wrapper);

            if (products_box.getChildren().size() % 2 == 0)
                pane.setStyle("-fx-background-color: #336699;");
            else
                pane.setStyle("-fx-background-color: #0F355C;");

            pane.setOnMouseClicked(event -> {
                if (lastClicked != null) {
                    if (products_box.getChildren().indexOf(lastClicked) % 2 == 0)
                        lastClicked.setStyle("-fx-background-color: #336699;");
                    else
                        lastClicked.setStyle("-fx-background-color: #0F355C;");
                }
                pane.setStyle("-fx-background-color: #ff6600;");
                lastClicked = pane;
                lastClickedValue = products_list.get(products_box.getChildren().indexOf(pane));
                actualiseDetails();
            });

            products_box.getChildren().add(pane);
        }
    }

    private void buildDetails() {
        String[] labels = {"Nom", "En stock", "Quantité critique", "Livré le"};
        for (int i = 0 ; i < 2 ; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPrefWidth(details.getPrefWidth() / 2);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0 ; i < labels.length ; i++) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPrefHeight(details.getMaxHeight() / 8);
            grid.getRowConstraints().add(constraint);
        }
        for (int i = 0 ; i < labels.length ; i++) {
            Label label  = new Label(labels[i] + ":");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-label");
            grid.addRow(i, label);
            GridPane.setMargin(label, new Insets(0 ,0, 0, 30));
        }
        for (int i = 0 ; i < labels.length ; i++) {
            Label label  = new Label(".");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-field");
            grid.add(label, 1, i);
            GridPane.setMargin(label, new Insets(0 ,0, 0, 30));
        }

        actualiseDetails();
    }

    private void actualiseDetails() {
        for (Node node : grid.getChildren()) {
            if (node instanceof Label && GridPane.getColumnIndex(node) == 1) {
                switch (GridPane.getRowIndex(node)) {
                    case 0:
                        ((Label) node).setText(lastClickedValue.getLabel());
                        break;
                    case 1:
                        ((Label) node).setText(String.valueOf(lastClickedValue.getStock()));
                        break;
                    case 2:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                    case 3:
                        ((Label) node).setText("TODO GET WITH DAO");
                        break;
                }
            }
        }
    }
}
