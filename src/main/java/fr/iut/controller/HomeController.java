package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Product;
import fr.iut.view.HomeView;
import fr.iut.view.InputsListDialog;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


public class HomeController implements ControllerInterface {

    private App app;
    private HomeView homeView;
    private NotificationsController notificationsController = new NotificationsController(this);
    private ClientsController clientsController = new ClientsController(this);
    private StatisticsController statisticsController = new StatisticsController(this);

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    public ArrayList<Product> getProductsList() {
        GenericDAOImpl<Product, Integer> dao = new GenericDAOImpl<Product, Integer>(Product.class);

        dao.open();
        ArrayList<Product> products = (ArrayList<Product>) dao.findAll();
        dao.close();

        return (ArrayList<Product>) products;
    }

    public void addNewProduct(){
        InputsListDialog newProductDialog = new InputsListDialog("Nouveau Produit");
        newProductDialog.addTextField("Nom");
        newProductDialog.addTextField("Quantité en stock");
        newProductDialog.addTextField("Prix (0.0)");
        newProductDialog.addTextField("Quantité limite");
        Optional<Map<String, String>> newProduct_result = newProductDialog.showAndWait();

        Product product = new Product();
        //product.setId(243);
        product.setName(newProduct_result.get().get("Nom"));
        product.setStock(Integer.parseInt(newProduct_result.get().get("Quantité en stock")));
        product.setCriticalQuantity(Integer.parseInt(newProduct_result.get().get("Quantité limite")));
        product.setSellPrice(Float.parseFloat(newProduct_result.get().get("Prix (0.0)")));


        GenericDAOImpl<Product, Integer> dao = new GenericDAOImpl<>(Product.class);
        dao.open();
        dao.persist(product);
        dao.close();
    }

    @Override
    public Scene getView() {
        return homeView;
    }

    @Override
    public void finish() {
        app.switchState(State.CONNECTION);
    }

    public NotificationsController getNotificationsController() {
        return notificationsController;
    }

    public StatisticsController getStatiscticsController() {
        return statisticsController;
    }

    public ClientsController getClientsController() {
        return clientsController;
    }

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
