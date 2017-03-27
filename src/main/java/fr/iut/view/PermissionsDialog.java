package fr.iut.view;

import fr.iut.App;
import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by theo on 08/02/17.
 */
public class PermissionsDialog extends Dialog<ArrayList<Boolean>> {

    public static final double PERMISSION_WIDTH = App.SCREEN_W / 3;
    public static final double PERMISSION_HEIGHT = App.SCREEN_H / 3;

    private ArrayList<RadioButton> radioButtonArrayList = new ArrayList<>();
    private GridPane gridPane = new GridPane();

    private void add(String name, int i){
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

        radioButtonArrayList.add(readButton);
        radioButtonArrayList.add(editButton);
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
            ArrayList<Boolean> permissionsArrayList = new ArrayList<>();
            if (dialogButton == okButtonType) {
                for (RadioButton radioButton: radioButtonArrayList) {
                    permissionsArrayList.add(radioButton.isSelected());
                }
            }
            return permissionsArrayList;
        });
    }

    public void checkPermissions(Employee employee){
        if(employee.getAuthorizations() != null){
            for (Authorization a: employee.getAuthorizations()) {
                for (Authorization autorization :Authorization.values()){
                    if(employee.getAuthorizations().contains(autorization))
                        radioButtonArrayList.get(a.ordinal()).setSelected(true);
                }
            }
        }
    }
}
