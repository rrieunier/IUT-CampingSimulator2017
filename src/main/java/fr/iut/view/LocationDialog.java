package fr.iut.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/8/17.
 */
public class LocationDialog extends Dialog<Map<String, String>> {

    public LocationDialog(ImageView imageView) {

        setTitle("Création d'emplacement");
        setHeaderText("Renseignez les informations concernant cet emplacement.");
        setGraphic(imageView);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nom");
        Spinner<Integer> capacity = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);
        capacity.setValueFactory(valueFactory);
        CheckBox water = new CheckBox("Point d'eau");
        CheckBox elec = new CheckBox("Electricité");
        CheckBox shadow = new CheckBox("Ombre");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Capacité:"), 0, 1);
        grid.add(capacity, 1, 1);
        grid.add(water, 1, 2);
        grid.add(elec, 1, 3);
        grid.add(shadow, 1, 4);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = getDialogPane().lookupButton(okButtonType);
        loginButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);

        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Map<String, String> map = new HashMap<>(5);
                map.put("name", name.getText());
                map.put("capacity", capacity.getValue().toString());
                map.put("water", Boolean.toString(water.isSelected()));
                map.put("elec", Boolean.toString(elec.isSelected()));
                map.put("shadow", Boolean.toString(shadow.isSelected()));
                return map;
            }
            return null;
        });
    }
}
