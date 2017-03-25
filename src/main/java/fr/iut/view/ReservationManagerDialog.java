package fr.iut.view;

import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by shellcode on 3/23/17.
 */
public class ReservationManagerDialog extends Dialog<Void> {

    public ReservationManagerDialog(ReservationsController controller, Reservation reservation, ImageView reservationPic) {

        Client clientOfTheReservation = reservation.getClient();

        VBox wrapper = new VBox();

        setTitle("Gestion de la réservation");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        HeaderView header = new HeaderView("Réservation");
        header.setRight(reservationPic);
        wrapper.getChildren().add(header);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);

        ButtonType deleteButtonType = new ButtonType("Supprimer");
        getDialogPane().getButtonTypes().add(deleteButtonType);

        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Text clientResaText = new Text("Réservé par : ");
        clientResaText.setFont(new Font(16));
        clientResaText.setFill(Color.WHITE);

        Text nameClientText = new Text(clientOfTheReservation.getFirstname() + " " + clientOfTheReservation.getLastname());
        nameClientText.setFont(new Font(16));
        nameClientText.setFill(Color.WHITE);

        Text personCountText = new Text("Nombre de personnes : ");
        personCountText.setFont(new Font(16));
        personCountText.setFill(Color.WHITE);

        Text beginText = new Text("Début du séjour : ");
        beginText.setFont(new Font(16));
        beginText.setFill(Color.WHITE);

        Text endText = new Text("Fin du séjour : ");
        endText.setFont(new Font(16));
        endText.setFill(Color.WHITE);

        Text locationText = new Text("Emplacement : ");
        locationText.setFont(new Font(16));
        locationText.setFill(Color.WHITE);

        Spinner personCountInput = new Spinner();
        personCountInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, reservation.getPersonCount()));

        DatePicker beginTime = new DatePicker();
        beginTime.setValue(Instant.ofEpochMilli(reservation.getStarttime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        DatePicker endTime = new DatePicker();
        endTime.setValue(Instant.ofEpochMilli(reservation.getEndtime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        ObservableList<String> locations = FXCollections.observableArrayList();
        locations.addAll(controller.getAllLocationNames());

        ComboBox<String> locationComboBox = new ComboBox<>(locations);
        locationComboBox.setValue(reservation.getSpot().getName());


        Button billButton = new Button("Facture");
        billButton.getStyleClass().add("record-sales");
        billButton.setOnAction(action -> {
            //TODO : mettre la fenetre de facturation de maxime
        });

        grid.add(clientResaText, 0, 0);
        grid.add(nameClientText, 1, 0);

        grid.add(personCountText, 0, 1);
        grid.add(personCountInput, 1, 1);

        grid.add(beginText, 0, 2);
        grid.add(beginTime, 1, 2);

        grid.add(endText, 0, 3);
        grid.add(endTime, 1, 3);

        grid.add(locationText, 0, 4);
        grid.add(locationComboBox, 1, 4);

        grid.add(billButton, 0, 5);

        wrapper.getChildren().add(grid);

        getDialogPane().setContent(wrapper);

        setResultConverter(dialogButton -> {

            if (dialogButton == okButtonType) {
                /*
                //TODO : modifier la reservation en fonction de toutes les valeurs qui ont été modifiées et update la BDD
                controller.updateReservation(reservation);
                */
            }

            else if(dialogButton == deleteButtonType) {
                controller.removeReservation(reservation);
            }

            return null;
        });
    }
}
