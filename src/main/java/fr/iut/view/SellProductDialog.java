package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.ProductController;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Timestamp;


public class SellProductDialog extends Dialog<Client> {
    public static final double SELL_PRODUCT_WIDTH = App.SCREEN_W/3;
    /**
     * height of the dialog window
     */
    public static final double SELL_PRODUCT_HEIGHT = App.SCREEN_H/3;
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
    private HeaderView header = new HeaderView("Choix du client et de la quantité");

    /**
     * @param choices
     * @param product
     * @param controller
     * return a dialog that permittes to choose the supplier to restock
     */
    public SellProductDialog(ObservableList<Client> choices, Product product, ProductController controller) {
        setTitle("Vente d'un produit");
        dialogPane = getDialogPane();

        dialogPane.setPrefSize(SELL_PRODUCT_WIDTH, SELL_PRODUCT_HEIGHT);

        header.setMinWidth(SELL_PRODUCT_WIDTH);

        ComboBox<Client> clients = new ComboBox<>();
        clients.setItems(choices);
        clients.setMinSize(SELL_PRODUCT_WIDTH * 0.8, SELL_PRODUCT_HEIGHT * 0.1);
        clients.getSelectionModel().selectFirst();

        VBox.setMargin(clients, new Insets(0, 0, 0, SELL_PRODUCT_WIDTH * 0.1));

        Spinner<Integer> qttField = new Spinner<>(1, product.getStock(), 1);
        qttField.setMinWidth(SELL_PRODUCT_WIDTH * 0.3);
        qttField.setStyle("-fx-font-size: 18px");

        VBox.setMargin(qttField, new Insets(0, 0, 0, SELL_PRODUCT_WIDTH * 0.1));

        HBox buttons = new HBox();
        Button cancel = new Button("Annuler");
        cancel.setMinWidth(SELL_PRODUCT_WIDTH * 0.3);
        Button validate = new Button("Confimer");

        validate.setMinWidth(SELL_PRODUCT_WIDTH * 0.3);
        buttons.setSpacing(SELL_PRODUCT_WIDTH * 0.2);
        buttons.getChildren().addAll(cancel, validate);

        VBox.setMargin(buttons, new Insets(SELL_PRODUCT_HEIGHT * 0.2, 0, 0, SELL_PRODUCT_WIDTH * 0.1));

        wrapper.setPrefSize(SELL_PRODUCT_WIDTH , SELL_PRODUCT_HEIGHT * 0.9);
        wrapper.setSpacing(SELL_PRODUCT_WIDTH * 0.05);
        wrapper.getChildren().addAll(header, clients, qttField, buttons);

        dialogPane.getChildren().add(wrapper);

        Window window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        cancel.setOnMouseClicked(event -> window.hide());

        validate.setOnMouseClicked(event -> {
            GenericDAO<Purchase, Integer> dao = new GenericDAO<>(Purchase.class);
            Purchase purchase = new Purchase();
            purchase.setDatetime(new Timestamp(System.currentTimeMillis()));
            purchase.setQuantity(qttField.getValue());
            purchase.setProduct(product);
            purchase.setClient(clients.getSelectionModel().getSelectedItem());

            dao.save(purchase);
            product.setStock(product.getStock() - purchase.getQuantity());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succés");
            alert.setHeaderText("La vente a été enregistrée.");
            alert.showAndWait();

            window.hide();
        });
    }
}
