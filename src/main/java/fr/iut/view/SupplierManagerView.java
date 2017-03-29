package fr.iut.view;

import fr.iut.controller.SupplierController;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Product;
import fr.iut.persistence.entities.Supplier;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Created by theo on 16/03/17.
 */
public class SupplierManagerView extends SubScene{

    private SupplierController controller;

    private VBox suppliers;

    private Boolean editMode = false;

    private TextField name, phone, website, mail;

    private Supplier lastClickedValue;


    public SupplierManagerView(SupplierController c){
        super(new BorderPane(), HomeView.TAB_CONTENT_W, HomeView.TAB_CONTENT_H);
        controller = c;
        controller.createSuppliers();

        BorderPane root = (BorderPane) getRoot();
        root.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(5))));
        root.setStyle("-fx-background-color: rgb(12, 27, 51);");

        HeaderView header = new HeaderView("Gestion des fournisseurs");

        HBox search_bar = new HBox();
        Label search_label = new Label("Rechercher: ");
        search_label.setStyle("-fx-text-fill: whitesmoke; -fx-font-size: 18px");
        TextField search_field = new TextField();
        search_field.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
        search_field.setPromptText("Nom du fournisseur");

        search_label.setLabelFor(search_field);
        search_bar.setAlignment(Pos.CENTER);
        search_bar.getChildren().addAll(search_label, search_field);
        header.setRight(search_bar);

        VBox wrapper = new VBox();
        wrapper.setSpacing(10);
        BorderPane.setMargin(wrapper, new Insets(20));

        suppliers = new VBox();
        suppliers.setSpacing(3);
        ScrollPane suppliersScroll = new ScrollPane(suppliers);
        suppliersScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        suppliersScroll.setMaxWidth(HomeView.TAB_CONTENT_W/4);
        suppliersScroll.setMinWidth(HomeView.TAB_CONTENT_W/4);


        HBox sort_options = new HBox();
        sort_options.setSpacing(10);
        sort_options.setAlignment(Pos.CENTER);
        ObservableList<String> options =
                FXCollections.observableArrayList("Nom (alphabétique)", "Nom (alphabétique inverse)");
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

        Button newSupplier = new Button("+");
        newSupplier.setDisable(!EmployeeDAO.getConnectedUser().hasPermission(Authorization.SUPPLIER_UPDATE));
        newSupplier.setTooltip(new Tooltip("Ajouter un nouveau fournisseur..."));
        newSupplier.getStylesheets().add(new File("res/style.css").toURI().toString());
        newSupplier.getStyleClass().add("record-sales");
        newSupplier.setOnAction(event -> {
            InputsListDialog newSupplierDialog = new InputsListDialog("Nouveau Fournisseur");
            newSupplierDialog.addTextField("Nom");
            newSupplierDialog.addTextField("Numéro");
            newSupplierDialog.addTextField("Site");
            newSupplierDialog.addTextField("Adresse électronique");

            Optional<Map<String, String>> newSupplier_result = newSupplierDialog.showAndWait();

            Supplier supplier = new Supplier();
            supplier.setName(newSupplier_result.get().get("Nom"));
            supplier.setPhone(newSupplier_result.get().get("Numéro"));
            supplier.setEmail(newSupplier_result.get().get("Adresse électronique"));
            supplier.setWebsite(newSupplier_result.get().get("Site"));

            controller.saveSupplier(supplier);

            Optional<ArrayList<Pair<Product, Float>>> products_result = new ProductListDialog().showAndWait();
            products_result.ifPresent(list -> {
                controller.saveOrUpdateProductsSupplier(supplier, list, false);
            });

            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());

        });

        sort_options.getChildren().addAll(newSupplier, sort_by_label, sort_by);

        header.setLeft(sort_options);
        root.setTop(header);

        wrapper.getChildren().add(suppliersScroll);

        createScroll(search_field.getText().toString(), true, 0);

        root.setLeft(wrapper);

        BorderPane supplierDetailsWrapper = new BorderPane();
        BorderPane.setMargin(supplierDetailsWrapper, new Insets(30));
        supplierDetailsWrapper.setStyle("-fx-background-color: grey;");
        HeaderView headerDetails = new HeaderView("Details");

        VBox detailsField = new VBox();

        name = new TextField();
        name.setPromptText("Nom");
        name.setDisable(true);
        name.setMinHeight(HomeView.TAB_CONTENT_H/15);

        phone = new TextField();
        phone.setPromptText("Numéro");
        phone.setDisable(true);
        phone.setMinHeight(HomeView.TAB_CONTENT_H/15);

        website = new TextField();
        website.setPromptText("Site");
        website.setDisable(true);
        website.setMinHeight(HomeView.TAB_CONTENT_H/15);

        mail = new TextField();
        mail.setPromptText("Adresse électronique");
        mail.setDisable(true);
        mail.setMinHeight(HomeView.TAB_CONTENT_H/15);
        
        detailsField.setSpacing(10);
        detailsField.setAlignment(Pos.CENTER);
        detailsField.getChildren().addAll(name, phone, website, mail);
        VBox.setVgrow(name, Priority.ALWAYS);
        VBox.setVgrow(phone, Priority.ALWAYS);
        VBox.setVgrow(website, Priority.ALWAYS);
        VBox.setVgrow(mail, Priority.ALWAYS);


        VBox buttons = new VBox();
        HBox updateButtons = new HBox();
        HBox otherButtons = new HBox();
        otherButtons.setAlignment(Pos.CENTER);
        updateButtons.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);

        Button editButton = new Button("Modifier");
        editButton.setDisable(!EmployeeDAO.getConnectedUser().hasPermission(Authorization.SUPPLIER_UPDATE));
        editButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        editButton.getStyleClass().add("record-sales");
        editButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        editButton.setOnAction(event -> {
            if(lastClickedValue != null){
                if(editMode){
                    name.setDisable(true);
                    phone.setDisable(true);
                    website.setDisable(true);
                    mail.setDisable(true);
                    editButton.setText("Modifier");
                    controller.updateSupplier(lastClickedValue, name.getText().toString(), phone.getText().toString(),
                            website.getText().toString(), mail.getText().toString());
                    createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
                }
                else{
                    name.setDisable(false);
                    phone.setDisable(false);
                    website.setDisable(false);
                    mail.setDisable(false);
                    editButton.setText("Sauvegarder");

                }

                editMode = !editMode;
            }
        });

        Button editProductButton = new Button("Ajouter / Modifier produits");
        editProductButton.setDisable(!EmployeeDAO.getConnectedUser().hasPermission(Authorization.SUPPLIER_UPDATE));
        editProductButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        editProductButton.getStyleClass().add("record-sales");
        editProductButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        editProductButton.setOnAction(event -> {
            final Supplier lastClikedCopy = lastClickedValue;
            if(lastClickedValue != null){
                ProductListDialog productListDialog = new ProductListDialog();
                productListDialog.checkProducts(controller.getProductsProposeBySupplier(lastClikedCopy));
                Optional<ArrayList<Pair<Product, Float>>> result = productListDialog.showAndWait();
                result.ifPresent(list -> {
                    controller.cleanSupplierProposeProduct(lastClikedCopy);
                    controller.saveOrUpdateProductsSupplier(lastClikedCopy, list, true);
                });
            }
        });

        Button contactButton = new Button("Contacter");
        contactButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        contactButton.getStyleClass().add("record-sales");
        contactButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        contactButton.setOnAction(event -> {
            final Supplier lastClikedCopy = lastClickedValue;
            try {
                controller.sendMailToSupplier(lastClikedCopy);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button removeButton = new Button("Supprimer");
        removeButton.setDisable(!EmployeeDAO.getConnectedUser().hasPermission(Authorization.SUPPLIER_UPDATE));
        removeButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        removeButton.getStyleClass().add("record-sales");
        removeButton.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        removeButton.setOnAction(event -> {
            final Supplier lastClikedCopy = lastClickedValue;
            controller.removeSupplier(lastClikedCopy);
            createScroll(search_field.getText().toString(), true, sort_by.getSelectionModel().getSelectedIndex());
        });

        updateButtons.setSpacing(10);
        updateButtons.getChildren().addAll(editButton, editProductButton);

        otherButtons.setSpacing(10);
        otherButtons.getChildren().addAll(contactButton, removeButton);

        buttons.setSpacing(10);
        buttons.getChildren().addAll(updateButtons, otherButtons);

        supplierDetailsWrapper.setPadding(new Insets(0,0,30,0));
        supplierDetailsWrapper.setTop(headerDetails);
        supplierDetailsWrapper.setCenter(detailsField);
        supplierDetailsWrapper.setBottom(buttons);
        BorderPane.setMargin(detailsField, new Insets(20));

        root.setCenter(supplierDetailsWrapper);
    }
    
    private void updateDetail(Supplier supplier){
        name.setText(supplier.getName());
        phone.setText(supplier.getPhone());
        website.setText(supplier.getWebsite());
        mail.setText(supplier.getEmail());
    }

    private void createElement(Supplier s, int i){
        HBox suppliersBox = new HBox();

        suppliersBox.setOnMouseClicked(event -> {
            for (int j=0; j<suppliers.getChildren().size(); j++) {
                if(j % 2 == 1)
                    suppliers.getChildren().get(j).setStyle("-fx-background-color: #336699;");
                else
                    suppliers.getChildren().get(j).setStyle("-fx-background-color: #0F355C;");
            }

            lastClickedValue = s;
            updateDetail(s);
            suppliersBox.setStyle("-fx-background-color: #ff6600;");
        });
        suppliersBox.setMinWidth(HomeView.TAB_CONTENT_W / 4);
        suppliersBox.setPadding(new Insets(20));

        Text supplierText = new Text(s.getName());
        supplierText.setFont(new Font(20));
        supplierText.setFill(Color.WHITE);
        suppliersBox.getChildren().add(supplierText);

        if (i % 2 == 1)
            suppliersBox.setStyle("-fx-background-color: #336699;");
        else
            suppliersBox.setStyle("-fx-background-color: #0F355C;");

        suppliers.getChildren().add(suppliersBox);
    }
    private void createScroll(String search, boolean refresh, int sort_options){
        suppliers.getChildren().clear();

        int i=0;
        if(refresh)
            controller.createSuppliers();

        controller.sortSuppliers(sort_options);

        for(Supplier supplier : controller.getSuppliers()){
            if(supplier.getName().toLowerCase().contains(search)){
                createElement(supplier, i);
                i++;
            }
        }
    }
}
