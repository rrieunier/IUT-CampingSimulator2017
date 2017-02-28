package fr.iut.controller;


import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.*;
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
                               @NamedArg("type") ChartType type) {
        switch (category) {
            case RESERVATIONS:
                return reservationChart(selectedChart, type);
            case CLIENTS:
                return clientChart(selectedChart, type);
            case PURCHASES:
                return purchaseChart(selectedChart, type);
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
                                       @NamedArg("type") ChartType type) {
        GenericDAOImpl<Spot, Integer> dao;
        ChartView chartView = null;

        switch (type) {
            case PIE:
                switch (selectedChart) {
                    case 0: // emplacements les plus réservés
                        dao = new GenericDAOImpl<>(Spot.class);
                        dao.open();
                        ArrayList<Spot> spots = (ArrayList<Spot>) dao.findAll();

                        spots.sort(new Comparator<Spot>() {
                            @Override
                            public int compare(Spot o1, Spot o2) {
                                return o2.getReservations().size() - o1.getReservations().size();
                            }
                        });

                        ArrayList<String> objects = new ArrayList<>();
                        ArrayList<Float> comparative = new ArrayList<>();

                        for (Spot s : spots) {
                            objects.add(s.getName());
                            comparative.add((float) s.getReservations().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o2 - o1);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Emplacements les plus réservés", "Nom", "Nombre de rés.");
                        dao.close();
                        break;

                    case 1: // emplacements les moins réservés

                        dao = new GenericDAOImpl<>(Spot.class);
                        dao.open();
                        spots = (ArrayList<Spot>) dao.findAll();

                        spots.sort(new Comparator<Spot>() {
                            @Override
                            public int compare(Spot o1, Spot o2) {
                                return o2.getReservations().size() - o1.getReservations().size();
                            }
                        });

                        objects = new ArrayList<>();
                        comparative = new ArrayList<>();

                        for (Spot s : spots) {
                            objects.add(s.getName());
                            comparative.add((float) s.getReservations().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o1 - o2);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Emplacements les moins réservés\n\t\t(1/nb de res.)", "Nom", "Nombre de rés.");
                        dao.close();
                        break;

                    default:
                        break;
                }
                break;

            case LINE:
                break;

            case BAR:
                break;
        }

        return chartView;
    }

    private ChartView clientChart(@NamedArg("selectedChart") int selectedChart,
                                  @NamedArg("type") ChartType type) {
        GenericDAOImpl<Client, Integer> dao;
        ChartView chartView = null;

        switch (type) {
            case PIE:
                switch (selectedChart) {
                    case 0:
                        dao = new GenericDAOImpl<>(Client.class);
                        dao.open();
                        ArrayList<Client> clients = (ArrayList<Client>) dao.findAll();

                        clients.sort(new Comparator<Client>() {
                            @Override
                            public int compare(Client o1, Client o2) {
                                return o2.getReservations().size() - o1.getReservations().size();
                            }
                        });

                        ArrayList<String> objects = new ArrayList<>();
                        ArrayList<Float> comparative = new ArrayList<>();

                        for (Client c : clients) {
                            objects.add(c.getFirstname() + " " + c.getLastname());
                            comparative.add((float) c.getReservations().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o2 - o1);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Clients les plus fidèles", "Nom", "Nombre de rés.");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();
                        break;

                    case 1:
                        dao = new GenericDAOImpl<>(Client.class);
                        dao.open();
                        clients = (ArrayList<Client>) dao.findAll();

                        clients.sort(new Comparator<Client>() {
                            @Override
                            public int compare(Client o1, Client o2) {
                                return o2.getProblems().size() - o1.getProblems().size();
                            }
                        });

                        objects = new ArrayList<>();
                        comparative = new ArrayList<>();

                        for (Client c : clients) {
                            objects.add(c.getFirstname() + " " + c.getLastname());
                            comparative.add((float) c.getProblems().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o2 - o1);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Clients ayant reportés \nle plus de problèmes", "Nom", "Nombre de prob.");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();

                        break;

                    case 2:
                        dao = new GenericDAOImpl<>(Client.class);
                        dao.open();
                        clients = (ArrayList<Client>) dao.findAll();

                        clients.sort(new Comparator<Client>() {
                            @Override
                            public int compare(Client o1, Client o2) {
                                return o1.getProblems().size() - o2.getProblems().size();
                            }
                        });

                        objects = new ArrayList<>();
                        comparative = new ArrayList<>();

                        for (Client c : clients) {
                            objects.add(c.getFirstname() + " " + c.getLastname());
                            comparative.add((float) c.getProblems().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o1 - o2);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Clients ayant reportés \nle moins de problèmes", "Nom", "Nombre de prob.");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();
                        break;

                    case 3:
                        dao = new GenericDAOImpl<>(Client.class);
                        dao.open();
                        clients = (ArrayList<Client>) dao.findAll();

                        clients.sort(new Comparator<Client>() {
                            @Override
                            public int compare(Client o1, Client o2) {
                                return o2.getPurchases().size() - o1.getPurchases().size();
                            }
                        });

                        objects = new ArrayList<>();
                        comparative = new ArrayList<>();

                        for (Client c : clients) {
                            objects.add(c.getFirstname() + " " + c.getLastname());
                            comparative.add((float) c.getPurchases().size());
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o2 - o1);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Clients ayant acheté le plus", "Nom", "Nombre d'achats");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();
                        break;
                }
                break;

            case LINE:
                break;

            case BAR:
                break;
        }

        return chartView;
    }

    private ChartView purchaseChart(@NamedArg("selectedChart") int selectedChart,
                                    @NamedArg("type") ChartType type) {
        GenericDAOImpl<Product, Integer> dao;
        ChartView chartView = null;

        switch (type) {

            case PIE:
                switch (selectedChart) {
                    case 0: // produit le plus vendu
                        dao = new GenericDAOImpl<>(Product.class);
                        dao.open();
                        ArrayList<Product> products = (ArrayList<Product>) dao.findAll();

                        ArrayList<String> objects = new ArrayList<>();
                        ArrayList<Float> comparative = new ArrayList<>();

                        products.sort(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                int sum1 = 0;
                                int sum2 = 0;
                                for (Purchase p : o1.getPurchases())
                                    sum1 += p.getQuantity();
                                for (Purchase p : o2.getPurchases())
                                    sum2 += p.getQuantity();
                                return sum2 - sum1;
                            }
                        });

                        for (Product c : products) {
                            int sum = 0;
                            objects.add(c.getName());
                            for (Purchase p : c.getPurchases())
                                sum += p.getQuantity();
                            comparative.add((float) sum);
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o2 - o1);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Produits les plus achetés", "Nom", "Nombre de ventes");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();
                        break;

                    case 1: // produits les moins vendus
                        dao = new GenericDAOImpl<>(Product.class);
                        dao.open();
                        products = (ArrayList<Product>) dao.findAll();

                        objects = new ArrayList<>();
                        comparative = new ArrayList<>();

                        products.sort(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                int sum1 = 0;
                                int sum2 = 0;
                                for (Purchase p : o1.getPurchases())
                                    sum1 += p.getQuantity();
                                for (Purchase p : o2.getPurchases())
                                    sum2 += p.getQuantity();
                                return sum1 - sum2;
                            }
                        });

                        for (Product c : products) {
                            int sum = 0;
                            objects.add(c.getName());
                            for (Purchase p : c.getPurchases())
                                sum += p.getQuantity();
                            comparative.add((float) sum);
                        }
                        comparative.sort(new Comparator<Float>() {
                            @Override
                            public int compare(Float o1, Float o2) {
                                return (int) (o1 - o2);
                            }
                        });

                        chartView = new ChartView(ChartType.PIE, objects, comparative,
                                "Produits les moins achetés", "Nom", "Nombre de ventes");
                        chartView.setLegendSide(Side.BOTTOM);
                        dao.close();
                        break;

                    default:
                        break;
                }
                break;

            case LINE:
                break;

            case BAR:
                break;
        }
        return chartView;
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


