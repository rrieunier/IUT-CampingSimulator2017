package fr.iut.view;

import fr.iut.controller.ClientsController;
import fr.iut.persistence.entities.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    private VBox clients;
    ClientsController controller;

    boolean editMode = false;

    TextField firstname, name, email, phone;

    Client currentClient = null;

    public ClientManagerView(ClientsController c) {
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = c;
        controller.createClients();

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des clients");
        root.setTop(header);

        VBox wrapper1 = new VBox();
        wrapper1.setSpacing(10);
        BorderPane.setMargin(wrapper1, new Insets(20));
        clients = new VBox();
        clients.setSpacing(3);
        ScrollPane clientsScroll = new ScrollPane(clients);
        clientsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        clientsScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);
        clientsScroll.setMinWidth(HomeView.TAB_CONTENT_W/4);


        HBox search_bar = new HBox();
        Label search_label = new Label("Rechercher: ");
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        TextField search_field = new TextField();
        search_field.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
        search_field.setPromptText("Nom du client");

        search_label.setLabelFor(search_field);
        search_bar.setAlignment(Pos.CENTER);
        search_bar.getChildren().addAll(search_label, search_field);
        header.setRight(search_bar);

        HBox sort_options = new HBox();
        sort_options.setSpacing(10);
        sort_options.setAlignment(Pos.CENTER);
        ObservableList<String> options =
                FXCollections.observableArrayList("Nom (alphabétique)", "Nom (alphabétique inverse)", "Prénom (alphabétique)", "Prénom (alphabétique inverse)" );
        ComboBox<String> sort_by = new ComboBox<>(options);
        sort_by.getSelectionModel().select(0);
        sort_by.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                createScroll(search_field.getText().toString(), false, sort_by.getSelectionModel().getSelectedIndex());
            }
        });
        Label sort_by_label = new Label("Tri par: ");
        sort_by_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        sort_by_label.setLabelFor(sort_by);


        search_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    createScroll(search_field.getText().toString(), false, sort_by.getSelectionModel().getSelectedIndex());
                    search_field.clear();
                }
            }
        });

        Button newClient = new Button("+");
        newClient.setTooltip(new Tooltip("Nouveau client"));
        newClient.getStylesheets().add(new File("res/style.css").toURI().toString());
        newClient.getStyleClass().add("record-sales");
        newClient.setOnAction(event -> {
            InputsListDialog newClientDialog = new InputsListDialog("Nouveau Client");
            newClientDialog.addTextField("Nom");
            newClientDialog.addTextField("Prénom");
            newClientDialog.addTextField("Téléphone");
            newClientDialog.addTextField("Mail");
            Optional<Map<String, String>> newClient_result = newClientDialog.showAndWait();

            Client client = new Client();
            client.setFirstname(newClient_result.get().get("Prénom"));
            client.setLastname(newClient_result.get().get("Nom"));
            client.setPhone(newClient_result.get().get("Téléphone"));
            client.setEmail(newClient_result.get().get("Mail"));

            controller.saveClient(client);

            createScroll(search_field.getText(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        sort_options.getChildren().addAll(newClient, sort_by_label, sort_by);

        header.setLeft(sort_options);

        createScroll(search_field.getText(), true, 0);


        wrapper1.getChildren().addAll(clientsScroll);

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

                createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());

                controller.updateClient(currentClient);
            }

            else {
                firstname.setDisable(false);
                name.setDisable(false);
                email.setDisable(false);
                phone.setDisable(false);
                editButton.setText("Sauvegarder");
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

        Button removeButton = new Button("Supprimer");
        removeButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        removeButton.getStyleClass().add("record-sales");
        removeButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        removeButton.setOnAction(actionEvent -> {
            controller.eraseClient(currentClient);

            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        Button reducButton = new Button("Réductions...");
        reducButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        reducButton.getStyleClass().add("record-sales");
        reducButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        reducButton.setOnAction(actionEvent -> {
            Optional<Map<String, Integer>> permissions_result = new ReductionsDialog().showAndWait();
            //TODO : stocker la reduction qqpart
        });

        buttonsWrap2.getChildren().addAll(removeButton, reducButton);

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

    private void createElement(Client c, int i){
        HBox clientsBox = new HBox();

        clientsBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int j = 0; j < clients.getChildren().size(); j++) {
                    if (j % 2 == 1)
                        clients.getChildren().get(j).setStyle("-fx-background-color: #336699;");
                    else
                        clients.getChildren().get(j).setStyle("-fx-background-color: #0F355C;");
                }

                currentClient = c;
                updateDetail(c);
                clientsBox.setStyle("-fx-background-color: #ff6600;");
            }
        });

        clientsBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        clientsBox.setPadding(new Insets(20));

        Text clientText = new Text(c.getFirstname() + " " + c.getLastname());
        clientText.setFont(new Font(20));
        clientText.setFill(Color.WHITE);
        clientsBox.getChildren().add(clientText);

        if (i % 2 == 1)
            clientsBox.setStyle("-fx-background-color: #336699;");
        else
            clientsBox.setStyle("-fx-background-color: #0F355C;");

        clients.getChildren().add(clientsBox);
    }

    private void createScroll(String search, boolean refresh, int sort_options){
        clients.getChildren().clear();

        int i = 0;
        if(refresh)
            controller.createClients();

        controller.sortClients(sort_options);

        for (Client client : controller.getClients()) {
            if (client.getFirstname().toLowerCase().contains(search) || client.getLastname().toLowerCase().contains(search)) {
                createElement(client, i);
                i++;
            }
        }
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
