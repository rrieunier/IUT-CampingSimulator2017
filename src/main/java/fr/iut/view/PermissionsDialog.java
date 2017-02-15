package fr.iut.view;

import fr.iut.App;
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

    public PermissionsDialog(){
        setTitle("Permissions");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        VBox wrapper = new VBox();

        GridPane gridPane = new GridPane();
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
        ArrayList<PermissionsLine> permissionsLines = new ArrayList<>(labels.length);
        for(int i=0; i<labels.length; i++) {
            PermissionsLine permissionsLine = new PermissionsLine(labels[i], gridPane, i);
            permissionsLines.add(permissionsLine);
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
                for (PermissionsLine permissionsLine : permissionsLines){
                    Pair<Boolean, Boolean> pair = new Pair<>(permissionsLine.getReadButton().isSelected(), permissionsLine.getReadEdit().isSelected());
                    map.put(permissionsLine.getText().toString(), pair);
                }
            }
            return map;
        });
    }

}
