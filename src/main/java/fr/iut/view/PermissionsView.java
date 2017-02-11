package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by theo on 08/02/17.
 */
public class PermissionsView extends Scene {

    public static final double PERMISSION_WIDTH = App.SCREEN_W / 2;
    public static final double PERMISSION_HEIGHT = App.SCREEN_H / 2;
    private static final Color PERMISSION_BACKGROUNG = Color.rgb(12, 27, 51);

    private App app;

    public PermissionsView(App app){
        super(new GridPane());
        this.app = app;

        setFill(PERMISSION_BACKGROUNG);

        GridPane gridPane = (GridPane) getRoot();
        gridPane.setStyle("-fx-background-color: rgb(12, 27, 51);");


        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(30);
        gridPane.getColumnConstraints().add(c);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        gridPane.getRowConstraints().add(new RowConstraints());
        for(int i = 0; i < 7; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(10);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Permissions");
        header.setMinWidth(PERMISSION_WIDTH);
        gridPane.addRow(0, header);


        String[] titles = {"Lecture", "Modification"};
        for(int i=0; i<titles.length; i++){
            Text text = new Text(titles[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px" );
            text.setFill(Color.WHITESMOKE);
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i+1, 1);
        }

        String[] labels = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Carte"};
        for(int i=0; i<labels.length; i++) {
            Text text = new Text(labels[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px");
            text.setFill(Color.WHITESMOKE);
            GridPane.setHalignment(text, HPos.RIGHT);
            gridPane.add(text, 0, i+2);

            RadioButton read_button = new RadioButton();
            GridPane.setHalignment(read_button, HPos.CENTER);
            gridPane.add(read_button, 1,i+2);

            RadioButton edit_button = new RadioButton();
            GridPane.setHalignment(edit_button, HPos.CENTER);
            gridPane.add(edit_button, 2, i+2);
        }

        Button confirm = new Button("Valider");
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");
        confirm.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        GridPane.setHalignment(confirm, HPos.CENTER);
        gridPane.add(confirm,1, 8);

        Button cancel = new Button("Annuler");
        cancel.getStylesheets().add(new File("res/style.css").toURI().toString());
        cancel.getStyleClass().add("record-sales");
        cancel.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        GridPane.setHalignment(cancel, HPos.CENTER);
        gridPane.add(cancel, 2, 8);
    }
}
