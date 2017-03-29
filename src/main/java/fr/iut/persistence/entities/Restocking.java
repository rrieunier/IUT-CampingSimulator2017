package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Restocking implements EntityModel<Integer> {

    /**
     * Restocking's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Quantity restocked.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Restocking date and time.
     */
    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    /**
     * Product restocked.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    /**
     * Supplier who did this restocking.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Supplier supplier;

    public Integer getId() {
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

    public void setId(Integer id) {
        this.id = id;
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
