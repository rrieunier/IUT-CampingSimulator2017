package fr.iut.view;

import fr.iut.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import java.util.Map;

/**
 * Created by theo on 08/02/17.
 */
public class PermissionsDialog extends Dialog<Map<String, Pair<Boolean, Boolean>>> {

    public static final double PERMISSION_WIDTH = App.SCREEN_W / 3;
    public static final double PERMISSION_HEIGHT = App.SCREEN_H / 3;

    private ArrayList<Map<Text,Pair<RadioButton, RadioButton>>> permissionsArrayList = new ArrayList<>();
    private GridPane gridPane = new GridPane();

    public void add(String name, int i){
        Text text = new Text(name);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        text.setFill(Color.WHITESMOKE);
        gridPane.add(text, 0, i+1);

        RadioButton readButton = new RadioButton();
        GridPane.setHalignment(readButton, HPos.CENTER);
        gridPane.add(readButton, 1,i+1);

        RadioButton editButton = new RadioButton();
        GridPane.setHalignment(editButton, HPos.CENTER);
        gridPane.add(editButton, 2, i+1);

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                readButton.setSelected(true);
            }
        });

        Pair<RadioButton, RadioButton> pair = new Pair<>(readButton, editButton);
        Map<Text, Pair<RadioButton, RadioButton>> map = new HashMap<>();
        map.put(text, pair);
        permissionsArrayList.add(map);
    }

    public PermissionsDialog(){
        setTitle("Permissions");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        VBox wrapper = new VBox();

        gridPane.setMinHeight(PERMISSION_HEIGHT);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 7; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Permissions");
        header.setMinWidth(PERMISSION_WIDTH);
        wrapper.getChildren().add(header);

        String[] titles = {"Lecture", "Modification"};
        for(int i=0; i<titles.length; i++){
            Text text = new Text(titles[i]);
            text.setStyle("-fx-font-weight: bold;" +
                    "-fx-font-size: 17px" );
            text.setFill(Color.WHITESMOKE);
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i+1, 0);
        }

        String[] labels = {"Clients", "Incidents", "Salariés", "Fournisseurs", "Stocks", "Carte"};
        for(int i=0; i<labels.length; i++) {
            add(labels[i], i);
        }

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

        setResultConverter((ButtonType dialogButton) -> {
            Map<String, Pair<Boolean, Boolean>> map = null;

            if (dialogButton == okButtonType) {
                map = new HashMap<>(labels.length);
                for (int i = 0; i < permissionsArrayList.size(); i++) {
                    for (Map.Entry<Text, Pair<RadioButton, RadioButton>> entry : permissionsArrayList.get(i).entrySet()){
                        Pair<Boolean, Boolean> pair = new Pair<>(entry.getValue().getKey().isSelected(), entry.getValue().getValue().isSelected());
                        map.put(entry.getKey().getText().toString(), pair);
                    }
                }
            }
            return map;
        });
    }

}
