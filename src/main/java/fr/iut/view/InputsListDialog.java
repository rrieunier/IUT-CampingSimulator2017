package fr.iut.view;

import fr.iut.App;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by theo on 17/02/17.
 */

public class InputsListDialog extends Dialog<Map<String,String>>{

    public static final double WINDOW_WIDTH = App.SCREEN_W/4.5;
    public static final double WINDOW_HEIGHT = App.SCREEN_H/1.3;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private VBox wrapper;

    public void add(String text){
        TextField textField = new TextField();
        textField.setMaxWidth(WINDOW_WIDTH/1.1);
        textField.setMinHeight(WINDOW_HEIGHT / 15);
        textField.setPromptText(text);
        textField.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");
        textFields.add(textField);
        wrapper.getChildren().add(textField);
    }

    public InputsListDialog(String title){
        setTitle(title);

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        wrapper = new VBox();
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setSpacing(20);

        HeaderView header = new HeaderView(title);
        header.setMinWidth(WINDOW_WIDTH);
        wrapper.getChildren().add(header);

        ButtonType okButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hbox = (HBox) dialogPane.lookup(".container");
        hbox.getChildren().add(spacer);

        dialogPane.setContent(wrapper);

        setResultConverter((ButtonType dialogButton) -> {
            Map<String, String> map = null;

            if(dialogButton == okButtonType){
                map = new HashMap<>();
                for (TextField textField : textFields){
                    map.put(textField.getPromptText(), textField.getText());
                }
            }

            return map;
        });

    }
}
