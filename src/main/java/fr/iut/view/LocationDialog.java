package fr.iut.view;

import fr.iut.persistence.domain.Location;
import fr.iut.persistence.domain.Spot;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shellcode on 2/8/17.
 */
public class LocationDialog extends Dialog<Map<String, String>> {

    public LocationDialog(ImageView imageView) {
        this(imageView, null);
    }

    public LocationDialog(ImageView imageView, Location location) {

        Spot spot = null;

        if(location instanceof Spot)
            spot = (Spot)location;

        setTitle("Création d'emplacement");
        setHeaderText("Renseignez les informations concernant cet emplacement.");
        setGraphic(imageView);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);

        ButtonType deleteButtonType = new ButtonType("Supprimer");

        if(location != null)
            getDialogPane().getButtonTypes().add(deleteButtonType);

        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nom");

        if(location != null)
            name.setText(location.getName());

        Spinner<Integer> capacity = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);

        if(spot != null)
            valueFactory.setValue(spot.getCapacity());

        capacity.setValueFactory(valueFactory);
        CheckBox water = new CheckBox("Point d'eau");
        CheckBox elec = new CheckBox("Electricité");
        CheckBox shadow = new CheckBox("Ombre");

        if(spot != null) {
            water.setSelected(spot.hasWater());
            elec.setSelected(spot.hasElectricity());
            shadow.setSelected(spot.hasShadow());
        }

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Capacité:"), 0, 1);
        grid.add(capacity, 1, 1);
        grid.add(water, 1, 2);
        grid.add(elec, 1, 3);
        grid.add(shadow, 1, 4);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = getDialogPane().lookupButton(okButtonType);
        loginButton.setDisable(name.getText().length() == 0);

        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);

        setResultConverter(dialogButton -> {

            Map<String, String> map = null;

            if (dialogButton == okButtonType) {
                map = new HashMap<>(5);
                map.put("name", name.getText());
                map.put("capacity", capacity.getValue().toString());
                map.put("water", Boolean.toString(water.isSelected()));
                map.put("elec", Boolean.toString(elec.isSelected()));
                map.put("shadow", Boolean.toString(shadow.isSelected()));
            }

            else if(dialogButton == deleteButtonType) {
                map = new HashMap<>(1);
                map.put("delete", "delete");
            }

            return map;
        });
    }
}
