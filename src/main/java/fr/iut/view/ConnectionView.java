package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.ConnectionController;
import fr.iut.controller.ReservationController;
import fr.iut.persistence.entities.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;


public class ConnectionView extends Scene {

    public static final double LOGIN_WIDTH = App.SCREEN_W / 2;
    public static final double LOGIN_HEIGHT = App.SCREEN_H / 2;
    private static final Color LOGIN_BACKGROUNG = Color.rgb(12, 27, 51);

    private ConnectionController controller;

    private Group components;

    private TextField login_field = new TextField();
    private PasswordField password_field = new PasswordField();

    private RadioButton remember = new RadioButton("Se souvenir de moi");
    private RadioButton show_pass = new RadioButton("Afficher le mot de passe");

    public ConnectionView(ConnectionController app) {
        super(new Group(), LOGIN_WIDTH, LOGIN_HEIGHT);
        components = (Group) getRoot();
        this.controller = app;

        setFill(LOGIN_BACKGROUNG);

        VBox wrapper = new VBox();

        HeaderView header = new HeaderView("Connexion");

        VBox fields = new VBox();
        TextField password_shown = new TextField(); // affiché quand "afficher le mdp" est coché

        HBox radio_buttons = new HBox();

        Button confirm = new Button("Valider");


        login_field.setMaxWidth(LOGIN_WIDTH / 1.1);
        login_field.setMinHeight(LOGIN_HEIGHT / 8);
        login_field.setPromptText("Identifiant");
        login_field.setStyle("-fx-font-weight: bold;" +
                            "-fx-font-size: 17px;");

        if (Files.exists(Paths.get("login"))) { // si "remember me" était coché à la dernière connection
            remember.setSelected(true);
            try {
                char username[] = new String(Files.readAllBytes(Paths.get("login"))).toCharArray();
                for (int i = 0; i < username.length; i++) {
                    username[i] = (char) (username[i] ^ 42);
                }
                login_field.setText(new String(username));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        password_field.setMaxWidth(LOGIN_WIDTH / 1.1);
        password_field.setMinHeight(LOGIN_HEIGHT / 8);
        password_field.setPromptText("Mot de passe");
        password_field.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px;");
        password_shown.setMinWidth(LOGIN_WIDTH / 1.1);
        password_shown.setMinHeight(LOGIN_HEIGHT / 8);
        password_shown.setPromptText("Mot de passe");
        password_shown.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        password_shown.setLayoutX(LOGIN_WIDTH / 21.7);
        password_shown.setLayoutY(LOGIN_HEIGHT / 2.41);
        password_shown.setVisible(false);
        password_field.textProperty().bindBidirectional(password_shown.textProperty());

        fields.getChildren().addAll(login_field, password_field);
        fields.setSpacing(LOGIN_HEIGHT / 20);
        fields.setAlignment(Pos.CENTER);

        remember.setStyle("-fx-text-fill: whitesmoke;");
        show_pass.setStyle("-fx-text-fill: whitesmoke;");
        show_pass.setOnMouseClicked(event -> {
            if (show_pass.isSelected()) {
                password_field.setVisible(false);
                password_shown.setVisible(true);
            } else {
                password_field.setVisible(true);
                password_shown.setVisible(false);
            }
        });
        radio_buttons.setSpacing(LOGIN_WIDTH / 6);
        radio_buttons.setAlignment(Pos.CENTER);
        radio_buttons.getChildren().addAll(remember, show_pass);

        confirm.setMinSize(LOGIN_WIDTH / 6, LOGIN_HEIGHT / 10);
        confirm.setLayoutX((LOGIN_WIDTH - confirm.getMinWidth()) / 2);
        confirm.setLayoutY(LOGIN_HEIGHT / 1.2);
        confirm.setText("Valider");
        confirm.setOnMouseClicked(event -> connectionButtonAction(login_field.getText(), password_field.getText()));
        confirm.getStylesheets().add(new File("res/style.css").toURI().toString());
        confirm.getStyleClass().add("record-sales");
        confirm.setOnMouseClicked(event -> connectionButtonAction(login_field.getText(), password_field.getText()));

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                connectionButtonAction(login_field.getText(), password_field.getText());
            }
        });

        wrapper.setSpacing(LOGIN_HEIGHT / 10);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPrefWidth(LOGIN_WIDTH);
        wrapper.getChildren().addAll(header, fields, radio_buttons);

        components.getChildren().addAll(wrapper, confirm, password_shown);
    }

    private void connectionButtonAction(String username, String password) {

        boolean connected = controller.tryLogin(username, password);

        if (connected) {
            if (remember.isSelected()) {
                try {
                    PrintWriter writer = new PrintWriter("login", "UTF-8");
                    for (char l : username.toCharArray()) {
                        writer.print((char)(l ^ 42));
                    }
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Can't write to \'login\' file.");
                }
            }

            else {
                if (Files.exists(Paths.get("login"))) {
                    try {
                        Files.delete(Paths.get("login"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            controller.finish();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops!");
            alert.setHeaderText(null);
            alert.setContentText("Combinaison utilisateur / mot de passe inexistante.");

            alert.showAndWait();

            login_field.requestFocus();
            login_field.positionCaret(0);
            login_field.selectAll();
            password_field.clear();
        }
    }
}