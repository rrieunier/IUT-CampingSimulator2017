package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.ReservationsController;
import fr.iut.persistence.entities.Purchase;
import fr.iut.persistence.entities.Reservation;
import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;


public class BillSummaryView extends Dialog<Void> {

    /**
     * width of the facture preview window
     */
    public static final double BILL_SUMMARY_WIDTH = App.SCREEN_W/1.5;
    /**
     * height of the facture preview window
     */
    public static final double BILL_SUMMARY_HEIGHT = App.SCREEN_H/1.3;
    /**
     * main container
     */
    private VBox wrapper = new VBox();
    /**
     * body of the window
     */
    private HBox content = new HBox();
    /**
     * header of the window
     */
    private HeaderView header = new HeaderView("Aperçu de la facture");
    /**
     * facture details grid
     */
    private GridPane grid = new GridPane();
    /**
     * reservation related to the facture
     */
    private Reservation reservation;
    /**
     * instance of the controller
     */
    private ReservationsController controller;
    /**
     * pdf facture of the reservation
     */
    private PDDocument facturePDF;

    /**
     * @param reservation reservation to create the facture for
     * @param controller instance of the controller
     * create view of the facture related to reservation
     */
    public BillSummaryView(@NamedArg("reservation") Reservation reservation,
                           @NamedArg("controller") ReservationsController controller) {
        setTitle("Aperçu de la facture");

        this.reservation = reservation;
        this.controller = controller;

        try {
            facturePDF = controller.makeFacturePDF(reservation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // on cross clicked
        Window window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        // dialog pane
        DialogPane dialogPane = getDialogPane();
        dialogPane.setMinHeight(BILL_SUMMARY_HEIGHT);
        dialogPane.setMaxSize(BILL_SUMMARY_WIDTH, BILL_SUMMARY_HEIGHT);
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());

        grid.setMaxHeight(BILL_SUMMARY_HEIGHT * 0.8);
        grid.setPrefWidth(BILL_SUMMARY_WIDTH * 0.6);
        grid.setStyle("-fx-background-color: whitesmoke;");
        grid.setGridLinesVisible(true);

        // content ===========================
        String[] factureFields = {"Date de d'arrivée", "Date de départ", "Taxe de séjour", "Prix/jour", "Cumul achats", "Réduction", "Total"};

        //build grid
        for (int i = 0; i < 2; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPrefWidth(BILL_SUMMARY_HEIGHT / 2);
            grid.getColumnConstraints().add(constraint);
        }
        for (int i = 0; i < factureFields.length; i++) {
            RowConstraints constraint = new RowConstraints();
            constraint.setPrefHeight(grid.getMaxHeight() / 6);
            grid.getRowConstraints().add(constraint);
        }
        for (int i = 0; i < factureFields.length; i++) {
            Label label = new Label(factureFields[i] + ":");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-label");
            grid.addRow(i, label);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }
        for (int i = 0; i < factureFields.length; i++) {
            Label label = new Label(".");
            label.getStylesheets().add(new File("res/style.css").toURI().toString());
            label.getStyleClass().add("grid-stock-field");
            grid.add(label, 1, i);
            GridPane.setMargin(label, new Insets(0, 0, 0, 30));
        }

        // fill grid
        Timestamp startime = this.reservation.getStarttime();
        Timestamp endtime = this.reservation.getEndtime();
        int personCount= this.reservation.getPersonCount();
        float pricePerDay = this.reservation.getSpot().getPricePerDay();
        float totalPurchases = 0;
        for (Purchase p : this.reservation.getClient().getPurchases())
            totalPurchases += (p.getProduct().getSellPrice() * p.getQuantity());

        float duration = (endtime.getTime() - startime.getTime()) / (1000 *60 *60* 24);
        float council_tax = this.reservation.getPersonCount() * this.reservation.getSpot().getCouncilTaxPersonDay() * duration;
        float reduction = (100 - this.reservation.getReduction()) / 100;

        float total = (council_tax + (duration * pricePerDay) + totalPurchases) * reduction;

        for (Node node : grid.getChildren()) {
            if (node instanceof Label && GridPane.getColumnIndex(node) == 1) {
                switch (GridPane.getRowIndex(node)) {
                    case 0:
                        ((Label) node).setText(startime.toString());
                        break;
                    case 1:
                        ((Label) node).setText(endtime.toString());
                        break;
                    case 2:
                        ((Label) node).setText(String.valueOf(council_tax) + "€ (1.20€/jour x " + personCount + "pers)");
                        break;
                    case 3:
                        ((Label) node).setText(String.valueOf(pricePerDay) + "€/j");
                        break;
                    case 4:
                        ((Label) node).setText(String.valueOf(totalPurchases) + "€");
                        break;
                    case 5:
                        ((Label) node).setText(String.valueOf(this.reservation.getReduction()) + "%");
                        break;
                    case 6:
                        ((Label) node).setText(String.valueOf(total) + "€");
                        break;
                }
            }
        }

        // ===================================

        // close button
        Button exportButton = new Button("Exporter");
        exportButton.setPrefWidth(BILL_SUMMARY_WIDTH / 5);
        exportButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        exportButton.getStyleClass().add("record-sales");
        exportButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    controller.exportFacturePDF(facturePDF);
                    facturePDF.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button okButton = new Button("Terminer");
        okButton.setPrefWidth(BILL_SUMMARY_WIDTH / 5);
        okButton.getStylesheets().add(new File("res/style.css").toURI().toString());
        okButton.getStyleClass().add("record-sales");
        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    facturePDF.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                window.hide();
            }
        });

        VBox buttons = new VBox();
        buttons.setSpacing(BILL_SUMMARY_HEIGHT * 0.05);
        buttons.getChildren().addAll(exportButton, okButton);
        buttons.setMargin(exportButton, new Insets(BILL_SUMMARY_WIDTH * 0.4, 0, 0, 0));

        header.setMinWidth(BILL_SUMMARY_WIDTH);

        HBox.setMargin(grid, new Insets(BILL_SUMMARY_HEIGHT / 25, 0, 0, BILL_SUMMARY_WIDTH / 20));
        HBox.setMargin(buttons, new Insets(BILL_SUMMARY_HEIGHT / 25, 0, 0, 0));
        content.setSpacing(BILL_SUMMARY_WIDTH * 0.1);
        content.getChildren().addAll(grid, buttons);

        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setSpacing(20);


        wrapper.getChildren().addAll(header, content);
        dialogPane.setContent(wrapper);
    }
}
