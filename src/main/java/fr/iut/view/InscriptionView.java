package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.InscriptionController;
import fr.iut.persistence.entities.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;


/**
 * Created by theo on 13/03/17.
 */
public class InscriptionView extends Scene {

    public static final double INSCRIPTION_WIDTH = App.SCREEN_W / 2;
    public static final double INSCRIPTION_HEIGHT = App.SCREEN_H / 1.5;

    private InscriptionController controller;

    public InscriptionView(InscriptionController controller){
        super(new BorderPane(), INSCRIPTION_WIDTH, INSCRIPTION_HEIGHT);
        this.controller = controller;
        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Première connexion");
        root.setTop(header);

        VBox employeeFields = new VBox();
        employeeFields.setSpacing(10);
        employeeFields.setAlignment(Pos.CENTER);

        TextField login = new TextField();
        login.setPromptText("Nom d'utilisateur");
        login.setMaxWidth(INSCRIPTION_WIDTH/2);
        login.setMinHeight(HomeView.TAB_CONTENT_H/15);

        PasswordField password = new PasswordField();
        password.setPromptText("Mot de passe");
        password.setMaxWidth(INSCRIPTION_WIDTH/2);
        password.setMinHeight(HomeView.TAB_CONTENT_H/15);

        TextField firstName = new TextField();
        firstName.setPromptText("Prénom");
        firstName.setMaxWidth(INSCRIPTION_WIDTH/2);
        firstName.setMinHeight(HomeView.TAB_CONTENT_H/15);

        TextField lastName = new TextField();
        lastName.setPromptText("Nom");
        lastName.setMaxWidth(INSCRIPTION_WIDTH/2);
        lastName.setMinHeight(HomeView.TAB_CONTENT_H/15);

        TextField phone = new TextField();
        phone.setPromptText("Numéro de téléphone (*)");
        phone.setMaxWidth(INSCRIPTION_WIDTH/2);
        phone.setMinHeight(HomeView.TAB_CONTENT_H/15);

        TextField address = new TextField();
        address.setPromptText("Adresse (*)");
        address.setMaxWidth(INSCRIPTION_WIDTH/2);
        address.setMinHeight(HomeView.TAB_CONTENT_H/15);

        TextField mail = new TextField();
        mail.setPromptText("Mail (*)");
        mail.setMaxWidth(INSCRIPTION_WIDTH/2);
        mail.setMinHeight(HomeView.TAB_CONTENT_H/15);

        Text text = new Text("(*) Les champs sont facultatifs.");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-weight: bold;" +
                "-fx-font-size: 17px");


        employeeFields.getChildren().addAll(login, password, firstName, lastName, phone, address, mail, text);
        root.setCenter(employeeFields);


        Button okButton = new Button("Valider");
        okButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        okButton.getStyleClass().add("record-sales");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(login.getText().toString() != "" && password.getText().toString() != "" && firstName.getText().toString() != "" && lastName.getText().toString() != "") {
                    Employee employee = new Employee();
                    employee.setLogin(login.getText().toString());
                    employee.setPassword(password.getText().toString());
                    employee.setFirstName(firstName.getText().toString());
                    employee.setLastName(lastName.getText().toString());
                    employee.setPhone(phone.getText().toString());
                    employee.setCompleteAddress(address.getText().toString());
                    employee.setEmail(mail.getText().toString());
                    controller.finish(employee);
                }
            }
        });
        BorderPane.setMargin(okButton, new Insets(0,0, 20, (INSCRIPTION_WIDTH/2) - 50));
        root.setBottom(okButton);
    }
}
