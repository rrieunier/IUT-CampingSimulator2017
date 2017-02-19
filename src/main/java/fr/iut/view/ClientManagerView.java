package fr.iut.view;

import fr.iut.controller.ClientsController;
import fr.iut.persistence.entities.Client;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by shellcode on 2/17/17.
 */
public class ClientManagerView extends SubScene {

    ClientsController controller;

    public ClientManagerView(ClientsController controller) {
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = controller;

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des clients");
        root.setTop(header);

        VBox wrapper1 = new VBox();
        wrapper1.setSpacing(10);
        BorderPane.setMargin(wrapper1, new Insets(10));
        VBox clients = new VBox();
        ScrollPane clientsScroll = new ScrollPane(clients);
        clientsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        clientsScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);

        int i = 0;
        for(Client client : controller.getClients()) {
            HBox clientBox = new HBox();
            clientBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
            clientBox.setPadding(new Insets(20));

            Text clientText = new Text(client.getFirstname() + " " + client.getLastname());
            clientText.setFont(new Font(16));
            clientText.setFill(Color.WHITE);
            clientBox.getChildren().add(clientText);

            if(i++ % 2 == 1)
                clientBox.setStyle("-fx-background-color: #336699;");
            else
                clientBox.setStyle("-fx-background-color: #0F355C;");

            clients.getChildren().add(clientBox);
        }

        Button newClient = new Button("Nouveau Client");
        newClient.getStylesheets().add(new File("res/style.css").toURI().toString());
        newClient.getStyleClass().add("record-sales");
        newClient.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        //newClient.setOnAction(event -> new InputsListDialog()); // TODO


        wrapper1.getChildren().addAll(clientsScroll, newClient);

        root.setLeft(wrapper1);

        VBox clientDetailsWrapper = new VBox();
        BorderPane.setMargin(clientDetailsWrapper, new Insets(30));
        clientDetailsWrapper.setStyle("-fx-background-color: white;");
        clientDetailsWrapper.setMinHeight(HomeView.TAB_CONTENT_H);
        HeaderView headerDetails = new HeaderView("Détails");

        clientDetailsWrapper.getChildren().add(headerDetails);
        root.setCenter(clientDetailsWrapper);
    }
}
