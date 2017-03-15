package fr.iut.view;

import fr.iut.controller.EmployeesController;
import fr.iut.persistence.entities.Employee;
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
public class EmployeeManagerView extends SubScene {

    EmployeesController controller;

    boolean editMode = false;

    TextField firstname, name, email, phone;

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
        VBox employees = new VBox();
        employees.setSpacing(3);
        ScrollPane employeesScroll = new ScrollPane(employees);
        employeesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        employeesScroll.setMaxWidth(HomeView.TAB_CONTENT_W / 4);

        int i = 0;
        for(Employee employee : controller.getEmployees()) {
            HBox employeeBox = new HBox();
            employeeBox.setOnMouseClicked(mouseEvent -> updateDetail(employee));

            employeeBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
            employeeBox.setPadding(new Insets(20));

            Text employeeText = new Text(employee.getFirstName() + " " + employee.getLastName());
            employeeText.setFont(new Font(16));
            employeeText.setFill(Color.WHITE);
            employeeBox.getChildren().add(employeeText);

            if(i++ % 2 == 1)
                employeeBox.setStyle("-fx-background-color: #336699;");
            else
                employeeBox.setStyle("-fx-background-color: #0F355C;");

            employees.getChildren().add(employeeBox);
        }

        Button newEmployee = new Button("Nouveau Employee");
        newEmployee.getStylesheets().add(new File("res/style.css").toURI().toString());
        newEmployee.getStyleClass().add("record-sales");
        newEmployee.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        newEmployee.setOnAction(event -> {
            InputsListDialog newEmployeeDialog = new InputsListDialog("Nouveau Employee");
            newEmployeeDialog.addTextField("Nom");
            newEmployeeDialog.addTextField("Prénom");
            newEmployeeDialog.addTextField("Téléphone");
            newEmployeeDialog.addTextField("Mail");
            Optional<Map<String, String>> newEmployee_result = newEmployeeDialog.showAndWait();

            Employee employee = new Employee();
            employee.setFirstName(newEmployee_result.get().get("Prénom"));
            employee.setLastName(newEmployee_result.get().get("Nom"));
            employee.setPhone(newEmployee_result.get().get("Téléphone"));
            employee.setEmail(newEmployee_result.get().get("Mail"));

            controller.saveEmployee(employee);
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
                //TODO : saveOrUpdate du employee dans la BDD
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
        employeeDetailsWrapper.setPadding(new Insets(0, 0, 30, 0));
        employeeDetailsWrapper.setTop(headerDetails);
        employeeDetailsWrapper.setBottom(wrapWrappers);
        employeeDetailsWrapper.setCenter(detailsWrapper);
        BorderPane.setAlignment(wrapWrappers, Pos.CENTER);
        root.setCenter(employeeDetailsWrapper);
    }

    private void updateDetail(Employee employee) {
        firstname.setText(employee.getFirstName());
        name.setText(employee.getLastName());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());

        currentEmployee = employee;
        editMode = false;
    }
}
