package fr.iut.controller;


import fr.iut.persistence.entities.*;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReservationController {
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
        for (Purchase p : reservation.getClient().getPurchases()) {
            purchasesValue.add(p.getDatetime().toString().substring(0, 10)
                    + " " + p.getProduct().getName() + " -> "
                    + p.getProduct().getSellPrice() * p.getQuantity() + "€");
        }
        String reductionValue = reservation.getReduction() + "%";
        String totalValue = "Somme des valeurs";

        float taxPrice = reservation.getPersonCount() * reservation.getSpot().getCoucilTaxPersonDay()
                * (reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24);
        float pricePerDayPrice = reservation.getSpot().getPricePerDay() * (reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24);
        float purchasesPrice = 0f;
        for (Purchase p : reservation.getClient().getPurchases()) {
            purchasesPrice += p.getProduct().getSellPrice() * p.getQuantity();
        }
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
        writer.newLineAtOffset(20, 750);
        writer.showText(title);


        writer.endText();
        //rectangle infos facture
        writer.setNonStrokingColor(java.awt.Color.getHSBColor(1, 271, 87));
        writer.addRect(350, 700, 250, 75);
        writer.fill();
        writer.beginText();
        //infos facture
        writer.setFont(PDType1Font.HELVETICA, 15);
        writer.setNonStrokingColor(java.awt.Color.BLACK);
        writer.newLineAtOffset(360, 755);
        writer.showText(factureNumber);
        writer.newLineAtOffset(0, -20);
        writer.showText(todaysDate);

        writer.endText();
        //rectangle infos client
        writer.setNonStrokingColor(java.awt.Color.getHSBColor(1, 271, 87));
        writer.addRect(350, 600, 250, 75);
        writer.fill();
        writer.beginText();

        //infos client
        writer.setNonStrokingColor(java.awt.Color.BLACK);
        writer.newLineAtOffset(20, 660);
        writer.showText(refClient);
        writer.newLineAtOffset(340, -5);
        writer.showText(clientName);
        writer.newLineAtOffset(0, -20);
        writer.showText(clientMail);
        writer.newLineAtOffset(0, -20);
        writer.showText(clientPhone);

        //dates et emplacement
        writer.newLineAtOffset(-340, -100);
        writer.showText(timelaspe);
        writer.newLineAtOffset(330, 0);
        writer.showText(location);

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
        writer.newLineAtOffset(15, 475);
        writer.showText(designation);
        writer.newLineAtOffset(240, 0);
        writer.showText(value);
        writer.newLineAtOffset(230, 0);
        writer.showText(price);

        //première colonne
        writer.setFont(PDType1Font.HELVETICA, 13);
        writer.newLineAtOffset(-470, -25);
        writer.showText(durationLabel);
        writer.newLineAtOffset(0, -25);
        writer.showText(personCountLabel);
        writer.newLineAtOffset(0, -25);
        writer.showText(taxlabel);
        writer.newLineAtOffset(0, -25);
        writer.showText(pricePerDayLabel);
        writer.newLineAtOffset(0, -25);
        writer.showText(purchasesLabel);
        //reduction & total
        writer.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 13);
        writer.newLineAtOffset(0, -275);
        writer.showText(reductionlabel);
        writer.newLineAtOffset(0, -30);
        writer.showText(totalLabel);

        //seconde colonne
        writer.setFont(PDType1Font.HELVETICA, 13);
        writer.newLineAtOffset(240, 405);
        writer.showText(durationValue);
        writer.newLineAtOffset(0, -25);
        writer.showText(personCountValue);
        writer.newLineAtOffset(0, -25);
        writer.showText(taxValue);
        writer.newLineAtOffset(0, -25);
        writer.showText(pricePerDayValue);
        writer.newLineAtOffset(0, -25);
        for (String s : purchasesValue) {
            writer.showText(s);
            writer.newLineAtOffset(0, -25);
        }
        writer.newLineAtOffset(0, -125);
        writer.showText(reductionValue);

        //troisième colonne
        writer.newLineAtOffset(225, 325);
        writer.showText(String.valueOf(taxPrice) + "€");
        writer.newLineAtOffset(0, -25);
        writer.showText(String.valueOf(pricePerDayPrice) + "€");
        writer.newLineAtOffset(0, -25);
        writer.showText(String.valueOf(purchasesPrice) + "€");
        writer.newLineAtOffset(0, -305);
        writer.showText(String.valueOf(totalPrice) + "€");

        writer.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
        writer.newLineAtOffset(-470, -35);
        writer.showText(warning);

        //fin écriture
        writer.endText();
        writer.close();
        // ===================================================
        pdf.addPage(facture);

        return pdf;
    }

    public void printFacture() {

    }

    public void exportFacturePDF() {

    }
}
