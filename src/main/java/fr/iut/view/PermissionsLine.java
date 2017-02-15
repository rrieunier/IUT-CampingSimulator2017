package fr.iut.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by theo on 15/02/17.
 */
public class PermissionsLine {

    private Text text;
    private RadioButton readButton;
    private RadioButton editButton;

    public PermissionsLine(String name, GridPane gridPane, int i){
        text = new Text(name);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);
        gridPane.add(text, 0, i+1);

        readButton = new RadioButton();
        GridPane.setHalignment(readButton, HPos.CENTER);
        gridPane.add(readButton, 1,i+1);

        editButton = new RadioButton();
        GridPane.setHalignment(editButton, HPos.CENTER);
        gridPane.add(editButton, 2, i+1);

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                readButton.setSelected(true);
            }
        });
    }

    public Text getText() { return text; }

    public RadioButton getReadButton() { return readButton; }

    public RadioButton getReadEdit() { return editButton; }
}
