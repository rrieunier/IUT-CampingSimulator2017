package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PURCHASE")
public class Purchase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private Product product;

    public int getId() {
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

    public void setId(int id) {
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
