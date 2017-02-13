package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by theo on 13/02/17.
 */
public class ReductionsView extends Scene {

    public static final double REDUCTIONS_HEIGHT = App.SCREEN_H / 2;
    public static final double REDUCTIONS_WIDTH = App.SCREEN_W / 2;

    private App app;

    public ReductionsView(App app){
        super(new VBox(), REDUCTIONS_WIDTH, REDUCTIONS_HEIGHT);
        this.app = app;

        VBox wrapper = (VBox) getRoot();
        wrapper.setSpacing(REDUCTIONS_HEIGHT/10);
        wrapper.setStyle("-fx-background-color: rgb(12, 27, 51);");

        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(REDUCTIONS_HEIGHT);
        gridPane.setAlignment(Pos.TOP_CENTER);


        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(10);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Réductions applicables");
        header.setMinWidth(REDUCTIONS_WIDTH);
        wrapper.getChildren().add(header);

        String[] labels = {"Etudiant", "Senior", "Famille", "Groupe", "Ancienneté" };
        for (int i = 0; i < labels.length; i++) {
            Text text = new Text(labels[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px");
            text.setFill(Color.WHITESMOKE);
            gridPane.add(text, 0, i);

            RadioButton radioButton = new RadioButton();
            GridPane.setHalignment(radioButton, HPos.CENTER);
            gridPane.add(radioButton, 1, i);
        }

        TextField textField = new TextField();
        textField.setMaxWidth(REDUCTIONS_WIDTH/15);
        textField.setMaxHeight(REDUCTIONS_HEIGHT/15);
        textField.setPromptText("%");
        textField.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px;");
        gridPane.add(textField, 2, labels.length-1);

        Button confirm = new Button("Valider");
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");
        confirm.setMinSize(REDUCTIONS_WIDTH / 6, REDUCTIONS_HEIGHT / 10);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        GridPane.setHalignment(confirm, HPos.CENTER);
        gridPane.add(confirm,0, labels.length+1);

        Button cancel = new Button("Annuler");
        cancel.getStylesheets().add(new File("res/style.css").toURI().toString());
        cancel.getStyleClass().add("record-sales");
        cancel.setMinSize(REDUCTIONS_WIDTH / 6, REDUCTIONS_HEIGHT / 10);
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        GridPane.setHalignment(cancel, HPos.CENTER);
        gridPane.add(cancel, 2, labels.length+1);

        wrapper.getChildren().add(gridPane);
    }
}
