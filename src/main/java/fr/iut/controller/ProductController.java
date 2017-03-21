package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Product;
import fr.iut.view.InputsListDialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Created by damnhotuser on 21/03/17.
 */
public class ProductController {

    private HomeController controller;
    private GenericDAO<Product, Integer> dao = new GenericDAO<>(Product.class);

    public ProductController(HomeController homeController) {
        this.controller = homeController;
    }

    /**
     * @return the whole list of products in database
     */
    public ArrayList<Product> getProductsList() {

        return (ArrayList<Product>) dao.findAll();
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


        dao.save(product);
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

        System.out.println(ancientProduct.getId());

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

        dao.update(product);
    }

    public void deleteProduct(Product lastClickedValue) {
        dao.remove(lastClickedValue);
    }
}
