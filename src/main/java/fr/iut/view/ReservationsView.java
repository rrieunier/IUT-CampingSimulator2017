package fr.iut.view;

import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
            HBox box = new HBox();
            box.setOnMouseClicked(mouseEvent -> {
                //TODO : focus sur la map et affichage de la reservation
            });

            box.setMinWidth(HomeView.TAB_CONTENT_W / 4);
            box.setPadding(new Insets(20));

            Client client = reservation.getClient();
            Text reservationText = new Text(client.getFirstname() + " " + client.getLastname());
            reservationText.setFont(new Font(20));
            reservationText.setFill(Color.BLACK);
            box.getChildren().add(reservationText);

            if (i++ % 2 == 1)
                box.setStyle("-fx-background-color: #336699;");
            else
                box.setStyle("-fx-background-color: #0F355C;");

            reservationsWrapper.getChildren().add(box);
        }

        setContent(reservationsWrapper);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
