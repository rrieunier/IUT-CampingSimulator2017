package fr.iut.controller;

import fr.iut.persistence.dao.ReservationsDAO;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Purchase;
import fr.iut.persistence.entities.Reservation;
import fr.iut.persistence.entities.Spot;
import fr.iut.view.ReservationsView;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationsController {

    /**
     * instance of the dao for reservations
     */
    private ReservationsDAO dao = new ReservationsDAO();


    /**
     * Different types of sorting
     */
    public enum SortType {
        NONE,
        SORT_BY_NAME,
        SORT_BY_NAME_DESC,
        SORT_BY_DATE,
        SORT_BY_DATE_DESC
    }

    /**
     * How the reservations will be sorted, default is by date, the more recent at the top
     */
    private SortType sortType = SortType.SORT_BY_DATE_DESC;


    /**
     * Filters the reservations by client name or firstname
     */
    private String filter = null;

    /**
     * instance of the home controller
     */
    private HomeController homeController;


    public ReservationsController(HomeController homeController) {
        this.homeController = homeController;
    }

    public ScrollPane getView() {
        return new ReservationsView(this);
    }

    /**
     * @return whole list of reservations in database
     */
    public List<Reservation> getReservations() {
        List<Reservation> reservations = dao.findAll();

        if(filter != null && filter.length() > 0) {

            String parts[] = filter.split(":");

            if(parts.length == 2 && parts[0].equals("client_id")) //S'il y a le mot clé by_id dans le filtre, alors on affiche que les reservations faites par le client avec l'id précisé
                reservations = reservations.stream().filter(res -> res.getClient().getId() == Integer.parseInt(parts[1])).collect(Collectors.toList());

            else //Sinon on recherche dans le nom et prénoms
                reservations = reservations.stream().filter(res -> res.getClient().getFirstname().contains(filter) || res.getClient().getLastname().contains(filter)).collect(Collectors.toList());
        }

        reservations.sort((r1, r2) -> {

            switch (sortType) {
                case SORT_BY_NAME:
                    return r1.getClient().getLastname().compareTo(r2.getClient().getLastname());

                case SORT_BY_NAME_DESC:
                    return r2.getClient().getLastname().compareTo(r1.getClient().getLastname());

                case SORT_BY_DATE:
                    return r1.getReservationDate().compareTo(r2.getReservationDate());

                case SORT_BY_DATE_DESC:
                    return r2.getReservationDate().compareTo(r1.getReservationDate());

                default: return 0;
            }
        });

        return reservations;
    }

    /**
     * Defines a sort type for the reservations
     */
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * defines a filter for the reservations
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @param reservation
     * delete reservation from the database
     */
    public void removeReservation(Reservation reservation) {
        dao.remove(reservation);
    }

    /**
     * @param reservation
     * update a reservation in database
     */
    public void updateReservation(Reservation reservation) {
        dao.update(reservation);
    }

    /**
     * @param reservation
     * create a reservation in database
     */
    public void createReservation(Reservation reservation) {
        dao.save(reservation);
    }

    /**
     * @return retrieves all spots from map controller
     */
    public List<Spot> getAllSpots() {
        return homeController.getMapController().getAllSpots();
    }

    /**
     * @return retrieves all clients from clients controller
     */
    public List<Client> getAllClients() {
        return homeController.getClientsController().getClients();
    }

    /**
     * @param reservation
     * @return a bill generated as PDF of the reservation
     * @throws IOException if writing in pdf is impossible
     */
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
        String taxValue = reservation.getSpot().getCouncilTaxPersonDay() + "€ / jour x " + reservation.getPersonCount() + "pers";
        String pricePerDayValue = reservation.getSpot().getPricePerDay() + "€ / jour";
        ArrayList<String> purchasesValue = new ArrayList<>();

        if (reservation.getClient().getPurchases() != null) {
            for (Purchase p : reservation.getClient().getPurchases()) {
                purchasesValue.add(p.getDatetime().toString().substring(0, 10)
                        + " " + p.getProduct().getName() + " -> "
                        + p.getProduct().getSellPrice() * p.getQuantity() + "€");
            }
        }
        String reductionValue = reservation.getReduction() + "%";

        //prices
        float taxPrice = reservation.getPersonCount() * reservation.getSpot().getCouncilTaxPersonDay()
                * ((reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24));
        float pricePerDayPrice = reservation.getSpot().getPricePerDay() * ((reservation.getEndtime().getTime() - reservation.getStarttime().getTime())
                / (1000 * 60 * 60 * 24));

        float purchasesPrice = 0f;
        for (Purchase p : reservation.getClient().getPurchases()) {
            purchasesPrice += p.getProduct().getSellPrice() * p.getQuantity();
        }

        float totalPrice = (taxPrice + pricePerDayPrice + purchasesPrice) * ((100 - reservation.getReduction()) / 100);

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

        writer.endText();
        writer.beginText();

        newLineAndShowText(255, 75, reductionValue, writer);

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

    /**
     * @param facture
     * @throws IOException
     * export the bill as pdf
     */
    public void exportFacturePDF(PDDocument facture) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la facture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Portable Document Format", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(homeController.getView().getWindow());

        facture.save(selectedFile);
    }

    // UTILS=================================

    /**
     * @param x layout of the new line on x axe
     * @param y layout of the new line on y axe
     * @param text text to write in the new line
     * @param writer writer to use to write text
     * @throws IOException
     * utils method creating a new line and writing a text into it
     */
    private void newLineAndShowText(int x, int y, String text, PDPageContentStream writer) throws IOException {
        writer.newLineAtOffset(x, y);
        writer.showText(text);
    }
}
