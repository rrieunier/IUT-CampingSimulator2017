package fr.iut.view;

import fr.iut.controller.IncidentsController;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Problem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.util.StringConverter;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * Created by theo on 01/03/17.
 */
public class IncidentsManagerView extends SubScene{

    /**
     * controller of the instance
     */
    private IncidentsController controller;

    /**
     * Differents textfields which are in details
     */
    private TextField description, appearanceDatetime, solutionDatetime;

    /**
     * boolean if the client can modif the incident
     */
    private Boolean editMode = false;

    /**
     * list of incidents
     */
    private VBox incidents;

    /**
     * if is selected, display only problems which are not resolved
     */
    private RadioButton resolved;

    /**
     * local value of the product selected
     */
    private Problem lastClickedValue;

    public IncidentsManagerView(IncidentsController c){
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        controller = c;
        controller.createIncidents();

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des incidents");

        HBox search_bar = new HBox();
        Label search_label = new Label("Rechercher: ");
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        TextField search_field = new TextField();
        search_field.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
        search_field.setPromptText("Nom de l'incident");

        search_label.setLabelFor(search_field);
        search_bar.setAlignment(Pos.CENTER);
        search_bar.getChildren().addAll(search_label, search_field);
        header.setRight(search_bar);

        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        BorderPane.setMargin(wrapper, new Insets(20));

        incidents = new VBox();
        incidents.setSpacing(3);
        ScrollPane incidentsScroll = new ScrollPane(incidents);
        incidentsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        incidentsScroll.setMaxWidth(HomeView.TAB_CONTENT_W/4);
        incidentsScroll.setMinWidth(HomeView.TAB_CONTENT_W/4);


        HBox sort_options = new HBox();
        sort_options.setSpacing(10);
        sort_options.setAlignment(Pos.CENTER);
        ObservableList<String> options =
                FXCollections.observableArrayList("Nom (alphabétique)", "Nom (alphabétique inverse)", "Date d'apparition (récent)",
                        "Date d'apparition (ancien)", "Date de résolution (récent)", "Date de résolution (ancien");
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

        Button newIncident = new Button("+");
        newIncident.setTooltip(new Tooltip("Ajouter un nouvel incident..."));
        newIncident.getStylesheets().add(new File("res/style.css").toURI().toString());
        newIncident.getStyleClass().add("record-sales");
        newIncident.setOnAction(event -> {
            InputsListDialog newIncidentDialog = new InputsListDialog("Nouvel incident");
            newIncidentDialog.addTextField("Description");

            ComboBox<Client> clientsComboBox = null;
            ComboBox<Location> locationComboBox = null;

            Text clientText = new Text("Client potentiel : ");
            clientText.setFont(new Font(20));
            clientText.setFill(Color.WHITE);

            ObservableList<Client> clients = FXCollections.observableArrayList();
            clients.addAll(controller.getClients());

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

            HBox hBoxClient = new HBox();
            hBoxClient.setSpacing(5);
            hBoxClient.getChildren().addAll(clientText, clientsComboBox);

            newIncidentDialog.getWrapper().getChildren().addAll(hBoxClient);

            Text locationText = new Text("Emplacement potentiel : ");
            locationText.setFont(new Font(20));
            locationText.setFill(Color.WHITE);

            ObservableList<Location> locations = FXCollections.observableArrayList();
            locations.addAll(controller.getLocations());

            locationComboBox = new ComboBox<>(locations);

            locationComboBox.setConverter(new StringConverter<Location>() {
                @Override
                public String toString(Location location) {
                    return location.getName();
                }

                @Override
                public Location fromString(String s) {
                    return null;
                }
            });

            HBox hBoxSpot = new HBox();
            hBoxSpot.setSpacing(5);
            hBoxSpot.getChildren().addAll(locationText, locationComboBox);

            newIncidentDialog.getWrapper().getChildren().addAll(hBoxSpot);


            Optional<Map<String, String>> newIncident_result = newIncidentDialog.showAndWait();

            Problem problem = new Problem();
            problem.setDescription(newIncident_result.get().get("Description"));
            HashSet<Client> clientsSet = new HashSet<>();
            clientsSet.add(clientsComboBox.getValue());
            HashSet<Location> locationsSet = new HashSet<>();
            locationsSet.add(locationComboBox.getValue());
            problem.setClients(clientsSet);
            problem.setLocations(locationsSet);

            controller.saveIncident(problem);

            // TODO : a verifier que les clients / locations fonctionnent...

            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        HBox resolvedBox = new HBox();
        resolvedBox.setSpacing(1);
        resolvedBox.setAlignment(Pos.CENTER);
        Label resolved_label = new Label("Non résolu uniquement");
        resolved = new RadioButton();
        resolved.setSelected(true);
        resolved.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createScroll(search_field.getText().toString(), false, sort_by.getSelectionModel().getSelectedIndex());
            }
        });
        resolved_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        resolved_label.setLabelFor(resolved);
        resolvedBox.getChildren().addAll(resolved, resolved_label);

        sort_options.getChildren().addAll(newIncident, sort_by_label, sort_by, resolvedBox);

        header.setLeft(sort_options);
        root.setTop(header);

        createScroll(search_field.getText().toString(), true, 0);

        wrapper.getChildren().add(incidentsScroll);

        root.setLeft(wrapper);

