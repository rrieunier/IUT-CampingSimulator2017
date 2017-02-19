package fr.iut.view;

import javafx.geometry.HPos;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by theo on 15/02/17.
 */
public class ReductionsLine {

    private Text text;
    private RadioButton radioButton;
    private Integer value;
    private Text textValue;

    //TODO : VALUE < 100

    public ReductionsLine(String name, int val, GridPane gridPane, int i){
        value = val;

        text = new Text(name);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);
        gridPane.add(text, 0, i);

        textValue = new Text("(" + value.toString() + "%)");
        textValue.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        textValue.setFill(Color.WHITESMOKE);
        gridPane.add(textValue, 1, i);

        radioButton = new RadioButton();
        GridPane.setHalignment(radioButton, HPos.CENTER);
        gridPane.add(radioButton, 2, i);
    }

    public Text getText() { return text; }

    public RadioButton getRadioButton() { return radioButton; }

    public Integer getValue() { return value; }

    public void setValue(Integer i) { value = i; }

}
