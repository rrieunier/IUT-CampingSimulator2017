package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Product implements EntityModel<Integer> {

    /**
     * Product's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

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
    @OneToMany(orphanRemoval = true, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Purchase> purchases;

    /**
     * Restocking made for this product.
     */
    @OneToMany(orphanRemoval = true, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Restocking> restockings;

    /**
     * Relations between suppliers and this product.
     */
    @OneToMany(orphanRemoval = true, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<SupplierProposeProduct> supplierProposeProducts = new HashSet<>();

    public Integer getId() {
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

    public Integer getSumPurchases() {
        Integer ret = 0;
        for (Purchase p : purchases)
            ret += p.getQuantity();

        return ret;
    }

    public Set<Restocking> getRestockings() {
        return restockings;
    }

    public Set<SupplierProposeProduct> getSupplierProposeProducts() {
        return supplierProposeProducts;
    }

    public void setId(Integer id) {
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
