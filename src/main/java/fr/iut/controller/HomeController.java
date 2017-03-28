package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.entities.Product;
import fr.iut.view.HomeView;
import javafx.scene.Scene;


public class HomeController {

    /**
     * instance of the application
     */
    private App app;
    /**
     * view of the application mainView
     */

    private ClientsController clientsController = new ClientsController(this);
    private NotificationsController notificationsController = new NotificationsController(this);
    private StatisticsController statisticsController = new StatisticsController();
    private IncidentsController incidentsController = new IncidentsController(this);
    private MapController mapController = new MapController(this);
    private EmployeesController employeesController = new EmployeesController(this);
    private SupplierController supplierController = new SupplierController(this);
    private ReservationsController reservationsController = new ReservationsController(this);
    private ProductController productController = new ProductController(this);

    private Employee connectedEmployee;
    private HomeView homeView;

    public HomeController(App app, Employee connectedEmployee) {
        this.app = app;
        this.connectedEmployee = connectedEmployee;
    }

    public Scene getView() {
        return (homeView = new HomeView(this, connectedEmployee));
    }

    /**
     * This method creates a link between the reservations button in the clients submenu on the first tab and the reservations on the 2nd tab
     * @param currentClient is the client of who we want to see the reservations
     */
    public void displayReservations(Client currentClient) {
        homeView.goToMapTab();
        homeView.searchReservationsByClientId(currentClient.getId());
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

    public ReservationsController getReservationsController() {
        return reservationsController;
    }

    public ProductController getProductController() { return productController; }

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
