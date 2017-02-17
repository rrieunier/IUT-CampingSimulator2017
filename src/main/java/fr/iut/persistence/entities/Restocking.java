package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "RESTOCKING")
public class Restocking {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Supplier supplier;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public Product getProduct() {
        return product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
