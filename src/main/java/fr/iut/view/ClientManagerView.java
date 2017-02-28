package fr.iut.view;

import fr.iut.controller.ClientsController;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/17/17.
 */
public class ClientManagerView extends SubScene {

    ClientsController controller;

    boolean editMode = false;

    TextField firstname, name, email, phone;

    Client currentClient = null;

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
        BorderPane.setMargin(wrapper1, new Insets(20));
        VBox clients = new VBox();
        clients.setSpacing(3);
        ScrollPane clientsScroll = new ScrollPane(clients);
        clientsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        clientsScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);

        int i = 0;
        for(Client client : controller.getClients()) {
            HBox clientBox = new HBox();
            clientBox.setOnMouseClicked(mouseEvent -> updateDetail(client));

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
        newClient.setOnAction(event -> {
            controller.createClient();
            // TODO : Clement verifie que c'est propre et me gueule pas dessus stp si j'ai fais de la merde
        });


        wrapper1.getChildren().addAll(clientsScroll, newClient);

        root.setLeft(wrapper1);

        BorderPane clientDetailsWrapper = new BorderPane();
        BorderPane.setMargin(clientDetailsWrapper, new Insets(30));
        clientDetailsWrapper.setStyle("-fx-background-color: grey;");
        HeaderView headerDetails = new HeaderView("Détails");

        VBox detailsWrapper = new VBox();

        HBox namesField = new HBox();
        firstname = new TextField();
        firstname.setPromptText("Prénom...");
        firstname.setDisable(true);
        firstname.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        name = new TextField();
        name.setPromptText("Nom...");
        name.setDisable(true);
        name.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        namesField.setSpacing(10);
        namesField.setAlignment(Pos.CENTER);
        namesField.getChildren().addAll(firstname, name);
        HBox.setHgrow(firstname, Priority.ALWAYS);
        HBox.setHgrow(name, Priority.ALWAYS);

        email = new TextField();
        email.setPromptText("Email...");
        email.setDisable(true);
        email.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        phone = new TextField();
        phone.setPromptText("Téléphone...");
        phone.setDisable(true);
        phone.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        HBox buttonsWrap1 = new HBox();
        HBox buttonsWrap2 = new HBox();
        VBox wrapWrappers = new VBox(buttonsWrap1, buttonsWrap2);

        buttonsWrap1.setSpacing(30);
        buttonsWrap2.setSpacing(30);
        wrapWrappers.setSpacing(30);
        buttonsWrap1.setAlignment(Pos.CENTER);
        buttonsWrap2.setAlignment(Pos.CENTER);
        wrapWrappers.setAlignment(Pos.CENTER);

        Button editButton = new Button("Modifier");
        editButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        editButton.getStyleClass().add("record-sales");
        editButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        editButton.setOnAction(actionEvent -> {
            if(editMode) {
                firstname.setDisable(true);
                name.setDisable(true);
                email.setDisable(true);
                phone.setDisable(true);
                editButton.setText("Modifier");
            }

            else {
                firstname.setDisable(false);
                name.setDisable(false);
                email.setDisable(false);
                phone.setDisable(false);
                editButton.setText("Sauvegarder");
                //TODO : update du client dans la BDD
            }

            editMode = !editMode;
        });

        Button bookingButton = new Button("Réservations...");
        bookingButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        bookingButton.getStyleClass().add("record-sales");
        bookingButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        bookingButton.setOnAction(actionEvent -> {
            //TODO : aller sur la carte
        });

        buttonsWrap1.getChildren().addAll(editButton, bookingButton);

        Button billButton = new Button("Facturer");
        billButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        billButton.getStyleClass().add("record-sales");
        billButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        billButton.setOnAction(actionEvent -> {
            //TODO : générer facture
        });

        Button reducButton = new Button("Réductions...");
        reducButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        reducButton.getStyleClass().add("record-sales");
        reducButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        reducButton.setOnAction(actionEvent -> {
            Optional<Map<String, Integer>> permissions_result = new ReductionsDialog().showAndWait();
            //TODO : stocker la reduction qqpart
        });

        buttonsWrap2.getChildren().addAll(billButton, reducButton);

        detailsWrapper.getChildren().addAll(namesField, email, phone);
        detailsWrapper.setSpacing(HomeView.TAB_CONTENT_H / 15);

        BorderPane.setMargin(detailsWrapper, new Insets(30));
        clientDetailsWrapper.setPadding(new Insets(0, 0, 30, 0));
        clientDetailsWrapper.setTop(headerDetails);
        clientDetailsWrapper.setBottom(wrapWrappers);
        clientDetailsWrapper.setCenter(detailsWrapper);
        BorderPane.setAlignment(wrapWrappers, Pos.CENTER);
        root.setCenter(clientDetailsWrapper);
    }

    private void updateDetail(Client client) {
        firstname.setText(client.getFirstname());
        name.setText(client.getLastname());
        email.setText(client.getEmail());
        phone.setText(client.getPhone());

        currentClient = client;
        editMode = false;
    }
}
