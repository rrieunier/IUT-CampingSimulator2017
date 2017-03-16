package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Reservation extends EntityModel<Integer> {

    /**
     * Reservation's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;


    /**
     * Reservation's reduction to apply.
     */
    @Column(nullable = false)
    private float reduction = 0.f;

    /**
     * Reservation's start date and time.
     */
    @Column(nullable = false)
    private Timestamp starttime;

    /**
     * Reservation's end date and time.
     */
    @Column(nullable = false)
    private Timestamp endtime;

    /**
     * Date and time the reservation was made.
     */
    @Column(nullable = false)
    private Timestamp reservationDate = new Timestamp(System.currentTimeMillis());

    /**
     * Number of persons on the reservation.
     */
    @Column(nullable = false)
    private int personCount;

    /**
     * Client comment on this reservation.
     */
    @Column
    private String clientComment;

    /**
     * Client who made this reservation.
     */
    @ManyToOne(optional = false)
    private Client client;

    /**
     * Spot reserved.
     */
    @ManyToOne(optional = false)
    private Spot spot;

    public Integer getId() {
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

    public float getReduction() {
        return reduction;
    }

    public void setId(Integer id) {
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

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }
}
