package fr.iut.controller;

import fr.iut.persistence.dao.ReservationsDAO;
import fr.iut.persistence.entities.Reservation;
import fr.iut.view.ReservationsView;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Created by shellcode on 3/21/17.
 */
public class ReservationsController {

    private ReservationsDAO dao = new ReservationsDAO();
    private ReservationsView reservationsView = new ReservationsView(this);
    private HomeController homeController;


    public ReservationsController(HomeController homeController) {
        this.homeController = homeController;
    }

    public ScrollPane getView() {
        return reservationsView;
    }

    public List<Reservation> getReservations() {
        return dao.findAll();
    }

    public PDDocument makeFacturePDF(Reservation reservation) throws IOException {

        PDDocument pdf = new PDDocument();
        PDPage facture = new PDPage();

        PDDocumentInformation factureInformations = pdf.getDocumentInformation();
        factureInformations.setAuthor("Camping Des Flots Blancs");
        factureInformations.setTitle("FACTURE n°" + reservation.getId());
        factureInformations.setSubject("Facture de la réservation n°" + reservation.getId());

        // champs à écrire dans le pdf ==============================
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String title = "Camping des Flots Blancs";
        String factureNumber = "Facture n°" + reservation.getId();
        String todaysDate = "du " + dateFormat.format(date).substring(0, 10);

        String refClient = "Réf. client: " + reservation.getClient().getId();
        String clientName = reservation.getClient().getFirstname() + " " + reservation.getClient().getLastname();
        String clientMail = reservation.getClient().getEmail();
        String clientPhone = reservation.getClient().getPhone();

        String timelaspe = "Du " + reservation.getStarttime().toString().substring(0, 10)
                + " au " + reservation.getEndtime().toString().substring(0, 10);
        String location = "Emplacement: " + reservation.getSpot().getName();

        String designation = "Désignation";
        String value = "Valeur";
        String price = "Prix TTC.";

        // labels
        String durationLabel = "Durée de séjour";
        String personCountLabel = "Nombre de personnes";
        String taxlabel = "Taxe de séjour";
        String pricePerDayLabel = "Prix de l'emplacement par jour";
        String purchasesLabel = "Cumul des achats";
        String reductionlabel = "Réduction";
        String totalLabel = "Total";

        //values
        String durationValue = (reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24) + " jours";
        String personCountValue = reservation.getPersonCount() + " pers.";
        String taxValue = reservation.getSpot().getCoucilTaxPersonDay() + "€ / jour x " + reservation.getPersonCount() + "pers";
        String pricePerDayValue = reservation.getSpot().getPricePerDay() + "€ / jour";
        ArrayList<String> purchasesValue = new ArrayList<>();
        /*
        for (Purchase p : reservation.getClient().getPurchases()) {
            purchasesValue.add(p.getDatetime().toString().substring(0, 10)
                    + " " + p.getProduct().getName() + " -> "
                    + p.getProduct().getSellPrice() * p.getQuantity() + "€");
        }
        */
        String reductionValue = reservation.getReduction() + "%";

        //prices
        float taxPrice = reservation.getPersonCount() * reservation.getSpot().getCoucilTaxPersonDay()
                * (reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24);
        float pricePerDayPrice = reservation.getSpot().getPricePerDay() * (reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24);
        float purchasesPrice = 0f;
        /*
        for (Purchase p : reservation.getClient().getPurchases()) {
            purchasesPrice += p.getProduct().getSellPrice() * p.getQuantity();
        }
        */
        float totalPrice = taxPrice + pricePerDayPrice + purchasesPrice * (1 - (reservation.getReduction() / 100));

        String warning = "Ce document est un original, toute tentative de reproduction sera passible de poursuites.";

        //===========================================================

        // écriture dans le pdf =====================================
        // l'origine du repaire est en bas à gauche
        // dans les x, écrire entre 20 & 500
        // dans les y, écrire entre 50 & 750
        PDPageContentStream writer = new PDPageContentStream(pdf, facture);
        writer.beginText();

        // titre
        writer.setFont(PDType1Font.HELVETICA_BOLD, 20);
        newLineAndShowText(20, 750, title, writer);


        writer.endText();
        //rectangle infos facture
        writer.setNonStrokingColor(java.awt.Color.getHSBColor(1, 271, 87));
        writer.addRect(350, 700, 250, 75);
        writer.fill();
        writer.beginText();
        //infos facture
        writer.setFont(PDType1Font.HELVETICA, 15);
        writer.setNonStrokingColor(java.awt.Color.BLACK);
        newLineAndShowText(360, 755, factureNumber, writer);
        newLineAndShowText(0, -20, todaysDate, writer);

        writer.endText();
        //rectangle infos client
        writer.setNonStrokingColor(java.awt.Color.getHSBColor(1, 271, 87));
        writer.addRect(350, 600, 250, 75);
        writer.fill();
        writer.beginText();

        //infos client
        writer.setNonStrokingColor(java.awt.Color.BLACK);
        newLineAndShowText(20, 660, refClient, writer);
        newLineAndShowText(340, -5, clientName, writer);
        newLineAndShowText(0, -20, clientMail, writer);
        newLineAndShowText(0, -20, clientPhone, writer);

        //dates et emplacement
        newLineAndShowText(-340, -100, timelaspe, writer);
        newLineAndShowText(330, 0, location, writer);

        writer.endText();
        //tableaux facturation
        writer.addRect(10, 495, 590, 1); //l1
        writer.addRect(10, 465, 590, 1); //l2
        writer.addRect(10, 495, 1, -460); //c1
        writer.addRect(250, 495, 1, -460); //c2
        writer.addRect(475, 495, 1, -460); //c3
        writer.addRect(600, 495, 1, -460); //c4
        writer.addRect(10, 95, 590, 1); //l3
        writer.addRect(10, 65, 590, 1); //l4
        writer.addRect(10, 35, 590, 1); //l5
        writer.fill();
        writer.beginText();

        //titre des colonnes
        writer.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
        newLineAndShowText(15, 475, designation, writer);
        newLineAndShowText(240, 0, value, writer);
        newLineAndShowText(230, 0, price, writer);

        //première colonne
        writer.setFont(PDType1Font.HELVETICA, 13);
        newLineAndShowText(-470, -24, durationLabel, writer);
        newLineAndShowText(0, -25, personCountLabel, writer);
        newLineAndShowText(0, -25, taxlabel, writer);
        newLineAndShowText(0 ,-25, pricePerDayLabel, writer);
        newLineAndShowText(0, -25, purchasesLabel, writer);
        //reduction & total
        writer.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
        writer.newLineAtOffset(0, -275);
        writer.showText(reductionlabel);
        writer.newLineAtOffset(0, -30);
        writer.showText(totalLabel);

        //seconde colonne
        writer.setFont(PDType1Font.HELVETICA, 13);
        newLineAndShowText(240, 405, durationValue, writer);
        newLineAndShowText(0, -25, personCountValue, writer);
        newLineAndShowText(0, -25, taxValue, writer);
        newLineAndShowText(0, -25, pricePerDayLabel, writer);
        writer.newLineAtOffset(0, -25);
        for (String s : purchasesValue) {
            writer.showText(s);
            writer.newLineAtOffset(0, -25);
        }
        newLineAndShowText(0, -125, reductionValue, writer);

        //troisième colonne
        newLineAndShowText(225, 325, String.valueOf(taxPrice) + "€", writer);
        newLineAndShowText(0, -25, String.valueOf(pricePerDayPrice) + "€", writer );
        newLineAndShowText(0, -25, String.valueOf(purchasesPrice) + "€", writer);
        newLineAndShowText(0, -305, String.valueOf(totalPrice) + "€", writer);

        writer.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
        newLineAndShowText(-470, -35, warning, writer);

        //fin écriture
        writer.endText();
        writer.close();
        // ===================================================
        pdf.addPage(facture);

        return pdf;
    }

    public void printFacture(PDDocument facture) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la facture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Portable Document Format", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(homeController.getView().getWindow());
    }

    public void exportFacturePDF(PDDocument facture) {


    }

    // UTILS=================================

    private void newLineAndShowText(int x, int y, String text, PDPageContentStream writer) throws IOException {
        writer.newLineAtOffset(x, y);
        writer.showText(text);
    }
}
