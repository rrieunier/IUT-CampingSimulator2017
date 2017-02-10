package fr.iut.view;


import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProductManagerView extends Scene{

    App app;

    public ProductManagerView (App app) {
        super(new AnchorPane());
        this.app = app;

        final StackPane[] lastClicked = {null};

        VBox wrapper = new VBox();
        wrapper.setMinSize(App.SCREEN_W, App.SCREEN_H);
        wrapper.setMaxSize(wrapper.getMinWidth(), wrapper.getMinHeight());

        AnchorPane components = (AnchorPane)getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        StackPane header = new StackPane();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #336699;");
        Text title = new Text("Gestion des produits");
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title);

        HBox body = new HBox();
        body.setMinWidth(App.SCREEN_W);

        VBox products_box = new VBox();
        products_box.setPrefSize(App.SCREEN_W / 4, App.SCREEN_H / 1.1);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(products_box);
        scrollPane.setMinSize(products_box.getPrefWidth(), products_box.getPrefHeight());

        for (String s : app.getProductsList()) { //a modifier après la génération des entités
            StackPane pane = new StackPane();
            VBox product_wrapper = new VBox();
            Text name = new Text(s);
            HBox informations = new HBox();
            Text stock = new Text("Restant: 0");
            Text price = new Text("Prix: 50€");

            informations.setSpacing(10);
            informations.setAlignment(Pos.CENTER);
            informations.getChildren().addAll(stock, price);
            product_wrapper.getChildren().addAll(name, informations);
            product_wrapper.setPadding(new Insets(0, 10, 10, 10));
            product_wrapper.setMargin(name, new Insets(15, 0, 5, 0));
            product_wrapper.setMargin(informations, new Insets(0, 0, 15, 0));

            pane.getChildren().add(product_wrapper);
            if (products_box.getChildren().size() % 2 == 0)
                pane.setStyle("-fx-background-color: darkgrey;");
            else
                pane.setStyle("-fx-background-color: gray");

            pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (lastClicked[0] != null) {
                        if (products_box.getChildren().indexOf(lastClicked[0]) % 2 == 0)
                            lastClicked[0].setStyle("-fx-background-color: darkgrey");
                        else
                            lastClicked[0].setStyle("-fx-background-color: grey");
                    }
                    pane.setStyle("-fx-background-color: aliceblue");
                    lastClicked[0] = pane;
                }
            });

            products_box.getChildren().add(pane);
        }

        body.getChildren().addAll(scrollPane);
        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);
    }

}
