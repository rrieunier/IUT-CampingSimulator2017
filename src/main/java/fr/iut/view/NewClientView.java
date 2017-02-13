package fr.iut.view;

/**
 * Created by Yannini on 09/02/2017.
 */

import fr.iut.App;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

public class NewClientView extends Scene {

    public static final double WINDOW_WIDTH = App.SCREEN_W/4.5;
    public static final double WINDOW_HEIGHT = App.SCREEN_H/1.3;

    private App app;

    public NewClientView (App app){
        super(new VBox(), WINDOW_WIDTH, WINDOW_HEIGHT);
        VBox wrapper = (VBox) getRoot();
        this.app = app;

        wrapper.setMinHeight(WINDOW_HEIGHT);
        wrapper.setStyle("-fx-background-color: rgb(12, 27, 51);");
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setSpacing(WINDOW_HEIGHT/20);

        HeaderView headerView = new HeaderView("Nouveau Client");
        headerView.setMinWidth(WINDOW_WIDTH);

        TextField last_name = new TextField();
        last_name.setMaxWidth(WINDOW_WIDTH/1.1);
        last_name.setMinHeight(WINDOW_HEIGHT / 10);
        last_name.setPromptText("Nom");
        last_name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        TextField first_name = new TextField();
        first_name.setMaxWidth(WINDOW_WIDTH / 1.1);
        first_name.setMinHeight(WINDOW_HEIGHT / 10);
        first_name.setPromptText("Prénom");
        first_name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        TextField address = new TextField();
        address.setMaxWidth(WINDOW_WIDTH / 1.1);
        address.setMinHeight(WINDOW_HEIGHT / 10);
        address.setPromptText("Adresse");
        address.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        TextField phone = new TextField();
        phone.setMaxWidth(WINDOW_WIDTH / 1.1);
        phone.setMinHeight(WINDOW_HEIGHT / 10);
        phone.setPromptText("N° de téléphone");
        phone.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        VBox fields = new VBox();
        fields.getChildren().addAll(last_name, first_name, address, phone);
        fields.setSpacing(WINDOW_HEIGHT / 20);
        fields.setAlignment(Pos.CENTER);
        fields.setLayoutX(WINDOW_WIDTH/4);
        fields.setLayoutY(WINDOW_HEIGHT/5);

        Button confirm = new Button("Valider");
        confirm.setMinSize(WINDOW_WIDTH / 4, WINDOW_HEIGHT / 10);
        confirm.setLayoutX((WINDOW_WIDTH - confirm.getMinWidth()) / 2);
        confirm.setLayoutY(WINDOW_HEIGHT - confirm.getMinHeight() - 5);
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");

        wrapper.getChildren().addAll(headerView, fields, confirm);
    }

}
