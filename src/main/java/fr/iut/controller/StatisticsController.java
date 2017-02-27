package fr.iut.controller;


import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import fr.iut.persistence.entities.Spot;
import fr.iut.view.ChartType;
import fr.iut.view.ChartView;
import fr.iut.view.HomeView;
import fr.iut.view.SelectedCategory;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.persistence.Id;
import java.io.File;
import java.util.*;

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
    public ChartView makeChart(@NamedArg("category") SelectedCategory category,
                               @NamedArg("selectedChart") int selectedChart,
                               @NamedArg("type") ChartType type)
    {
        switch (category) {
            case RESERVATIONS:
                return reservationChart(selectedChart, type);
            case CLIENTS:
                return clientChart(selectedChart, type);
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
        return null;
    }

    private ChartView reservationChart(@NamedArg("selectedChart") int selectedChart,
                                       @NamedArg("type") ChartType type)
    {

        Chart chart = new PieChart();
        TableView<SpotV> table = new TableView<>();

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
                GenericDAOImpl<Spot, Integer> dao_spots = new GenericDAOImpl<>(Spot.class);
                dao_spots.open();

                ArrayList<Spot> spots = (ArrayList<Spot>) dao_spots.findAll();
                spots.sort(new Comparator<Spot>() {
                    @Override
                    public int compare(Spot o1, Spot o2) {
                        return o2.getReservations().size() - o1.getReservations().size();
                    }
                });

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // données observables
                for (Spot r : spots.subList(0, (spots.size() > 8 ? 8 : spots.size()))) {
                    pieChartData.add(new PieChart.Data(r.getName(), r.getReservations().size()));
                }
                if (spots.size() > 7) {
                    int sum = 0;
                    for (Spot c : spots.subList(7, spots.size())) {
                        sum += c.getReservations().size();
                    }
                    sum = sum / (spots.subList(7, spots.size())).size() + 1;
                    pieChartData.add(new PieChart.Data("Others", sum));
                }

                chart = new PieChart(pieChartData);
                chart.setLegendSide(Side.RIGHT);
                chart.setPrefSize(HomeView.TAB_CONTENT_W / 2, HomeView.TAB_CONTENT_H * 15 / 20);
                chart.getStylesheets().add(new File("res/style.css").toURI().toString());
                chart.getStyleClass().add("pie-chart");
                chart.setTitle("Emplacements les plus réservés");

                ObservableList<SpotV> data = FXCollections.observableArrayList();
                for (Spot s : spots) {
                    data.add(new SpotV(s.getName(), s.getReservations().size()));
                }

                TableColumn nameColum = new TableColumn<>("Nom");
                TableColumn resColumn = new TableColumn<>("Nb de réservation");
                nameColum.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                resColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
                resColumn.setCellValueFactory(new PropertyValueFactory<>("nbRes"));

                table.getColumns().addAll(nameColum, resColumn);
                table.setItems(data);

                dao_spots.close();
                break;

            case 1: // emplacements les moins réservés

                dao_spots = new GenericDAOImpl<>(Spot.class);
                dao_spots.open();

                spots = (ArrayList<Spot>) dao_spots.findAll();
                spots.sort(new Comparator<Spot>() {
                    @Override
                    public int compare(Spot o1, Spot o2) {
                        return o1.getReservations().size() - o2.getReservations().size();
                    }
                });

                pieChartData = FXCollections.observableArrayList();
                for (Spot r : spots.subList(0, (spots.size() > 8 ? 8 : spots.size()))) {
                    pieChartData.add(new PieChart.Data(r.getName(), (1.0 / (float) r.getReservations().size()) * 100.0));
                }
                if (spots.size() > 7) {
                    int sum = 0;
                    for (Spot c : spots.subList(7, spots.size())) {
                        sum += c.getReservations().size();
                    }
                    sum = sum / (spots.subList(7, spots.size())).size() + 1;
                    pieChartData.add(new PieChart.Data("Others", sum));
                }

                chart = new PieChart(pieChartData);
                chart.setLegendSide(Side.RIGHT);
                chart.setPrefSize(HomeView.TAB_CONTENT_W / 2, HomeView.TAB_CONTENT_H * 15 / 20);
                chart.getStylesheets().add(new File("res/style.css").toURI().toString());
                chart.getStyleClass().add("pie-chart");
                chart.setTitle("Emplacements les moins réservés\n\t\t(1 / nb de res)");

                data = FXCollections.observableArrayList();
                for (Spot s : spots) {
                    data.add(new SpotV(s.getName(), s.getReservations().size()));
                }

                nameColum = new TableColumn<>("Nom");
                resColumn = new TableColumn<>("Nb de réservation");
                nameColum.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                resColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
                resColumn.setCellValueFactory(new PropertyValueFactory<>("nbRes"));

                table.getColumns().addAll(nameColum, resColumn);
                table.setItems(data);

                dao_spots.close();

                break;

            case 2: // reservations impayées

                break;
        }

        return new ChartView(chart, table);
    }

    private ChartView clientChart(@NamedArg("selectedChart") int selectedChart,
                                  @NamedArg("type") ChartType type)
    {
        Chart chart = new PieChart();
        TableView<ClientV> table = new TableView<>();

        switch (type) {
            case PIE:
                break;
            case LINE:
                break;
            case BAR:
                break;
        }

        switch (selectedChart) {
            case 0:
                GenericDAOImpl<Client, Integer> client_dao = new GenericDAOImpl<>(Client.class);
                client_dao.open();

                ArrayList<Client> clients = (ArrayList<Client>) client_dao.findAll();
                clients.sort(new Comparator<Client>() {
                    @Override
                    public int compare(Client o1, Client o2) {
                        return o2.getReservations().size() - o1.getReservations().size();
                    }
                });

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // données observables
                for (Client r : clients.subList(0, (clients.size() > 7 ? 7 : clients.size()))) {
                    pieChartData.add(new PieChart.Data(r.getFirstname() + " " + r.getLastname(), r.getReservations().size()));
                }
                if (clients.size() > 7) {
                    int sum = 0;
                    for (Client c : clients.subList(7, clients.size())) {
                        sum += c.getReservations().size();
                    }
                    sum = sum / (clients.subList(7, clients.size())).size() + 1;
                    pieChartData.add(new PieChart.Data("Others", sum));
                }

                chart = new PieChart(pieChartData);
                chart.setLegendSide(Side.RIGHT);
                chart.setPrefSize(HomeView.TAB_CONTENT_W / 2, HomeView.TAB_CONTENT_H * 15 / 20);
                chart.getStylesheets().add(new File("res/style.css").toURI().toString());
                chart.getStyleClass().add("pie-chart");
                chart.setTitle("Clients réservant le plus");
                chart.setLegendSide(Side.BOTTOM);

                ObservableList<ClientV> data = FXCollections.observableArrayList();
                for (Client c : clients) {
                    data.add(new ClientV(c.getFirstname() + " "+  c.getLastname(), c.getPhone(), c.getReservations().size()));
                }

                TableColumn nameColumn = new TableColumn<>("Nom");
                TableColumn phoneColumn = new TableColumn<>("Téléphone");
                TableColumn resColumn = new TableColumn<>("Nb de réservation");
                nameColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
                phoneColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 7);
                resColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 10);
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
                resColumn.setCellValueFactory(new PropertyValueFactory<>("nbRes"));

                table.getColumns().addAll(nameColumn, phoneColumn, resColumn);
                table.setItems(data);

                client_dao.close();
                break;

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;
        }

        return new ChartView(chart, table);
    }

    public static class SpotV {
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty nbRes;

        private SpotV(String name, Integer nbRes) {
            this.name = new SimpleStringProperty(name);
            this.nbRes = new SimpleIntegerProperty(nbRes);
        }


        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public int getNbRes() {
            return nbRes.get();
        }

        public SimpleIntegerProperty nbResProperty() {
            return nbRes;
        }
    }

    public static class ClientV {

        private final SimpleStringProperty name;
        private final SimpleStringProperty phone;
        private final SimpleIntegerProperty nbRes;

        private ClientV(String name, String phone, Integer nbRes) {
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.nbRes = new SimpleIntegerProperty(nbRes);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public String getPhone() {
            return phone.get();
        }

        public SimpleStringProperty phoneProperty() {
            return phone;
        }

        public int getNbRes() {
            return nbRes.get();
        }

        public SimpleIntegerProperty nbResProperty() {
            return nbRes;
        }
    }
}


