package fr.iut.view;

import fr.iut.App;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import java.util.Map;

/**
 * Created by theo on 08/02/17.
 */
public class PermissionsView extends Dialog<Map<String, Pair<Boolean, Boolean>>> {

    public static final double PERMISSION_WIDTH = App.SCREEN_W / 2;
    public static final double PERMISSION_HEIGHT = App.SCREEN_H / 2;

    //private App app;

    public PermissionsView(){
        //super(new VBox(), PERMISSION_WIDTH, PERMISSION_HEIGHT);
        //this.app = app;

        VBox wrapper = new VBox();

        setTitle("Permissions");

        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(PERMISSION_HEIGHT);
        gridPane.setStyle("-fx-background-color: rgb(12, 27, 51);");
        gridPane.setAlignment(Pos.CENTER);


        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            gridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 8; i++){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20);
            gridPane.getRowConstraints().add(row);
        }

        HeaderView header = new HeaderView("Permissions");
        header.setMinWidth(PERMISSION_WIDTH);
        wrapper.getChildren().add(header);
        //gridPane.add(header, 0,0);
        //getDialogPane().setContent(header);

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
        getDialogPane().getButtonTypes().add(okButtonType);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        /*Button confirm = new Button("Valider");
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");
        confirm.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        GridPane.setHalignment(confirm, HPos.RIGHT);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        gridPane.add(confirm,0, labels.length+1);

        Button cancel = new Button("Annuler");
        cancel.getStylesheets().add(new File("res/style.css").toURI().toString());
        cancel.getStyleClass().add("record-sales");
        cancel.setMinSize(PERMISSION_WIDTH / 6, PERMISSION_HEIGHT / 10);
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            }
        });
        gridPane.add(cancel, 2, labels.length+1);*/

        wrapper.getChildren().add(gridPane);
        getDialogPane().setContent(wrapper);

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
