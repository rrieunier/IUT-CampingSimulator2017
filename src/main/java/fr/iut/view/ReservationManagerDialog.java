package fr.iut.view;

import fr.iut.persistence.entities.Reservation;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Created by shellcode on 3/23/17.
 */
public class ReservationManagerDialog extends Dialog<Void> {

    public ReservationManagerDialog(Reservation reservation, ImageView reservationPic) {
        setTitle("Création de réservation");
        setHeaderText("Renseignez les informations concernant cet emplacement.");
        setGraphic(reservationPic);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);

        ButtonType deleteButtonType = new ButtonType("Supprimer");
        getDialogPane().getButtonTypes().add(deleteButtonType);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nom");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Capacité:"), 0, 1);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {

            if (dialogButton == okButtonType) {

            }

            else if(dialogButton == deleteButtonType) {

            }

            return null;
        });
    }
}
