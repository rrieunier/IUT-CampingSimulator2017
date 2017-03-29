package fr.iut.view;

import fr.iut.App;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Spot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.io.File;
import java.util.*;

/**
 * Created by theo on 17/02/17.
 */

public class InputsListDialog extends Dialog<Map<String,String>>{

    public static final double WINDOW_WIDTH = App.SCREEN_W/4.5;
    public static final double WINDOW_HEIGHT = App.SCREEN_H/1.3;
    private ArrayList<TextField> textFields = new ArrayList<>();
    private VBox wrapper;


    public void addTextField(String text){
        TextField textField = new TextField();
        textField.setMaxWidth(WINDOW_WIDTH/1.1);
        textField.setMinHeight(WINDOW_HEIGHT / 15);
        textField.setPromptText(text);
        textField.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");
        textFields.add(textField);
        wrapper.getChildren().add(textField);
    }

    public void addPasswordField(String text){
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(WINDOW_WIDTH/1.1);
        passwordField.setMinHeight(WINDOW_HEIGHT / 15);
        passwordField.setPromptText(text);
        passwordField.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");
        textFields.add(passwordField);
        wrapper.getChildren().add(passwordField);
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

    public VBox getWrapper() { return wrapper; }
}
