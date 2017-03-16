package fr.iut.view;

import fr.iut.controller.IncidentsController;
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

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public IncidentsManagerView(IncidentsController controller){
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = controller;
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
        search_field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    createScroll(search_field.getText().toString());
                    search_field.clear();
                }
            }
        });
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
                controller.sortIncidents(sort_by.getSelectionModel().getSelectedIndex());
                createScroll(search_field.getText().toString());
            }
        });
        Label sort_by_label = new Label("Tri par: ");
        sort_by_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        sort_by_label.setLabelFor(sort_by);


        Button newIncident = new Button("+");
        newIncident.setTooltip(new Tooltip("Ajouter un nouvel incident..."));
        newIncident.getStylesheets().add(new File("res/style.css").toURI().toString());
        newIncident.getStyleClass().add("record-sales");
        newIncident.setOnAction(event -> {
            InputsListDialog newIncidentDialog = new InputsListDialog("Nouvel incident");
            newIncidentDialog.addTextField("Description");
            Optional<Map<String, String>> newIncident_result = newIncidentDialog.showAndWait();

            Problem problem = new Problem();
            problem.setDescription(newIncident_result.get().get("Description"));

            controller.saveIncident(problem);
            createScroll(search_field.getText().toString());
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
                createScroll(search_field.getText().toString());
            }
        });
        resolved_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        resolved_label.setLabelFor(resolved);
        resolvedBox.getChildren().addAll(resolved, resolved_label);

        sort_options.getChildren().addAll(newIncident, sort_by_label, sort_by, resolvedBox);

        header.setLeft(sort_options);
        root.setTop(header);

        createScroll(search_field.getText().toString());

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

                controller.updateIncident(lastClikedCopy, description.getText().toString(),
                        appearanceDatetime.getText().toString(), solutionDatetime.getText().toString());
                createScroll(search_field.getText().toString());
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
            createScroll(search_field.getText().toString());
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

    private void createScroll(String search){
        incidents.getChildren().clear();

        int i = 0;
        controller.createIncidents();
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
