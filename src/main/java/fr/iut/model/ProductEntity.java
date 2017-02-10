package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Product", schema = "CampingSimulator", catalog = "")
public class ProductEntity {
    private int idProduct;
    private int stock;
    private double sellPrice;
    private String label;
    private Collection<PurchaseEntity> purchasesByIdProduct;
    private Collection<RestockingEntity> restockingsByIdProduct;
    private Collection<SupplierHasProductEntity> supplierHasProductsByIdProduct;

    @Id
    @Column(name = "idProduct", nullable = false)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (idProduct != that.idProduct) return false;
        if (stock != that.stock) return false;
        if (Double.compare(that.sellPrice, sellPrice) != 0) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idProduct;
        result = 31 * result + stock;
        temp = Double.doubleToLongBits(sellPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "productByIdProduct")
    public Collection<PurchaseEntity> getPurchasesByIdProduct() {
        return purchasesByIdProduct;
    }

    public void setPurchasesByIdProduct(Collection<PurchaseEntity> purchasesByIdProduct) {
        this.purchasesByIdProduct = purchasesByIdProduct;
    }

    @OneToMany(mappedBy = "productByIdProduct")
    public Collection<RestockingEntity> getRestockingsByIdProduct() {
        return restockingsByIdProduct;
    }

    public void setRestockingsByIdProduct(Collection<RestockingEntity> restockingsByIdProduct) {
        this.restockingsByIdProduct = restockingsByIdProduct;
    }

    @OneToMany(mappedBy = "productByIdProduct")
    public Collection<SupplierHasProductEntity> getSupplierHasProductsByIdProduct() {
        return supplierHasProductsByIdProduct;
    }

    public void setSupplierHasProductsByIdProduct(Collection<SupplierHasProductEntity> supplierHasProductsByIdProduct) {
        this.supplierHasProductsByIdProduct = supplierHasProductsByIdProduct;
    }
}
