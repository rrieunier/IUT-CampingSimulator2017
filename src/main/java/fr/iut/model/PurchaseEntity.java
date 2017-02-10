package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Purchase", schema = "CampingSimulator", catalog = "")
public class PurchaseEntity {
    private int idPurchase;
    private Timestamp datetime;
    private int quantity;
    private int idProduct;
    private int idClient;
    private ProductEntity productByIdProduct;
    private ClientEntity clientByIdClient;

    @Id
    @Column(name = "idPurchase", nullable = false)
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
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
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "idProduct", nullable = false)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Basic
    @Column(name = "idClient", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseEntity that = (PurchaseEntity) o;

        if (idPurchase != that.idPurchase) return false;
        if (quantity != that.quantity) return false;
        if (idProduct != that.idProduct) return false;
        if (idClient != that.idClient) return false;
        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPurchase;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + idProduct;
        result = 31 * result + idClient;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct", nullable = false)
    public ProductEntity getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(ProductEntity productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }

    @ManyToOne
    @JoinColumn(name = "idClient", referencedColumnName = "idClient", nullable = false)
    public ClientEntity getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientEntity clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }
}
