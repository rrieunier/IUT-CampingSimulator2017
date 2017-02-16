package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id @GeneratedValue
    @Column(nullable = false)
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
}
