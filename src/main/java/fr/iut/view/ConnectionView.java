package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class ConnectionView extends Scene {

    public static final double LOGIN_WIDTH = App.SCREEN_W / 2.5;
    public static final double LOGIN_HEIGHT = App.SCREEN_H / 2.5;
    private static final Color LOGIN_BACKGROUNG = Color.rgb(12, 27, 51);

    App app;

    protected Group components;

    protected VBox wrapper = new VBox();

    protected StackPane header = new StackPane();
    protected Rectangle title_back = new Rectangle(); // rectangle de fond du titre
    protected Text title = new Text("Connexion");

    protected VBox fields = new VBox();
    protected TextField login_field = new TextField();
    protected PasswordField password_field = new PasswordField();
    protected TextField password_shown = new TextField();

    protected HBox radio_buttons = new HBox();
    protected RadioButton remember = new RadioButton("Se souvenir de moi");
    protected RadioButton show_pass = new RadioButton("Afficher le mot de passe");

    protected Button confirm = new Button("Valider");

    public ConnectionView(App app) {
        super(new Group(), LOGIN_WIDTH, LOGIN_HEIGHT);
        components = (Group) getRoot();
        this.app = app;
        setFill(LOGIN_BACKGROUNG);

        title_back.setWidth(LOGIN_WIDTH);
        title_back.setHeight(LOGIN_HEIGHT / 6);
        title_back.setFill(Color.rgb(55, 77, 114));
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title_back, title);

        login_field.setMaxWidth(LOGIN_WIDTH / 1.1);
        login_field.setMinHeight(LOGIN_HEIGHT / 8);
        login_field.setPromptText("Identifiant");
        login_field.setStyle("-fx-font-weight: bold;");

        password_field.setMaxWidth(LOGIN_WIDTH / 1.1);
        password_field.setMinHeight(LOGIN_HEIGHT / 8);
        password_field.setPromptText("Mot de passe");
        password_field.setStyle("-fx-font-weight: bold;");
        password_shown.setMinWidth(LOGIN_WIDTH / 1.1);
        password_shown.setMinHeight(LOGIN_HEIGHT / 8);
        password_shown.setPromptText("Mot de passe");
        password_shown.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");
        password_shown.setLayoutX(LOGIN_WIDTH / 21.7);
        password_shown.setLayoutY(LOGIN_HEIGHT / 2.26);
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

        confirm.setMinSize(LOGIN_WIDTH / 8, LOGIN_HEIGHT / 10);
        confirm.setLayoutX(LOGIN_WIDTH / 1.2);
        confirm.setLayoutY(LOGIN_HEIGHT / 1.2);
        confirm.setText("Valider");
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                connectionButtonAction(login_field.getText(), password_field.getText());
            }
        });

        wrapper.setSpacing(LOGIN_HEIGHT / 10);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().addAll(header, fields, radio_buttons);

        components.getChildren().addAll(wrapper, confirm, password_shown);
    }

    private void connectionButtonAction(String username, String password) {
        boolean connected = app.tryLogin(username, password);

        if (connected)
            app.GO_MAMENE_GOOOOOOOOOO();

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
