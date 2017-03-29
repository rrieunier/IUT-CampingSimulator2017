package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Product;
import fr.iut.persistence.entities.Supplier;
import fr.iut.persistence.entities.SupplierProposeProduct;
import fr.iut.view.SupplierManagerView;
import javafx.scene.SubScene;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by theo on 16/03/17.
 */
public class SupplierController {

    private HomeController homeController;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private GenericDAO<Supplier, Integer> daoSuppliers = new GenericDAO<>(Supplier.class);
    private GenericDAO<SupplierProposeProduct, Integer> daoProposeProduct = new GenericDAO<>(SupplierProposeProduct.class);

    public SupplierController(HomeController homeController){ this.homeController = homeController;}

    public SubScene getView() {
        return new SupplierManagerView(this);
    }

    public void createSuppliers() { suppliers = (ArrayList<Supplier>) daoSuppliers.findAll();}

    public void updateSupplier(Supplier s, String name, String phone, String web, String mail){
        s.setName(name);
        s.setPhone(phone);
        s.setWebsite(web);
        s.setEmail(mail);

        daoSuppliers.update(s);
    }

    public void saveSupplier(Supplier supplier){
        daoSuppliers.save(supplier);
    }

    public void removeSupplier(Supplier supplier) { daoSuppliers.remove(supplier); }

    public void saveOrUpdateProductsSupplier(Supplier supplier, ArrayList<Pair<Product, Float>> products, boolean update){
        for (Pair<Product, Float> p : products) {
            SupplierProposeProduct supplierProposeProduct = new SupplierProposeProduct();
            supplierProposeProduct.setProduct(p.getKey());
            supplierProposeProduct.setSellPrice(p.getValue());
            supplierProposeProduct.setSupplier(supplier);
            if(!update)
                daoProposeProduct.save(supplierProposeProduct);
            else
                daoProposeProduct.update(supplierProposeProduct);
        }
    }

    public void cleanSupplierProposeProduct(Supplier supplier){
        List<SupplierProposeProduct> list = daoProposeProduct.findAll();
        for (SupplierProposeProduct prod :
                list) {
            if(prod.getSupplier().getId() == supplier.getId())
                daoProposeProduct.remove(prod);
        }
    }

    public ArrayList<SupplierProposeProduct> getProductsProposeBySupplier(Supplier supplier){
        ArrayList<SupplierProposeProduct> productsPropose = new ArrayList<>();
        List<SupplierProposeProduct> list = daoProposeProduct.findAll();
        for (SupplierProposeProduct prod :
                list) {
            if(prod.getSupplier().getId() == supplier.getId()){
                productsPropose.add(prod);
            }
        }
        return productsPropose;
    }

    /**
     * @param supplier
     * @throws IOException
     * browse "mailto:email@supplier.com" to send a mail to the supplier
     */
    public void sendMailToSupplier(Supplier supplier) throws IOException {
        String supplierEmail = supplier.getEmail();
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) { // windows
            Runtime rt = Runtime.getRuntime();
            String url = "mailto:" + supplierEmail;
            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (os.contains("mac")) { // macos
            Runtime rt = Runtime.getRuntime();
            String url = "mailto:" + supplierEmail;
            rt.exec("open" + url);
        } else { // linux
            Runtime rt = Runtime.getRuntime();
            String url = "mailto:" + supplierEmail;
            String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};

            StringBuffer cmd = new StringBuffer();
            for (int i = 0; i < browsers.length; i++)
                cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");

            rt.exec(new String[]{"sh", "-c", cmd.toString()});
        }
    }

    public void sortSuppliers(int sort_options){
        suppliers.sort(new Comparator<Supplier>() {
            @Override
            public int compare(Supplier o1, Supplier o2) {
                double result = 0;
                switch (sort_options){
                    case 1:
                        result = o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
                        break;
                    default:
                        result = o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                        break;
                }
                return (int) result;
            }
        });
    }

    public ArrayList<Supplier> getSuppliers() { return suppliers; }
}