        BorderPane incidentDetailsWrapper = new BorderPane();
        BorderPane.setMargin(incidentDetailsWrapper, new Insets(30));
        incidentDetailsWrapper.setStyle("-fx-background-color: grey;");
        HeaderView headerDetails = new HeaderView("Details");

        VBox detailsField = new VBox();

        description = new TextField();
        description.setPromptText("Description");
        description.setDisable(true);
        description.setMinHeight(HomeView.TAB_CONTENT_H/15);

        appearanceDatetime = new TextField();
        appearanceDatetime.setPromptText("Date d'apparition ...");
        appearanceDatetime.setDisable(true);
        appearanceDatetime.setMinHeight(HomeView.TAB_CONTENT_H/15);

        solutionDatetime = new TextField();
        solutionDatetime.setPromptText("Date de résolution ...");
        solutionDatetime.setDisable(true);
        solutionDatetime.setMinHeight(HomeView.TAB_CONTENT_H/15);

        detailsField.setSpacing(10);
        detailsField.setAlignment(Pos.CENTER);
        detailsField.getChildren().addAll(description, appearanceDatetime, solutionDatetime);
        VBox.setVgrow(description, Priority.ALWAYS);
        VBox.setVgrow(appearanceDatetime, Priority.ALWAYS);
        VBox.setVgrow(solutionDatetime, Priority.ALWAYS);

        HBox buttonsWrap = new HBox();
        buttonsWrap.setAlignment(Pos.CENTER);

        Button editButton = new Button("Modifier");
        editButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        editButton.getStyleClass().add("record-sales");
        editButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        editButton.setOnAction(actionEvent -> {
            final Problem lastClikedCopy = lastClickedValue;
            if(editMode) {
                description.setDisable(true);
                appearanceDatetime.setDisable(true);
                solutionDatetime.setDisable(true);
                editButton.setText("Modifier");

                if(lastClikedCopy.isSolved()){
                    controller.updateIncident(lastClikedCopy, description.getText(),
                            appearanceDatetime.getText(), solutionDatetime.getText());
                }
                else{
                    controller.updateIncident(lastClikedCopy, description.getText(),
                            appearanceDatetime.getText(), null);
                }

                createScroll(search_field.getText(), true, sort_by.getSelectionModel().getSelectedIndex());
            }

            else {
                description.setDisable(false);
                appearanceDatetime.setDisable(false);
                if(lastClikedCopy.isSolved())
                    solutionDatetime.setDisable(false);
                editButton.setText("Sauvegarder");
            }

            editMode = !editMode;
        });

        Button resolvedButton = new Button("Incident résolu");
        resolvedButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        resolvedButton.getStyleClass().add("record-sales");
        resolvedButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        resolvedButton.setOnAction(actionEvent -> {
            final Problem lastClikedCopy = lastClickedValue;
            controller.resolveIncident(lastClikedCopy);
            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        buttonsWrap.setSpacing(10);
        buttonsWrap.getChildren().addAll(editButton, resolvedButton);

        incidentDetailsWrapper.setPadding(new Insets(0,0,30,0));
        incidentDetailsWrapper.setTop(headerDetails);
        incidentDetailsWrapper.setCenter(detailsField);
        incidentDetailsWrapper.setBottom(buttonsWrap);
        BorderPane.setMargin(detailsField, new Insets(20));

        root.setCenter(incidentDetailsWrapper);
    }

    private void updateDetail(Problem problem){
        description.setText(problem.getDescription());
        appearanceDatetime.setText(problem.getAppearanceDatetime().toString());
        if(problem.isSolved())
            solutionDatetime.setText(problem.getSolutionDatetime().toString());
        else
            solutionDatetime.setText("Date de résolution ...");
    }

    private void createElement(Problem p, int i){
        HBox incidentsBox = new HBox();

        incidentsBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int j=0; j<incidents.getChildren().size(); j++) {
                    if(j % 2 == 1)
                        incidents.getChildren().get(j).setStyle("-fx-background-color: #336699;");
                    else
                        incidents.getChildren().get(j).setStyle("-fx-background-color: #0F355C;");
                }

                lastClickedValue = p;
                updateDetail(p);
                incidentsBox.setStyle("-fx-background-color: #ff6600;");
            }
        });
        incidentsBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        incidentsBox.setPadding(new Insets(20));

        Text incidentText = new Text(p.getDescription());
        incidentText.setFont(new Font(20));
        incidentText.setFill(Color.WHITE);
        incidentsBox.getChildren().add(incidentText);

        if (i % 2 == 1)
            incidentsBox.setStyle("-fx-background-color: #336699;");
        else
            incidentsBox.setStyle("-fx-background-color: #0F355C;");

        incidents.getChildren().add(incidentsBox);
    }

    private void createScroll(String search, boolean refresh, int sort_options){
        incidents.getChildren().clear();

        int i = 0;
        if(refresh)
            controller.createIncidents();

        controller.sortIncidents(sort_options);

        for (Problem problem : controller.getIncidents()) {
            if (problem.getDescription().toLowerCase().contains(search)) {
                if(resolved.isSelected()){
                    if(!problem.isSolved()){
                        createElement(problem, i);
                        i++;
                    }
                }
                else{
                    createElement(problem, i);
                    i++;
                }
            }
        }
    }
}
