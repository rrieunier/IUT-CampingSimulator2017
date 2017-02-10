package fr.iut.view;

import fr.iut.App;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by theo on 08/02/17.
 */
public class AutorisationsView extends Scene {

    private App app;

    private Button confirm = new Button("Valider");

    public AutorisationsView(App app){
        super(new StackPane());
        this.app = app;

        StackPane stackPane = (StackPane)getRoot();

        ScrollPane scrollPane = new ScrollPane();
        VBox verticalWrap = new VBox();
        scrollPane.setContent(verticalWrap);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        verticalWrap.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Permissions");

        setFill(Color.rgb(12,27,51));

        verticalWrap.getChildren().add(header);

        stackPane.getChildren().add(scrollPane);

        verticalWrap.setAlignment(Pos.TOP_CENTER);

    }
}
