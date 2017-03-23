package fr.iut.view;

import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shellcode on 3/21/17.
 */
public class ReservationsView extends ScrollPane {

    ReservationsController controller;

    public ReservationsView(ReservationsController controller) {
        this.controller = controller;

        VBox reservationsWrapper = new VBox();

        int i = 0;
        for(Reservation reservation : controller.getReservations()) {
            VBox verticalWrapper = new VBox();
            verticalWrapper.setAlignment(Pos.CENTER);

            verticalWrapper.setOnMouseClicked(mouseEvent -> {
                //TODO : focus sur la map et affichage de la reservation
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Text datesText = new Text("Dates début/fin : " + dateFormat.format(new Date(reservation.getStarttime().getTime())) + " / " + dateFormat.format(new Date(reservation.getEndtime().getTime())));
            datesText.setFont(new Font(15));
            datesText.setFill(Color.WHITE);
            verticalWrapper.getChildren().add(datesText);

            if (i++ % 2 == 1)
                verticalWrapper.setStyle("-fx-background-color: #336699;");
            else
                verticalWrapper.setStyle("-fx-background-color: #0F355C;");

            reservationsWrapper.getChildren().add(verticalWrapper);
        }

        setContent(reservationsWrapper);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
