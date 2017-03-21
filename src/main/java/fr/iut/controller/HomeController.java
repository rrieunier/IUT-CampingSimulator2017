package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Product;
import fr.iut.view.HomeView;
import fr.iut.view.InputsListDialog;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


public class HomeController {

    /**
     * instance of the application
     */
    private App app;
    /**
     * view of the application mainView
     */
    private HomeView homeView;

    private NotificationsController notificationsController = new NotificationsController(this);
    private ClientsController clientsController = new ClientsController(this);
    private StatisticsController statisticsController = new StatisticsController();
    private IncidentsController incidentsController = new IncidentsController(this);
    private MapController mapController = new MapController(this);
    private EmployeesController employeesController = new EmployeesController(this);
    private SupplierController supplierController = new SupplierController(this);
    private ReservationController reservationController = new ReservationController(this);
    private ProductController productController = new ProductController(this);

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    public Scene getView() {
        return homeView;
    }

    /**
     * return to connection screen
     */
    public void finish() {
        app.switchState(State.CONNECTION);
    }

    public NotificationsController getNotificationsController() {
        return notificationsController;
    }

    public StatisticsController getStatiscticsController() { return statisticsController; }

    public ClientsController getClientsController() {
        return clientsController;
    }

    public IncidentsController getIncidentsController() { return incidentsController; }

    public MapController getMapController() { return mapController; }

    public EmployeesController getEmployeesController() { return  employeesController; }

    public SupplierController getSupplierController() { return  supplierController; }

    public ReservationController getReservationController() { return reservationController; }

    public ProductController getProductController() { return productController; }

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
