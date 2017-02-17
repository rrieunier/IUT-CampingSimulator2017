package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by theo on 13/02/17.
 */
public class ReductionsDialog extends Dialog<Map<String, Integer>> {

    public static final double REDUCTIONS_HEIGHT = App.SCREEN_H / 3;
    public static final double REDUCTIONS_WIDTH = App.SCREEN_W / 3;

    private App app; //TODO : il faut un controller, pas une instance de App

    public ReductionsDialog(){
        setTitle("Réductions");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        VBox wrapper = new VBox();

        GridPane gridPane = new GridPane();
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

        String[] labels = {"Etudiant", "Senior", "Famille", "Groupe", "Autres" };
        Integer[] values = {5, 5, 10, 10, 0};
        ArrayList<ReductionsLine> reductionsLines = new ArrayList<ReductionsLine>(labels.length);
        for (int i = 0; i < labels.length; i++) {
            ReductionsLine reductionsLine = new ReductionsLine(labels[i], values[i], gridPane, i);
            reductionsLines.add(reductionsLine);
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
            Map<String, Integer> map = null;

            if (dialogButton == okButtonType){
                map = new HashMap<>(1);
                reductionsLines.get(reductionsLines.size()-1).setValue(Integer.valueOf(textField.getText()));
                for (ReductionsLine reductionsLine : reductionsLines){
                    if(reductionsLine.getRadioButton().isSelected()){
                        map.put(reductionsLine.getText().getText(), reductionsLine.getValue());
                        System.out.println(reductionsLine.getText().getText());
                        System.out.println(reductionsLine.getValue());
                    }
                }
            }
            return map;
        });
    }
}
