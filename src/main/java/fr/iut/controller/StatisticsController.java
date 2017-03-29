package fr.iut.controller;


import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.*;
import fr.iut.view.ChartType;
import fr.iut.view.ChartView;
import fr.iut.view.SelectedCategory;
import fr.iut.view.StatisticsView;
import javafx.beans.NamedArg;
import javafx.geometry.Side;
import javafx.scene.SubScene;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticsController {

    public SubScene getView() {
        return new StatisticsView(this);
    }

    /**
     * @param category
     * @param selectedChart
     * @param type
     * @return a chart depending on the category chosen
     */
    public ChartView makeChart(@NamedArg("category") SelectedCategory category,
                               @NamedArg("selectedChart") int selectedChart,
                               @NamedArg("type") ChartType type) {
        switch (category) {
            case RESERVATIONS:
                return reservationChart(selectedChart, type);
            case CLIENTS:
                return clientChart(selectedChart, type);
            case PURCHASES:
                return purchaseChart(selectedChart, type);
            case OTHERS:
                return otherChart(selectedChart, type);
            case NONE:
                break;
        }
        return null;
    }


    /**
     * @param selectedChart
     * @param type
     * @return return charts for the reservation category
     */
    private ChartView reservationChart(@NamedArg("selectedChart") int selectedChart, @NamedArg("type") ChartType type) {
        GenericDAO<Spot, Integer> dao = new GenericDAO<>(Spot.class);
        ArrayList<Spot> spots = (ArrayList<Spot>) dao.findAll();
        ArrayList<String> objects = new ArrayList<>();
        ArrayList<Float> comparative = new ArrayList<>();
        String title = null;

        switch (selectedChart) {
            case 0: // emplacements les plus réservés

                spots.sort((o1, o2) -> o2.getReservations().size() - o1.getReservations().size());

                for (Spot s : spots) {
                    objects.add(s.getName());
                    comparative.add((float) s.getReservations().size());
                }

                comparative.sort((o1, o2) -> (int) (o2 - o1));

                title = "Emplacements les plus réservés";
                break;

            case 1: // emplacements les moins réservés

                spots.sort((o1, o2) -> o2.getReservations().size() - o1.getReservations().size());

                for (Spot s : spots) {
                    objects.add(s.getName());
                    comparative.add((float) s.getReservations().size());
                }

                comparative.sort((o1, o2) -> (int) (o1 - o2));

                title = "Emplacements les moins réservés\n\t\t(1/nb de res.)";
                break;

            default:
                break;
        }
        ChartView chart = new ChartView(ChartType.PIE, objects, comparative, title, "Nom", "Nombre de rés.");
        chart.setLegendSide(Side.BOTTOM);

        return chart;
    }
    /**
     * @param selectedChart
     * @param type
     * @return return charts for the client category
     */
    private ChartView clientChart(@NamedArg("selectedChart") int selectedChart,
                                  @NamedArg("type") ChartType type) {
        GenericDAO<Client, Integer> dao = new GenericDAO<>(Client.class);
        ArrayList<Client> clients = (ArrayList<Client>) dao.findAll();
        ArrayList<String> objects = new ArrayList<>();
        ArrayList<Float> comparative = new ArrayList<>();
        ChartView chartView = null;

        switch (selectedChart) {
            case 0: // clients les plus fidèles

                clients.sort((o1, o2) -> o2.getReservations().size() - o1.getReservations().size());

                for (Client c : clients) {
                    objects.add(c.getFirstname() + " " + c.getLastname());
                    comparative.add((float) c.getReservations().size());
                }

                comparative.sort((o1, o2) -> (int) (o2 - o1));

                chartView = new ChartView(ChartType.PIE, objects, comparative, "Clients les plus fidèles", "Nom", "Nombre de rés.");
                chartView.setLegendSide(Side.BOTTOM);
                break;

            case 1:// clients les plus plaintifs

                clients.sort((o1, o2) -> o2.getProblems().size() - o1.getProblems().size());

                for (Client c : clients) {
                    objects.add(c.getFirstname() + " " + c.getLastname());
                    comparative.add((float) c.getProblems().size());
                }

                comparative.sort((o1, o2) -> (int) (o2 - o1));

                chartView = new ChartView(ChartType.PIE, objects, comparative,"Clients ayant reportés \nle plus de problèmes", "Nom", "Nombre de prob.");
                break;

            case 2: // clients les moins fidèles

                clients.sort(Comparator.comparingInt(o -> o.getProblems().size()));

                for (Client c : clients) {
                    objects.add(c.getFirstname() + " " + c.getLastname());
                    comparative.add((float) c.getProblems().size());
                }

                comparative.sort((o1, o2) -> (int) (o1 - o2));

                chartView = new ChartView(ChartType.PIE, objects, comparative, "Clients ayant reportés \nle moins de problèmes", "Nom", "Nombre de prob.");
                break;

            case 3:// clients achetant le plus

                clients.sort((o1, o2) -> o2.getPurchases().size() - o1.getPurchases().size());

                for (Client c : clients) {
                    objects.add(c.getFirstname() + " " + c.getLastname());
                    comparative.add((float) c.getPurchases().size());
                }

                comparative.sort((o1, o2) -> (int) (o2 - o1));

                chartView = new ChartView(type, objects, comparative,"Clients ayant acheté le plus", "Nom", "Nombre d'achats");
                break;
        }

        assert chartView != null;
        chartView.setLegendSide(Side.BOTTOM);
        return chartView;
    }

    /**
     * @param selectedChart
     * @param type
     * @return return charts for the purchase category
     */
    private ChartView purchaseChart(@NamedArg("selectedChart") int selectedChart, @NamedArg("type") ChartType type) {

        GenericDAO<Product, Integer> dao = new GenericDAO<>(Product.class);
        ArrayList<Product> products = (ArrayList<Product>) dao.findAll();
        ArrayList<String> objects = new ArrayList<>();
        ArrayList<Float> comparative = new ArrayList<>();
        String title = null;

        switch (selectedChart) {
            case 0: // produit le plus vendu
                products.sort((p1, p2) -> p2.getSumPurchases() - p1.getSumPurchases());
                comparative.sort((o1, o2) -> (int) (o2 - o1));
                title = "Produits les plus achetés";
                break;

            case 1: // produits les moins vendus
                products.sort(Comparator.comparingInt(Product::getSumPurchases));
                comparative.sort((o1, o2) -> (int) (o1 - o2));
                title = "Produits les moins achetés";
                break;

            default:
                break;
        }

        for (Product c : products) {
            int sum = c.getSumPurchases();
            objects.add(c.getName());
            comparative.add((float) sum);
        }

        ChartView chartView = new ChartView(ChartType.PIE, objects, comparative, title, "Nom", "Nombre de ventes");
        chartView.setLegendSide(Side.BOTTOM);
        return chartView;
    }

    /**
     * @param selectedChart
     * @param type
     * @return return charts for the others category
     */
    private ChartView otherChart(@NamedArg("selectedChart") int selectedChart,
                                   @NamedArg("type") ChartType type) {
        GenericDAO<Employee, Integer> dao = new GenericDAO<>(Employee.class);
        ArrayList<Employee> employees = (ArrayList<Employee>) dao.findAll();
        ArrayList<String> objects = new ArrayList<>();
        ArrayList<Float> comparative = new ArrayList<>();
        ChartView chartView = null;

        switch (selectedChart) {
            case 0:
                employees.sort((o1, o2) -> o2.getLogs().size() - o1.getLogs().size());

                for (Employee e : employees) {
                    objects.add(e.getFirstName() + " " + e.getLastName());
                    comparative.add((float) e.getLogs().size());
                }
                comparative.sort((o1, o2) -> (int) (o2 - o1));

                chartView = new ChartView(ChartType.PIE, objects, comparative,"Employés se connectant le plus", "Nom", "Nombre de connections");
                chartView.setLegendSide(Side.BOTTOM);
                
                break;

            default:
                break;
        }
        return chartView;
    }
}


