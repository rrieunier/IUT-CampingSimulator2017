package fr.iut.controller;


import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Reservation;
import fr.iut.persistence.entities.Spot;
import fr.iut.view.ChartType;
import fr.iut.view.HomeView;
import fr.iut.view.SelectedCategory;
import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class StatisticsController implements ControllerInterface {

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
                return reservationChart(selectedChart, type);
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
                GenericDAOImpl<Spot, Integer> dao = new GenericDAOImpl<>(Spot.class);
                dao.open();

                ArrayList<Spot> reservations = (ArrayList<Spot>) dao.findAll();
                reservations.sort(new Comparator<Spot>() {
                    @Override
                    public int compare(Spot o1, Spot o2) {
                        return o1.getReservations().size() - o2.getReservations().size();
                    }
                });

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // données observables
                for (Spot r : reservations.subList(0, (reservations.size() > 8 ? 8 : reservations.size()))) {
                    pieChartData.add(new PieChart.Data(r.getName(), r.getReservations().size()));
                }

                chart = new PieChart(pieChartData);
                chart.setLegendSide(Side.RIGHT);
                chart.setPrefSize(HomeView.TAB_CONTENT_W / 2, HomeView.TAB_CONTENT_H * 15 / 20);
                chart.getStylesheets().add(new File("res/style.css").toURI().toString());
                chart.getStyleClass().add("pie-chart");

                chart.setTitle("Emplacements les plus réservés");

                dao.close();
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;
        }

        return chart;
    }
}
