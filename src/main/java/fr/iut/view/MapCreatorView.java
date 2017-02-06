package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by shellcode on 2/6/17.
 */
public class MapCreatorView extends Scene {

    App app;

    public MapCreatorView(App app) {
        super(new AnchorPane());
        this.app = app;

        AnchorPane components = (AnchorPane)getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        VBox verticalWrap = new VBox();

        StackPane header = new StackPane();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #336699;");
        Text title = new Text("Création de la carte");
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title);

        Canvas map = new Canvas(500, 500);
        map.getGraphicsContext2D().setFill(Color.BLACK);
        map.getGraphicsContext2D().fillRect(0, 0, map.getWidth(), map.getHeight());
        map.getGraphicsContext2D().setFill(Color.WHITE);
        map.getGraphicsContext2D().setFont(new Font(20));
        map.getGraphicsContext2D().fillText("Cliquez pour importer une carte...", 80, 235);

        map.setOnMouseClicked(mouseEvent -> {
            map.getGraphicsContext2D().setFill(Color.BLACK);
            map.getGraphicsContext2D().fillRect(0, 0, map.getWidth(), map.getHeight());
            map.getGraphicsContext2D().setFill(Color.WHITE);
            map.getGraphicsContext2D().setFont(new Font(20));
            map.getGraphicsContext2D().fillText("Mdr t'as cru ca allait déjà marcher fdp", 65, 235);
        });

        FlowPane items = new FlowPane();
        items.setPadding(new Insets(10));
        items.setStyle("-fx-background-color: rgb(50, 50, 50);");
        items.setAlignment(Pos.CENTER);
        items.setHgap(10);
        items.setVgap(10);

        for(int i = 0; i < 20; i++) {
            Button button = new Button("Item " + (i + 1));
            items.getChildren().add(button);
        }

        verticalWrap.setMargin(items, new Insets(20));
        verticalWrap.setMargin(map, new Insets(20));
        verticalWrap.getChildren().addAll(header, map, items);

        AnchorPane.setLeftAnchor(verticalWrap, 0.0);
        AnchorPane.setRightAnchor(verticalWrap, 0.0);
        AnchorPane.setTopAnchor(verticalWrap, 0.0);
        AnchorPane.setBottomAnchor(verticalWrap, 0.0);

        verticalWrap.setAlignment(Pos.TOP_CENTER);
        components.getChildren().addAll(verticalWrap);
    }
}
