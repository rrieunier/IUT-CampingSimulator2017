package fr.iut.persistence.domain;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int stock = 0;

    @Column(nullable = false)
    private int criticalQuantity = 0;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float sellPrice;

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
}
