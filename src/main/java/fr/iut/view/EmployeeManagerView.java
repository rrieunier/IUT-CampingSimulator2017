package fr.iut.view;

import fr.iut.controller.EmployeesController;
import fr.iut.persistence.entities.Employee;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
public class EmployeeManagerView extends SubScene {

    private final VBox employees;
    EmployeesController controller;

    boolean editMode = false;

    TextField firstname, name, email, phone, login, password, confirm;

    Employee currentEmployee = null;

    public EmployeeManagerView(EmployeesController controller) {
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = controller;

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des employees");
        root.setTop(header);

        VBox wrapper1 = new VBox();
        wrapper1.setSpacing(10);
        BorderPane.setMargin(wrapper1, new Insets(20));
        employees = new VBox();
        employees.setSpacing(3);
        ScrollPane employeesScroll = new ScrollPane(employees);
        employeesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        employeesScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);

        reloadList();

        Button newEmployee = new Button("Nouvel employé");
        newEmployee.getStylesheets().add(new File("res/style.css").toURI().toString());
        newEmployee.getStyleClass().add("record-sales");
        newEmployee.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        newEmployee.setOnAction(event -> {
            InputsListDialog newEmployeeDialog = new InputsListDialog("Nouvel employé");
            newEmployeeDialog.addTextField("Nom");
            newEmployeeDialog.addTextField("Prénom");
            newEmployeeDialog.addTextField("Téléphone");
            newEmployeeDialog.addTextField("Mail");
            newEmployeeDialog.addTextField("Identifiant");
            newEmployeeDialog.addTextField("Mot de passe");
            newEmployeeDialog.addTextField("Retapez le mot de passe");
            Optional<Map<String, String>> newEmployee_result = newEmployeeDialog.showAndWait();

            Employee employee = new Employee();
            employee.setFirstName(newEmployee_result.get().get("Prénom"));
            employee.setLastName(newEmployee_result.get().get("Nom"));
            employee.setPhone(newEmployee_result.get().get("Téléphone"));
            employee.setEmail(newEmployee_result.get().get("Mail"));
            employee.setLogin(newEmployee_result.get().get("Identifiant"));
            if (newEmployee_result.get().get("Identifiant") == newEmployee_result.get().get("Retapez le mot de passe"))
                employee.setPassword(newEmployee_result.get().get("Mot de passe"));

            controller.saveEmployee(employee);
            reloadList();
        });


        wrapper1.getChildren().addAll(employeesScroll, newEmployee);

        root.setLeft(wrapper1);

        BorderPane employeeDetailsWrapper = new BorderPane();
        BorderPane.setMargin(employeeDetailsWrapper, new Insets(30));
        employeeDetailsWrapper.setStyle("-fx-background-color: grey;");
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

        login = new TextField();
        login.setPromptText("Identifiant...");
        login.setDisable(true);
        login.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        HBox passField = new HBox();
        password = new PasswordField();
        password.setPromptText("Mot de passe...");
        password.setDisable(true);
        password.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        confirm = new PasswordField();
        confirm.setPromptText("Retapez le mot de passe...");
        confirm.setDisable(true);
        confirm.setMinHeight(HomeView.TAB_CONTENT_H / 15);

        passField.setSpacing(10);
        passField.setAlignment(Pos.CENTER);
        passField.getChildren().addAll(password, confirm);
        HBox.setHgrow(password, Priority.ALWAYS);
        HBox.setHgrow(confirm, Priority.ALWAYS);

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
            if (editMode) {
                firstname.setDisable(true);
                name.setDisable(true);
                email.setDisable(true);
                phone.setDisable(true);
                login.setDisable(true);
                password.setDisable(true);
                confirm.setDisable(true);

                Employee employee = new Employee();
                employee.setFirstName(firstname.getText());
                employee.setLastName(name.getText());
                employee.setEmail(email.getText());
                employee.setPhone(phone.getText());
                employee.setLogin(login.getText());
                if (password.getText() == confirm.getText())
                    employee.setPassword(password.getText());
                controller.updateEmployee(employee);

                editButton.setText("Modifier");
            } else {
                firstname.setDisable(false);
                name.setDisable(false);
                email.setDisable(false);
                phone.setDisable(false);
                login.setDisable(false);
                password.setDisable(false);
                confirm.setDisable(false);
                editButton.setText("Sauvegarder");
            }

            reloadList();
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

        detailsWrapper.getChildren().addAll(namesField, email, phone, login, passField);
        detailsWrapper.setSpacing(HomeView.TAB_CONTENT_H / 15);

        BorderPane.setMargin(detailsWrapper, new Insets(30));
        employeeDetailsWrapper.setPadding(new Insets(0, 0, 30, 0));
        employeeDetailsWrapper.setTop(headerDetails);
        employeeDetailsWrapper.setBottom(wrapWrappers);
        employeeDetailsWrapper.setCenter(detailsWrapper);
        BorderPane.setAlignment(wrapWrappers, Pos.CENTER);
        root.setCenter(employeeDetailsWrapper);
    }

    private void reloadList() {
        employees.getChildren().removeAll();
        int i = 0;
        for (Employee employee : controller.getEmployees()) {
            HBox employeeBox = new HBox();
            employeeBox.setOnMouseClicked(mouseEvent -> updateDetail(employee));

            employeeBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
            employeeBox.setPadding(new Insets(20));

            Text employeeText = new Text(employee.getFirstName() + " " + employee.getLastName());
            employeeText.setFont(new Font(16));
            employeeText.setFill(Color.WHITE);
            employeeBox.getChildren().add(employeeText);

            if (i++ % 2 == 1)
                employeeBox.setStyle("-fx-background-color: #336699;");
            else
                employeeBox.setStyle("-fx-background-color: #0F355C;");

            employees.getChildren().add(employeeBox);
        }
    }

    private void updateDetail(Employee employee) {
        firstname.setText(employee.getFirstName());
        name.setText(employee.getLastName());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());
        login.setText(employee.getLogin());
        password.setText(employee.getPassword());

        currentEmployee = employee;
        editMode = false;
    }
}
