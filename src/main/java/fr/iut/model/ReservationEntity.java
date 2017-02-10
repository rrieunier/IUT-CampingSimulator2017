package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Reservation", schema = "CampingSimulator", catalog = "")
public class ReservationEntity {
    private int idClient;
    private int idSpot;
    private int idReservation;
    private String clientComment;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp reservationDate;
    private int personCount;
    private ClientEntity clientByIdClient;
    private SpotEntity spotByIdSpot;

    @Basic
    @Column(name = "idClient", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "idSpot", nullable = false)
    public int getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(int idSpot) {
        this.idSpot = idSpot;
    }

    @Id
    @Column(name = "idReservation", nullable = false)
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Basic
    @Column(name = "client_comment", nullable = true, length = 250)
    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    @Basic
    @Column(name = "starttime", nullable = false)
    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "endtime", nullable = false)
    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "reservation_date", nullable = false)
    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Basic
    @Column(name = "person_count", nullable = false)
    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntity that = (ReservationEntity) o;

        if (idClient != that.idClient) return false;
        if (idSpot != that.idSpot) return false;
        if (idReservation != that.idReservation) return false;
        if (personCount != that.personCount) return false;
        if (clientComment != null ? !clientComment.equals(that.clientComment) : that.clientComment != null)
            return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (reservationDate != null ? !reservationDate.equals(that.reservationDate) : that.reservationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + idSpot;
        result = 31 * result + idReservation;
        result = 31 * result + (clientComment != null ? clientComment.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        result = 31 * result + personCount;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idClient", referencedColumnName = "idClient", nullable = false)
    public ClientEntity getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientEntity clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "idSpot", referencedColumnName = "idSpot", nullable = false)
    public SpotEntity getSpotByIdSpot() {
        return spotByIdSpot;
    }

    public void setSpotByIdSpot(SpotEntity spotByIdSpot) {
        this.spotByIdSpot = spotByIdSpot;
    }
}
