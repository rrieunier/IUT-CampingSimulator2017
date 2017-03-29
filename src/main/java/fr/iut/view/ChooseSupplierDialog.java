package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.ProductController;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Product;
import fr.iut.persistence.entities.Restocking;
import fr.iut.persistence.entities.Supplier;
import fr.iut.persistence.entities.SupplierProposeProduct;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * dialog that permittes to choose the supplier to restock
 */
public class ChooseSupplierDialog extends Dialog<Supplier> {

    /**
     * width of the dialog window
     */
    public static final double SUPPLIER_CHOOSE_WIDTH = App.SCREEN_W / 3;
    /**
     * height of the dialog window
     */
    public static final double SUPPLIER_CHOOSE_HEIGHT = App.SCREEN_H / 3;
    /**
     * pane of the dialog
     */
    private DialogPane dialogPane;
    /**
     * wrapper wrapping content of the dialog
     */
    private VBox wrapper = new VBox();
    /**
     * header of the dialog
     */
    private HeaderView header = new HeaderView("Choix du fournisseur");
    /**
     * string representing the price proposed by the current selected supplier
     */
    private String price = "";
    /**
     * field of the price
     */
    private TextField priceField = new TextField();

    /**
     * @param choices
     * @param product
     * @param controller return a dialog that permittes to choose the supplier to restock
     */
    public ChooseSupplierDialog(ObservableList<Supplier> choices, Product product, ProductController controller) {
        setTitle("Réapprovisionement");
        dialogPane = getDialogPane();

        dialogPane.setPrefSize(SUPPLIER_CHOOSE_WIDTH, SUPPLIER_CHOOSE_HEIGHT);

        header.setMinWidth(SUPPLIER_CHOOSE_WIDTH);

        ComboBox<Supplier> suppliers = new ComboBox<>();
        suppliers.setItems(choices);
        suppliers.setMinSize(SUPPLIER_CHOOSE_WIDTH * 0.8, SUPPLIER_CHOOSE_HEIGHT * 0.1);
        suppliers.getSelectionModel().selectFirst();
        for (SupplierProposeProduct s : product.getSupplierProposeProducts()) {
            if (s.getSupplier().getId().equals(suppliers.getSelectionModel().getSelectedItem().getId())) {
                price = String.valueOf(s.getSellPrice() + "€ / unité");
            }
        }

        priceField.setText("Prix: " + price);
        priceField.setEditable(false);

        suppliers.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            for (SupplierProposeProduct s : product.getSupplierProposeProducts()) {
                if (s.getSupplier().getId().equals(suppliers.getSelectionModel().getSelectedItem().getId())) {
                    price = String.valueOf(s.getSellPrice() + "€ / unité");
                }
            }

            priceField.setText("Prix: " + price);
        });
        VBox.setMargin(suppliers, new Insets(0, 0, 0, SUPPLIER_CHOOSE_WIDTH * 0.1));

        HBox textFields = new HBox();
        priceField.setMinWidth(SUPPLIER_CHOOSE_WIDTH * 0.3);
        priceField.setStyle("-fx-font-size: 18px");

        TextField qttField = new TextField("1");
        qttField.setMinWidth(SUPPLIER_CHOOSE_WIDTH * 0.3);
        qttField.setStyle("-fx-font-size: 18px");

        textFields.getChildren().addAll(priceField, qttField);
        textFields.setSpacing(SUPPLIER_CHOOSE_WIDTH * 0.2);
        VBox.setMargin(textFields, new Insets(0, 0, 0, SUPPLIER_CHOOSE_WIDTH * 0.1));

        HBox buttons = new HBox();
        Button cancel = new Button("Annuler");
        cancel.setMinWidth(SUPPLIER_CHOOSE_WIDTH * 0.3);
        Button validate = new Button("Commander");

        validate.setMinWidth(SUPPLIER_CHOOSE_WIDTH * 0.3);
        buttons.setSpacing(SUPPLIER_CHOOSE_WIDTH * 0.2);
        buttons.getChildren().addAll(cancel, validate);

        VBox.setMargin(buttons, new Insets(SUPPLIER_CHOOSE_HEIGHT * 0.2, 0, 0, SUPPLIER_CHOOSE_WIDTH * 0.1));

        wrapper.setPrefSize(SUPPLIER_CHOOSE_WIDTH, SUPPLIER_CHOOSE_HEIGHT * 0.9);
        wrapper.setSpacing(SUPPLIER_CHOOSE_WIDTH * 0.05);
        wrapper.getChildren().addAll(header, suppliers, textFields, buttons);

        dialogPane.getChildren().add(wrapper);

        Window window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        cancel.setOnMouseClicked(event -> window.hide());

        validate.setOnMouseClicked(event -> {
            try {
                if (suppliers.getSelectionModel().getSelectedItem() != null) {
                    controller.sendMailToSupplier(suppliers.getSelectionModel().getSelectedItem());

                    product.setStock(product.getStock() + Integer.parseInt(qttField.getText()));

                    Restocking restocking = new Restocking();
                    restocking.setProduct(product);
                    restocking.setQuantity(Integer.parseInt(qttField.getText()));
                    restocking.setDatetime(new Timestamp(System.currentTimeMillis()));
                    restocking.setSupplier(suppliers.getSelectionModel().getSelectedItem());

                    GenericDAO<Restocking, Integer> restockingDao = new GenericDAO<>(Restocking.class);
                    restockingDao.save(restocking);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            window.hide();
        });
    }
}
