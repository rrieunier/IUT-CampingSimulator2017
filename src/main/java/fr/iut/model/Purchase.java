package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Purchase {
    private Timestamp datetime;
    private int quantity;
    private int id;
    private int productId;
    private int clientId;
    private Product productByProductId;
    private Client clientByClientId;

    @Basic
    @Column(name = "datetime", nullable = false)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    @Column(name = "Product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "Client_id", nullable = false)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (quantity != purchase.quantity) return false;
        if (id != purchase.id) return false;
        if (productId != purchase.productId) return false;
        if (clientId != purchase.clientId) return false;
        if (datetime != null ? !datetime.equals(purchase.datetime) : purchase.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = datetime != null ? datetime.hashCode() : 0;
        result = 31 * result + quantity;
        result = 31 * result + id;
        result = 31 * result + productId;
        result = 31 * result + clientId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Product_id", referencedColumnName = "id", nullable = false)
    public Product getProductByProductId() {
        return productByProductId;
    }

    public void setProductByProductId(Product productByProductId) {
        this.productByProductId = productByProductId;
    }

    @ManyToOne
    @JoinColumn(name = "Client_id", referencedColumnName = "id", nullable = false)
    public Client getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(Client clientByClientId) {
        this.clientByClientId = clientByClientId;
    }
}
