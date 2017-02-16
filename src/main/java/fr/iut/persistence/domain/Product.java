package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
public class Product {
    private int stock;
    private double sellPrice;
    private String label;
    private int id;
    private int criticalQuantity;
    private Collection<Purchase> purchasesById;
    private Collection<Restocking> restockingsById;

    @Basic
    @Column(name = "stock", nullable = false)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "sell_price", nullable = false, precision = 0)
    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "critical_quantity", nullable = false)
    public int getCriticalQuantity() {
        return criticalQuantity;
    }

    public void setCriticalQuantity(int criticalQuantity) {
        this.criticalQuantity = criticalQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (stock != product.stock) return false;
        if (Double.compare(product.sellPrice, sellPrice) != 0) return false;
        if (id != product.id) return false;
        if (criticalQuantity != product.criticalQuantity) return false;
        if (label != null ? !label.equals(product.label) : product.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = stock;
        temp = Double.doubleToLongBits(sellPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + criticalQuantity;
        return result;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<Purchase> getPurchasesById() {
        return purchasesById;
    }

    public void setPurchasesById(Collection<Purchase> purchasesById) {
        this.purchasesById = purchasesById;
    }

    @OneToMany(mappedBy = "productByProductId")
    public Collection<Restocking> getRestockingsById() {
        return restockingsById;
    }

    public void setRestockingsById(Collection<Restocking> restockingsById) {
        this.restockingsById = restockingsById;
    }

}
