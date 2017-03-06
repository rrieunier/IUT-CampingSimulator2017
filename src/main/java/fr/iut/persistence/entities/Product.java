package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

    /**
     * Product's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    /**
     * Product's stock.
     */
    @Column(nullable = false)
    private int stock = 0;

    /**
     * Product's critical quantity.
     */
    @Column(nullable = false)
    private int criticalQuantity = 0;

    /**
     * Product's name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Product's sell price.
     */
    @Column(nullable = false)
    private float sellPrice;

    /**
     * Purchases made for this product.
     */
    @OneToMany(mappedBy = "product")
    private Set<Purchase> purchases = new HashSet<>();

    /**
     * Restocking made for this product.
     */
    @OneToMany(mappedBy = "product")
    private Set<Restocking> restockings = new HashSet<>();

    /**
     * Relations between suppliers and this product.
     */
    @OneToMany(mappedBy = "product")
    private Set<SupplierProposeProduct> supplierProposeProducts = new HashSet<>();

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public int getCriticalQuantity() {
        return criticalQuantity;
    }

    public String getName() {
        return name;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public Set<Restocking> getRestockings() {
        return restockings;
    }

    public Set<SupplierProposeProduct> getSupplierProposeProducts() {
        return supplierProposeProducts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCriticalQuantity(int criticalQuantity) {
        this.criticalQuantity = criticalQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setRestockings(Set<Restocking> restockings) {
        this.restockings = restockings;
    }

    public void setSupplierProposeProducts(Set<SupplierProposeProduct> supplierProposeProducts) {
        this.supplierProposeProducts = supplierProposeProducts;
    }
}
