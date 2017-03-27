package fr.iut.view;

import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import fr.iut.persistence.entities.Spot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shellcode on 3/23/17.
 */
public class ReservationManagerDialog extends Dialog<Void> {

    private Reservation reservationToEdit;

    public ReservationManagerDialog(ReservationsController controller, Reservation reservation, ImageView reservationPic) {
        this(controller, reservation, null, reservationPic);
        setTitle("Création d'une réservation");
    }

    public ReservationManagerDialog(ReservationsController controller) {
        this(controller, null, null, null);
        setTitle("Création d'une réservation");
    }

    private  ReservationManagerDialog(ReservationsController controller, Reservation reservationToEdit, Client clientOfNewReservation, ImageView reservationPic) {

        this.reservationToEdit = reservationToEdit;

        Client clientOfTheReservation = clientOfNewReservation;

        if(clientOfNewReservation == null && reservationToEdit != null)
            clientOfTheReservation = reservationToEdit.getClient();

        VBox wrapper = new VBox();

        setTitle("Gestion de la réservation");

        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        HeaderView header = new HeaderView("Réservation");

        if(reservationPic != null)
            header.setRight(reservationPic);

        wrapper.getChildren().add(header);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(okButtonType);

        ButtonType deleteButtonType = new ButtonType("Supprimer");

        if(reservationToEdit != null)
            getDialogPane().getButtonTypes().add(deleteButtonType);

        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Text clientResaText = new Text("Réservé par : ");
        clientResaText.setFont(new Font(16));
        clientResaText.setFill(Color.WHITE);

        Text nameClientText = null;
        ComboBox<Client> clientsComboBox = null;

        if(clientOfTheReservation != null) {
            nameClientText = new Text();
            nameClientText.setText(clientOfTheReservation.getFirstname() + " " + clientOfTheReservation.getLastname());
            nameClientText.setFont(new Font(16));
            nameClientText.setFill(Color.WHITE);
        }

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

            personCountInput.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, (reservationToEdit != null) ? reservationToEdit.getPersonCount() : 1));

        DatePicker beginTime = new DatePicker();

        if(reservationToEdit != null)
            beginTime.setValue(Instant.ofEpochMilli(reservationToEdit.getStarttime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        DatePicker endTime = new DatePicker();

        if(reservationToEdit != null && reservationToEdit.getEndtime() != null)
            endTime.setValue(Instant.ofEpochMilli(reservationToEdit.getEndtime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());

        ObservableList<Spot> locations = FXCollections.observableArrayList();
        locations.addAll(controller.getAllSpots());

        ComboBox<Spot> locationComboBox = new ComboBox<>(locations);

        locationComboBox.setConverter(new StringConverter<Spot>() {
            @Override
            public String toString(Spot spot) {
                return spot.getName();
            }

            @Override
            public Spot fromString(String s) {
                return null;
            }
        });

        if(reservationToEdit != null)
            locationComboBox.setValue(reservationToEdit.getSpot());


        Button billButton = new Button("Facture");
        billButton.getStyleClass().add("record-sales");

        if(reservationToEdit != null) {
            billButton.setOnAction(action -> {
                BillSummaryView billSummaryView = new BillSummaryView(this.reservationToEdit, controller);
                billSummaryView.showAndWait();
            });
        }

        grid.add(clientResaText, 0, 0);

        if(nameClientText != null)
            grid.add(nameClientText, 1, 0);

        else {
            ObservableList<Client> clients = FXCollections.observableArrayList();
            clients.addAll(controller.getAllClients());

            clientsComboBox = new ComboBox<>(clients);

            clientsComboBox.setConverter(new StringConverter<Client>() {
                @Override
                public String toString(Client client) {
                    return client.getFirstname() + " " + client.getLastname();
                }

                @Override
                public Client fromString(String s) {
                    return null;
                }
            });

            grid.add(clientsComboBox, 1, 0);
        }

        grid.add(personCountText, 0, 1);
        grid.add(personCountInput, 1, 1);

        grid.add(beginText, 0, 2);
        grid.add(beginTime, 1, 2);

        grid.add(endText, 0, 3);
        grid.add(endTime, 1, 3);

        grid.add(locationText, 0, 4);
        grid.add(locationComboBox, 1, 4);

        if(reservationToEdit != null)
            grid.add(billButton, 1, 5);

        wrapper.getChildren().add(grid);

        getDialogPane().setContent(wrapper);

        ComboBox<Client> finalClientsComboBox = clientsComboBox;
        Client finalClientOfTheReservation = clientOfTheReservation;
        setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {

                if(this.reservationToEdit == null)
                    this.reservationToEdit = new Reservation();

                Instant instant = Instant.from(beginTime.getValue().atStartOfDay(ZoneId.systemDefault()));
                Date dateBegin = Date.from(instant);
                instant = Instant.from(endTime.getValue().atStartOfDay(ZoneId.systemDefault()));
                Date dateEnd = Date.from(instant);

                Calendar c = Calendar.getInstance();
                c.setTime(dateBegin);
                this.reservationToEdit.setStarttime(new Timestamp(c.getTimeInMillis()));

                c.setTime(dateEnd);
                this.reservationToEdit.setEndtime(new Timestamp(c.getTimeInMillis()));

                this.reservationToEdit.setSpot(locationComboBox.getValue());
                this.reservationToEdit.setPersonCount((Integer)personCountInput.getValue());

                if(finalClientOfTheReservation == null)
                    this.reservationToEdit.setClient(finalClientsComboBox.getValue());

                if(reservationToEdit == null) //paramètre constructeur, alors que les autres sont des attributs de l'objet
                    controller.createReservation(this.reservationToEdit);
                else
                    controller.updateReservation(this.reservationToEdit);
            }

            else if(dialogButton == deleteButtonType) {
                if(this.reservationToEdit != null)
                    controller.removeReservation(this.reservationToEdit);
            }

            return null;
        });
    }
}
