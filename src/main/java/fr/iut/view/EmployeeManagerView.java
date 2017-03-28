package fr.iut.view;

import fr.iut.controller.ConnectionController;
import fr.iut.controller.EmployeesController;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Problem;
import fr.iut.persistence.entities.Product;
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
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
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

    public EmployeeManagerView(EmployeesController c) {
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        this.controller = c;
        controller.createEmployees();

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");


        HeaderView headerView = new HeaderView("Gestion des employees");
        root.setTop(headerView);

        VBox wrapper1 = new VBox();
        wrapper1.setSpacing(10);
        BorderPane.setMargin(wrapper1, new Insets(20));
        employees = new VBox();
        employees.setSpacing(3);
        ScrollPane employeesScroll = new ScrollPane(employees);
        employeesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        employeesScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);
        employeesScroll.setMinWidth(HomeView.TAB_CONTENT_W/4);



        HBox search_bar = new HBox();
        Label search_label = new Label("Rechercher: ");
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        TextField search_field = new TextField();
        search_field.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
        search_field.setPromptText("Nom de l'employée");

        search_label.setLabelFor(search_field);
        search_bar.setAlignment(Pos.CENTER);
        search_bar.getChildren().addAll(search_label, search_field);
        headerView.setRight(search_bar);

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

        createScroll(search_field.getText(), true, sort_by.getSelectionModel().getSelectedIndex());

        Button newEmployee = new Button("+");
        newEmployee.setTooltip(new Tooltip("Nouvel employé"));
        newEmployee.getStylesheets().add(new File("res/style.css").toURI().toString());
        newEmployee.getStyleClass().add("record-sales");
        newEmployee.setOnAction((ActionEvent event) -> {
            InputsListDialog newEmployeeDialog = new InputsListDialog("Nouvel employé");
            newEmployeeDialog.addTextField("Nom");
            newEmployeeDialog.addTextField("Prénom");
            newEmployeeDialog.addTextField("Téléphone");
            newEmployeeDialog.addTextField("Mail");
            newEmployeeDialog.addTextField("Identifiant");
            newEmployeeDialog.addPasswordField("Mot de passe");
            newEmployeeDialog.addPasswordField("Retapez le mot de passe");
            Optional<Map<String, String>> newEmployee_result = newEmployeeDialog.showAndWait();

            Employee employee = new Employee();
            employee.setFirstName(newEmployee_result.get().get("Prénom"));
            employee.setLastName(newEmployee_result.get().get("Nom"));
            employee.setPhone(newEmployee_result.get().get("Téléphone"));
            employee.setEmail(newEmployee_result.get().get("Mail"));
            employee.setLogin(newEmployee_result.get().get("Identifiant"));
            if (Objects.equals(newEmployee_result.get().get("Identifiant"), newEmployee_result.get().get("Retapez le mot de passe")))
                employee.setPassword(ConnectionController.hash(newEmployee_result.get().get("Mot de passe")));

            controller.saveEmployee(employee);

            PermissionsDialog permissionsDialog = new PermissionsDialog();
            Optional<ArrayList<Boolean>> result = permissionsDialog.showAndWait();
            result.ifPresent(list -> {
                controller.updateAuthorizations(employee, list);
            });

            createScroll(search_field.getText(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        sort_options.getChildren().addAll(newEmployee, sort_by_label, sort_by);

        headerView.setLeft(sort_options);

        wrapper1.getChildren().addAll(employeesScroll);

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

        buttonsWrap1.setSpacing(10);
        buttonsWrap2.setSpacing(10);
        wrapWrappers.setSpacing(10);
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

                currentEmployee.setFirstName(firstname.getText());
                currentEmployee.setLastName(name.getText());
                currentEmployee.setEmail(email.getText());
                currentEmployee.setPhone(phone.getText());
                currentEmployee.setLogin(login.getText());
                if (Objects.equals(password.getText(), confirm.getText()))
                    currentEmployee.setPassword(password.getText());
                controller.updateEmployee(currentEmployee);

                createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());

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

            editMode = !editMode;
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        deleteButton.getStyleClass().add("record-sales");
        deleteButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        deleteButton.setOnAction(actionEvent -> {
            controller.eraseEmployee(currentEmployee);
            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        Button authorizationButton = new Button("Permissions...");
        authorizationButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        authorizationButton.getStyleClass().add("record-sales");
        authorizationButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        authorizationButton.setOnAction(actionEvent -> {
            PermissionsDialog permissionsDialog = new PermissionsDialog();
            permissionsDialog.checkPermissions(currentEmployee);
            Optional<ArrayList<Boolean>> result = permissionsDialog.showAndWait();
            result.ifPresent(list -> {
                controller.updateAuthorizations(currentEmployee, list);
            });
        });

        buttonsWrap1.getChildren().addAll(editButton, deleteButton);
        buttonsWrap2.getChildren().add(authorizationButton);

        detailsWrapper.getChildren().addAll(namesField, email, phone, login, passField);
        detailsWrapper.setSpacing(HomeView.TAB_CONTENT_H / 20);

        BorderPane.setMargin(detailsWrapper, new Insets(30));
        employeeDetailsWrapper.setPadding(new Insets(0, 0, 0, 0));
        employeeDetailsWrapper.setTop(headerDetails);
        employeeDetailsWrapper.setBottom(wrapWrappers);
        employeeDetailsWrapper.setCenter(detailsWrapper);
        BorderPane.setAlignment(wrapWrappers, Pos.CENTER);
        root.setCenter(employeeDetailsWrapper);
    }
    private void createElement(Employee e, int i){
        HBox employeesBox = new HBox();

        employeesBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int j=0; j<employees.getChildren().size(); j++) {
                    if(j % 2 == 1)
                        employees.getChildren().get(j).setStyle("-fx-background-color: #336699;");
                    else
                        employees.getChildren().get(j).setStyle("-fx-background-color: #0F355C;");
                }

                currentEmployee = e;
                updateDetail(e);
                employeesBox.setStyle("-fx-background-color: #ff6600;");
            }
        });
        employeesBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        employeesBox.setPadding(new Insets(20));

        Text employeeText = new Text(e.getFirstName() + " " + e.getLastName());
        employeeText.setFont(new Font(20));
        employeeText.setFill(Color.WHITE);
        employeesBox.getChildren().add(employeeText);

        if (i % 2 == 1)
            employeesBox.setStyle("-fx-background-color: #336699;");
        else
            employeesBox.setStyle("-fx-background-color: #0F355C;");

        employees.getChildren().add(employeesBox);
    }

    private void createScroll(String search, boolean refresh, int sort_options){
        employees.getChildren().clear();

        int i = 0;
        if(refresh)
            controller.createEmployees();

        controller.sortEmployees(sort_options);

        for (Employee employee : controller.getEmployees()) {
            if (employee.getFirstName().toLowerCase().contains(search) || employee.getLastName().toLowerCase().contains(search)) {
                createElement(employee, i);
                i++;
            }
        }
    }

    private void updateDetail(Employee employee) {
        firstname.setText(employee.getFirstName());
        name.setText(employee.getLastName());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());
        login.setText(employee.getLogin());

        currentEmployee = employee;
        editMode = false;
    }
}
