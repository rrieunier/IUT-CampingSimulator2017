package fr.iut.view;

import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shellcode on 3/21/17.
 */
public class ReservationsView extends ScrollPane {

    /**
     * instance of the controller
     */
    ReservationsController controller;
    VBox reservationsWrapper;

    /**
     * @param controller
     * reservations list
     */
    public ReservationsView(ReservationsController controller) {
        this.controller = controller;

        reservationsWrapper = new VBox();

        refresh();

        setContent(reservationsWrapper);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void refresh() {

        reservationsWrapper.getChildren().clear();

        int i = 0;
        for(Reservation reservation : controller.getReservations()) {
            HBox horizontalWrapper = new HBox();
            horizontalWrapper.setSpacing(10);
            horizontalWrapper.setAlignment(Pos.CENTER);

            String filename = null;

            switch (reservation.getSpot().getSpotType()) {
                case HOUSE: filename = "cabin.png"; break;
                case TRAILER: filename = "trailer.png"; break;
                case RESTAURANT: filename = "cutlery.png"; break;
                case PARKING: filename = "parking.png"; break;
                case POOL: filename = "swimming-pool.png"; break;
                case SPORT: filename = "kayak.png"; break;
                case TENT: filename = "tent.png"; break;
            }

            Image bigImage = new Image(new File("res/items/x64/" + filename).toURI().toString());
            ImageView locationImageView = new ImageView(bigImage);
            HBox.setMargin(locationImageView, new Insets(0, 0, 0, 20));
            horizontalWrapper.getChildren().add(locationImageView);

            VBox verticalWrapper = new VBox();
            horizontalWrapper.getChildren().add(verticalWrapper);
            verticalWrapper.setAlignment(Pos.CENTER_LEFT);

            horizontalWrapper.setOnMouseClicked(mouseEvent -> {
                ReservationManagerDialog reservationManagerDialog = new ReservationManagerDialog(controller, reservation, new ImageView(bigImage));
                reservationManagerDialog.showAndWait();
                refresh();
            });

            verticalWrapper.setMinWidth(HomeView.TAB_CONTENT_W / 4);
            verticalWrapper.setPadding(new Insets(20));

            Client client = reservation.getClient();

            Text clientNameText = new Text(client.getFirstname() + " " + client.getLastname());
            clientNameText.setFont(new Font(20));
            clientNameText.setFill(Color.WHITE);
            verticalWrapper.getChildren().add(clientNameText);

            Text spotText = new Text("Emplacement : " + reservation.getSpot().getName());
            spotText.setFont(new Font(15));
            spotText.setFill(Color.WHITE);
            verticalWrapper.getChildren().add(spotText);

            Text personCountText = new Text("Nombre de personnnes : " + reservation.getPersonCount());
            personCountText.setFont(new Font(15));
            personCountText.setFill(Color.WHITE);
            verticalWrapper.getChildren().add(personCountText);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Text datesText = new Text("Dates début/fin : " + dateFormat.format(new Date(reservation.getStarttime().getTime())) + " / " + dateFormat.format(new Date(reservation.getEndtime().getTime())));
            datesText.setFont(new Font(15));
            datesText.setFill(Color.WHITE);
            verticalWrapper.getChildren().add(datesText);

            if (i++ % 2 == 1)
                horizontalWrapper.setStyle("-fx-background-color: #336699;");
            else
                horizontalWrapper.setStyle("-fx-background-color: #0F355C;");

            reservationsWrapper.getChildren().add(horizontalWrapper);
        }
    }
}
