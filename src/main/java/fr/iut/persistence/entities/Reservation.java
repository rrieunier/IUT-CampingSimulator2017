package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(nullable = false)
    private Timestamp starttime;

    @Column(nullable = false)
    private Timestamp endtime;

    @Column(nullable = false)
    private Timestamp reservationDate = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private int personCount;

    @Column
    private String clientComment;

    @ManyToOne(optional = false)
    private Client client;

    @ManyToOne(optional = false)
    private Spot spot;

    public int getId() {
        return id;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public int getPersonCount() {
        return personCount;
    }

    public String getClientComment() {
        return clientComment;
    }

    public Client getClient() {
        return client;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}
