package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Purchase extends EntityModel<Integer> {

    /**
     * Purchase's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Purchase's date and time.
     */
    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    /**
     * Purchase's product quantity.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Client who made this purchase.
     */
    @ManyToOne(optional = false)
    private Client client;

    /**
     * Product which was purchased.
     */
    @ManyToOne(optional = false)
    private Product product;

    public Integer getId() {
        return id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public int getQuantity() {
        return quantity;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
