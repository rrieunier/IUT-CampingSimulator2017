package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Supplier;

import java.util.ArrayList;

/**
 * Created by theo on 16/03/17.
 */
public class SupplierController {

    private HomeController homeController;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private GenericDAO<Supplier, Integer> daoSuppliers = new GenericDAO<>(Supplier.class);

    public SupplierController(HomeController homeController){ this.homeController = homeController;}

    public void createSuppliers() { suppliers = (ArrayList<Supplier>) daoSuppliers.findAll();}


    public ArrayList<Supplier> getSuppliers() { return suppliers; }
}
