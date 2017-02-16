package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PURCHASE")
public class Purchase {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private int quantity;

    public int getId() {
        return id;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
