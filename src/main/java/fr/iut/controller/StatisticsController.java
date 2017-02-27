package fr.iut.controller;


import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Reservation;
import fr.iut.view.ChartType;
import fr.iut.view.SelectedCategory;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.ArrayList;
import java.util.Comparator;

public class StatisticsController implements ControllerInterface{

    private HomeController controller;

    public StatisticsController(@NamedArg("controller") HomeController controller) {
        this.controller = controller;
    }


    @Override
    public Object getView() {
        return null;
    }

    @Override
    public void finish() {

    }

    // TODO: Implement other charts type (other than PieChart)
    public Chart makeChart(SelectedCategory category, int selectedChart, ChartType type) {
        switch (category) {
            case RESERVATIONS:
                reservationChart(selectedChart, type);
                break;
            case CLIENTS:
                break;
            case PURCHASES:
                break;
            case EMPLOYEES:
                break;
            case PROBLEMS:
                break;
            case OTHERS:
                break;
            case NONE:
                break;
        }
        return new PieChart();
    }

    private Chart reservationChart(int selectedChart, ChartType type) {

        GenericDAOImpl<Reservation, Integer> dao = new GenericDAOImpl<>(Reservation.class);
        dao.open();

        Chart chart = new PieChart();
        switch (type) {
            case PIE:
                break;
            case LINE:
                break;
            case BAR:
                break;
        }

        switch (selectedChart) {
            case 0: // emplacements les plus réservés
                ArrayList<Reservation> reservations = (ArrayList<Reservation>) dao.findAll();
                reservations.sort(new Comparator<Reservation>() {
                    @Override
                    public int compare(Reservation o1, Reservation o2) {
                        return o1.getSpot().getReservations().size() - o2.getSpot().getReservations().size();
                    }
                });

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // données observables
                for (Reservation r : reservations.subList(0, 8)) {
                    pieChartData.add(new PieChart.Data(r.getSpot().getName(), r.getSpot().getReservations().size()));
                }

                chart = new PieChart(pieChartData);
                chart.setTitle("Emplacements les plus réservés");
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;
        }

        dao.close();
        return chart;
    }
}
