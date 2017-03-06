package fr.iut.controller;


import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.*;
import fr.iut.view.ChartType;
import fr.iut.view.ChartView;
import fr.iut.view.SelectedCategory;
import javafx.beans.NamedArg;
import javafx.geometry.Side;

import java.util.ArrayList;
import java.util.Comparator;

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
                return employeeChart(selectedChart, type);
            case PROBLEMS:
                return problemChart(selectedChart, type);
            case OTHERS:
                return otherChart(selectedChart, type);
            case NONE:
                break;
        }
        return null;
    }


    private ChartView reservationChart(@NamedArg("selectedChart") int selectedChart,
                                       @NamedArg("type") ChartType type) {
        GenericDAO<Spot, Integer> dao;
        ChartView chartView = null;

        switch (selectedChart) {
            case 0: // emplacements les plus réservés
                dao = new GenericDAO<>(Spot.class);
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

                dao = new GenericDAO<>(Spot.class);
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

                chartView = new ChartView(type, objects, comparative,
                        "Emplacements les moins réservés\n\t\t(1/nb de res.)", "Nom", "Nombre de rés.");
                dao.close();
                break;

            default:
                break;
        }

        return chartView;
    }

    private ChartView clientChart(@NamedArg("selectedChart") int selectedChart,
                                  @NamedArg("type") ChartType type) {
        GenericDAO<Client, Integer> dao;
        ChartView chartView = null;

        switch (selectedChart) {
            case 0: // clients les plus fidèles
                dao = new GenericDAO<>(Client.class);
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

            case 1:// clients les plus plaintifs
                dao = new GenericDAO<>(Client.class);
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

            case 2: // clients les moins fidèles
                dao = new GenericDAO<>(Client.class);
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

            case 3:// clients achetant le plus
                dao = new GenericDAO<>(Client.class);
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

                chartView = new ChartView(type, objects, comparative,
                        "Clients ayant acheté le plus", "Nom", "Nombre d'achats");
                chartView.setLegendSide(Side.BOTTOM);
                dao.close();
                break;
        }

        return chartView;
    }

    private ChartView purchaseChart(@NamedArg("selectedChart") int selectedChart,
                                    @NamedArg("type") ChartType type) {
        GenericDAO<Product, Integer> dao;
        ChartView chartView = null;

        switch (selectedChart) {
            case 0: // produit le plus vendu
                dao = new GenericDAO<>(Product.class);
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
                dao = new GenericDAO<>(Product.class);
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

                chartView = new ChartView(type, objects, comparative,
                        "Produits les moins achetés", "Nom", "Nombre de ventes");
                chartView.setLegendSide(Side.BOTTOM);
                dao.close();
                break;

            default:
                break;
        }

        return chartView;
    }

    private ChartView employeeChart(@NamedArg("selectedChart") int selectedChart,
                                    @NamedArg("type") ChartType type) {
        GenericDAO<Employee, Integer> dao;
        ChartView chartView = null;

        switch (selectedChart) {
            case 0:
                dao = new GenericDAO<>(Employee.class);
                dao.open();
                ArrayList<Employee> employees = (ArrayList<Employee>) dao.findAll();

                employees.sort(new Comparator<Employee>() {

                    @Override
                    public int compare(Employee o1, Employee o2) {
                        int sum1 = 0;
                        int sum2 = 1;
                        for (Task t : o1.getTasks()) {
                            sum1 += t.getEndtime().getTime() - t.getStarttime().getTime();
                        }
                        for (Task t : o2.getTasks()) {
                            sum2 += t.getEndtime().getTime() - t.getStarttime().getTime();
                        }
                        return sum2 - sum1;
                    }
                });

                ArrayList<String> objects = new ArrayList<>();
                ArrayList<Float> comparative = new ArrayList<>();

                for (Employee e : employees) {
                    int sum = 0;
                    for (Task t : e.getTasks())
                        sum += t.getEndtime().getTime() - t.getStarttime().getTime();
                    objects.add(e.getFirstName() + " " + e.getLastName());
                    comparative.add((float) ((sum / 60000.0) / 60.0));
                }
                comparative.sort(new Comparator<Float>() {
                    @Override
                    public int compare(Float o1, Float o2) {
                        return (int) (o2 - o1);
                    }
                });

                chartView = new ChartView(type, objects, comparative,
                        "Employés travaillant le plus", "Nom", "Nombre d'heures de travail");
                chartView.setLegendSide(Side.BOTTOM);
                dao.close();
                break;

            default:
                break;
        }
        return chartView;
    }

    private ChartView problemChart(@NamedArg("selectedChart") int selectedChart,
                                   @NamedArg("type") ChartType type) {
        GenericDAO<Client, Integer> dao_client;
        GenericDAO<Location, Integer> dao_location;
        ChartView chartView = null;

        switch (selectedChart) {
            case 0:
                dao_client = new GenericDAO<>(Client.class);
                dao_client.open();
                ArrayList<Client> clients = (ArrayList<Client>) dao_client.findAll();

                clients.sort(new Comparator<Client>() {
                    @Override
                    public int compare(Client o1, Client o2) {
                        return o2.getProblems().size() - o1.getProblems().size();
                    }
                });

                ArrayList<String> objects = new ArrayList<>();
                ArrayList<Float> comparative = new ArrayList<>();

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
                dao_client.close();

                break;

            case 1:
                dao_location = new GenericDAO<>(Location.class);
                dao_location.open();
                ArrayList<Location> locations = (ArrayList<Location>) dao_location.findAll();

                locations.sort(new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        return o2.getProblems().size() - o1.getProblems().size();
                    }
                });

                objects = new ArrayList<>();
                comparative = new ArrayList<>();

                for (Location c : locations) {
                    objects.add(c.getName());
                    comparative.add((float) c.getProblems().size());
                }
                comparative.sort(new Comparator<Float>() {
                    @Override
                    public int compare(Float o1, Float o2) {
                        return (int) (o2 - o1);
                    }
                });

                chartView = new ChartView(ChartType.PIE, objects, comparative,
                        "Lieu ayant le plus de problèmes", "Nom", "Nombre de prob.");
                chartView.setLegendSide(Side.BOTTOM);
                dao_location.close();
                break;
        }
        return chartView;
    }

    private ChartView otherChart(@NamedArg("selectedChart") int selectedChart,
                                   @NamedArg("type") ChartType type) {
        GenericDAO<Employee, Integer> dao = new GenericDAO<>(Employee.class);
        ChartView chartView = null;

        switch (selectedChart) {
            case 0:
                dao.open();
                ArrayList<Employee> employees = (ArrayList<Employee>) dao.findAll();

                employees.sort(new Comparator<Employee>() {
                    @Override
                    public int compare(Employee o1, Employee o2) {
                        return o2.getLogs().size() - o1.getLogs().size();
                    }
                });

                ArrayList<String> objects = new ArrayList<>();
                ArrayList<Float> comparative = new ArrayList<>();

                for (Employee e : employees) {
                    objects.add(e.getFirstName() + " " + e.getLastName());
                    comparative.add((float) e.getLogs().size());
                }
                comparative.sort(new Comparator<Float>() {
                    @Override
                    public int compare(Float o1, Float o2) {
                        return (int) (o2 - o1);
                    }
                });

                chartView = new ChartView(ChartType.PIE, objects, comparative,
                        "Employés se connectant le plus", "Nom", "Nombre de connections");
                chartView.setLegendSide(Side.BOTTOM);
                dao.close();
                break;

            default:
                break;
        }
        return chartView;
    }
}


