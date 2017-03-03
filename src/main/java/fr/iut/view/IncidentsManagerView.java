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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

/**
 * Created by theo on 01/03/17.
 */
public class IncidentsManagerView extends SubScene{

    IncidentsController controller;

    TextField appearanceDatetime, solutionDatetime;

    Boolean editMode = false;

    VBox incidents;

    RadioButton resolved;

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
            // TODO
        });

        HBox resolvedBox = new HBox();
        resolvedBox.setSpacing(1);
        resolvedBox.setAlignment(Pos.CENTER);
        Label resolved_label = new Label("Non résolu uniquement");
        resolved = new RadioButton();
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

        VBox timesField = new VBox();
        appearanceDatetime = new TextField();
        appearanceDatetime.setPromptText("Date début ...");
        appearanceDatetime.setDisable(true);
        appearanceDatetime.setMinHeight(HomeView.TAB_CONTENT_H/15);

        solutionDatetime = new TextField();
        solutionDatetime.setPromptText("Date fin ...");
        solutionDatetime.setDisable(true);
        solutionDatetime.setMinHeight(HomeView.TAB_CONTENT_H/15);

        timesField.setSpacing(10);
        timesField.setAlignment(Pos.CENTER);
        timesField.getChildren().addAll(appearanceDatetime, solutionDatetime);
        VBox.setVgrow(appearanceDatetime, Priority.ALWAYS);
        VBox.setVgrow(solutionDatetime, Priority.ALWAYS);

        HBox buttonsWrap = new HBox();
        buttonsWrap.setAlignment(Pos.CENTER);

        Button editButton = new Button("Modifier");
        editButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        editButton.getStyleClass().add("record-sales");
        editButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);

        editButton.setOnAction(actionEvent -> {
            if(editMode) {
                appearanceDatetime.setDisable(true);
                solutionDatetime.setDisable(true);
                editButton.setText("Modifier");
            }

            else {
                appearanceDatetime.setDisable(false);
                solutionDatetime.setDisable(false);
                editButton.setText("Sauvegarder");
                //TODO : saveOrUpdate de l'incident dans la BDD
            }

            editMode = !editMode;
        });

        Button deleteButton = new Button("Incident résolu");
        deleteButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        deleteButton.getStyleClass().add("record-sales");
        deleteButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        deleteButton.setOnAction(actionEvent -> {
            //TODO : supprimer dans la BD ou pas telle est la question
        });

        buttonsWrap.setSpacing(10);
        buttonsWrap.getChildren().addAll(editButton, deleteButton);

        incidentDetailsWrapper.setPadding(new Insets(0,0,30,0));
        incidentDetailsWrapper.setTop(headerDetails);
        incidentDetailsWrapper.setCenter(timesField);
        incidentDetailsWrapper.setBottom(buttonsWrap);
        BorderPane.setMargin(timesField, new Insets(20));

        root.setCenter(incidentDetailsWrapper);
    }

    private void updateDetail(Problem problem){
        appearanceDatetime.setText(problem.getDescription());
        solutionDatetime.setText(problem.getDescription());
    }

    private void createElement(Problem p, int i){
        HBox incidentsBox = new HBox();
        incidentsBox.setOnMouseClicked(mouseEvent -> updateDetail(p));
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
        for (Problem problem : controller.getIncidents()) {
            if (problem.getDescription().toLowerCase().contains(search)) {
                if(resolved.isSelected()){
                    if(problem.getState().toString() == "Non résolu"){
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