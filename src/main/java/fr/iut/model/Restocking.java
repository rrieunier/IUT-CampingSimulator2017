package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Restocking {
    private int quantity;
    private Timestamp datetime;
    private int id;
    private int supplierId;
    private int productId;
    private Supplier supplierBySupplierId;
    private Product productByProductId;

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "datetime", nullable = false)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
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
    @Column(name = "Supplier_id", nullable = false)
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "Product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restocking that = (Restocking) o;

        if (quantity != that.quantity) return false;
        if (id != that.id) return false;
        if (supplierId != that.supplierId) return false;
        if (productId != that.productId) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quantity;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + supplierId;
        result = 31 * result + productId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Supplier_id", referencedColumnName = "id", nullable = false)
    public Supplier getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(Supplier supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @ManyToOne
    @JoinColumn(name = "Product_id", referencedColumnName = "id", nullable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }
}
