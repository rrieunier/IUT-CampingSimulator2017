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


public class HomeController implements ControllerInterface {

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

    public HomeController(App app, String connectedUser) {
        this.app = app;
        homeView = new HomeView(this, connectedUser);
    }

    /**
     * @return the whole list of products in database
     */
    public ArrayList<Product> getProductsList() {
        GenericDAO<Product, Integer> dao = new GenericDAO<Product, Integer>(Product.class);

        ArrayList<Product> products = (ArrayList<Product>) dao.findAll();

        return products;
    }

    /**
     * add a newproduct in the database
     */
    public void addNewProduct(){
        InputsListDialog newProductDialog = new InputsListDialog("Nouveau Produit");
        newProductDialog.addTextField("Nom");
        newProductDialog.addTextField("Quantité en stock");
        newProductDialog.addTextField("Prix (0.0)");
        newProductDialog.addTextField("Quantité limite");
        Optional<Map<String, String>> newProduct_result = newProductDialog.showAndWait();

        Product product = new Product();
        product.setName(newProduct_result.get().get("Nom"));
        product.setStock(Integer.parseInt(newProduct_result.get().get("Quantité en stock")));
        product.setCriticalQuantity(Integer.parseInt(newProduct_result.get().get("Quantité limite")));
        product.setSellPrice(Float.parseFloat(newProduct_result.get().get("Prix (0.0)")));


        GenericDAO<Product, Integer> dao = new GenericDAO<>(Product.class);
        dao.saveOrUpdate(product);
    }

    /**
     * @param ancientProduct
     * update informations of a product
     */
    public void modifyProduct(Product ancientProduct){
        InputsListDialog modifyProductDialog = new InputsListDialog("Modifier le produit");
        modifyProductDialog.addTextField(ancientProduct.getName());
        modifyProductDialog.addTextField(String.valueOf(ancientProduct.getStock()));
        modifyProductDialog.addTextField(String.valueOf(ancientProduct.getSellPrice()));
        modifyProductDialog.addTextField(String.valueOf(ancientProduct.getCriticalQuantity()));
        Optional<Map<String, String>> newProduct_result = modifyProductDialog.showAndWait();

        GenericDAO<Product, Integer> dao = new GenericDAO<Product, Integer>(Product.class);

        Product product = dao.findById(ancientProduct.getId());
        product.setName(newProduct_result.get().get(ancientProduct.getName()).isEmpty()
                ? ancientProduct.getName()
                : newProduct_result.get().get(ancientProduct.getName()));
        product.setStock(newProduct_result.get().get(String.valueOf(ancientProduct.getStock())).isEmpty()
                ? ancientProduct.getStock()
                : Integer.parseInt(newProduct_result.get().get(String.valueOf(ancientProduct.getStock()))));
        product.setCriticalQuantity(newProduct_result.get().get(String.valueOf(ancientProduct.getCriticalQuantity())).isEmpty()
                ? ancientProduct.getCriticalQuantity()
                : Integer.parseInt(newProduct_result.get().get(String.valueOf(ancientProduct.getCriticalQuantity()))));
        product.setSellPrice(newProduct_result.get().get(String.valueOf(ancientProduct.getSellPrice())).isEmpty()
                ? ancientProduct.getSellPrice()
                : Float.parseFloat(newProduct_result.get().get(String.valueOf(ancientProduct.getSellPrice()))));

        dao.saveOrUpdate(product);
    }

    public void deleteProduct(Product lastClickedValue) {
        GenericDAO<Product, Integer> dao = new GenericDAO<Product, Integer>(Product.class);
        dao.open();
        dao.remove(lastClickedValue);
        dao.close();
    }

    @Override
    public Scene getView() {
        return homeView;
    }

    /**
     * return to connection screen
     */
    @Override
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

    public void OnWindowIsClosing() {
        notificationsController.stopQuerying();
    }
}
