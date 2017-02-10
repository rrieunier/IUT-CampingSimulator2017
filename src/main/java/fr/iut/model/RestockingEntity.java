package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Restocking", schema = "CampingSimulator", catalog = "")
public class RestockingEntity {
    private int idRestocking;
    private int quantity;
    private Timestamp datetime;
    private int idSupplier;
    private int idProduct;
    private SupplierEntity supplierByIdSupplier;
    private ProductEntity productByIdProduct;

    @Id
    @Column(name = "idRestocking", nullable = false)
    public int getIdRestocking() {
        return idRestocking;
    }

    public void setIdRestocking(int idRestocking) {
        this.idRestocking = idRestocking;
    }

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

    @Basic
    @Column(name = "idSupplier", nullable = false)
    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    @Basic
    @Column(name = "idProduct", nullable = false)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestockingEntity that = (RestockingEntity) o;

        if (idRestocking != that.idRestocking) return false;
        if (quantity != that.quantity) return false;
        if (idSupplier != that.idSupplier) return false;
        if (idProduct != that.idProduct) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRestocking;
        result = 31 * result + quantity;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + idSupplier;
        result = 31 * result + idProduct;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idSupplier", referencedColumnName = "idSupplier", nullable = false)
    public SupplierEntity getSupplierByIdSupplier() {
        return supplierByIdSupplier;
    }

    public void setSupplierByIdSupplier(SupplierEntity supplierByIdSupplier) {
        this.supplierByIdSupplier = supplierByIdSupplier;
    }

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct", nullable = false)
    public ProductEntity getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(ProductEntity productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }
}
