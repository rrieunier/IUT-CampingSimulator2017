package fr.iut.view;

import fr.iut.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by theo on 13/02/17.
 */
public class ReductionsDialog extends Dialog<Float> {

    public static final double REDUCTIONS_HEIGHT = App.SCREEN_H / 3;
    public static final double REDUCTIONS_WIDTH = App.SCREEN_W / 3;

    private GridPane gridPane = new GridPane();
    private ArrayList<Map<Text, Pair<RadioButton, Float>>> reductionsArrayList = new ArrayList<>();

    private Float default_reduc;

    public ReductionsDialog() {
        this(0.f);
    }

    /**
     * @param default_reduc is the default value to check when showing the dialog
     */
    public ReductionsDialog(Float default_reduc){
        this.default_reduc = default_reduc;
        setTitle("Réductions");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        VBox wrapper = new VBox();

        gridPane.setMinHeight(REDUCTIONS_HEIGHT);
        gridPane.setAlignment(Pos.TOP_CENTER);


        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Réductions applicables");
        header.setMinWidth(REDUCTIONS_WIDTH);
        wrapper.getChildren().add(header);

        String[] labels = {"Senior", "Etudiant", "Famille", "Groupe", "Autres" };
        Float[] values = {5f, 8f, 10f, 15f, 0f};
        for (int i = 0; i < labels.length; i++) {
            add_line(labels[i], i, values[i]);
        }

        TextField textField = new TextField();
        textField.setMaxWidth(REDUCTIONS_WIDTH/10);
        textField.setMaxHeight(REDUCTIONS_HEIGHT/15);
        textField.setPromptText("%");
        textField.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px;");
        gridPane.add(textField, 1, labels.length-1);

        ButtonType okButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hbox = (HBox) dialogPane.lookup(".container");
        hbox.getChildren().add(spacer);

        wrapper.getChildren().add(gridPane);
        dialogPane.setContent(wrapper);

        setResultConverter( dialogButton -> {

            if (dialogButton == okButtonType){
                if(textField.getLength() > 0){
                    for(Map.Entry<Text, Pair<RadioButton, Float>> textPairEntry : reductionsArrayList.get(reductionsArrayList.size()-1).entrySet()){
                        Pair<RadioButton, Float> pair = new Pair<>(textPairEntry.getValue().getKey(), Float.valueOf(textField.getText()));
                        textPairEntry.setValue(pair);
                    }
                }

                for (int i = 0; i < reductionsArrayList.size(); i++){
                    for (Map.Entry<Text, Pair<RadioButton, Float>> entry : reductionsArrayList.get(i).entrySet()){
                        if(entry.getValue().getKey().isSelected())
                            return entry.getValue().getValue();
                    }
                }
            }

            return null;
        });
    }

    private void add_line(String name, int i, Float value){
        Text text = new Text(name);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);
        gridPane.add(text, 0, i);

        Text textValue = new Text("(" + value.toString() + "%)");
        textValue.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        textValue.setFill(Color.WHITESMOKE);
        gridPane.add(textValue, 1, i);

        RadioButton radioButton = new RadioButton();
        GridPane.setHalignment(radioButton, HPos.CENTER);
        gridPane.add(radioButton, 2, i);

        if(value != 0 && value.equals(default_reduc))
            radioButton.setSelected(true);

        radioButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < reductionsArrayList.size(); i++){
                    for (Map.Entry<Text, Pair<RadioButton, Float>> entry : reductionsArrayList.get(i).entrySet()){
                        if(entry.getValue().getKey().isSelected())
                            entry.getValue().getKey().setSelected(false);
                    }
                }
                radioButton.setSelected(true);
            }
        });

        Pair<RadioButton, Float> pair = new Pair<>(radioButton, value);
        Map<Text, Pair<RadioButton, Float>> map = new HashMap<>();
        map.put(text, pair);
        reductionsArrayList.add(map);
    }
}
