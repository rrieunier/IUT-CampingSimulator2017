package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Reservation", schema = "CampingSimulator", catalog = "")
public class ReservationEntity {
    private String clientComment;
    private Timestamp starttime;
    private Timestamp endtime;
    private Timestamp reservationDate;
    private int personCount;
    private int id;
    private int spotId;
    private int clientId;
    private SpotEntity spot;
    private ClientEntity client;

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

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Spot_id", nullable = false)
    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
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

        ReservationEntity that = (ReservationEntity) o;

        if (personCount != that.personCount) return false;
        if (id != that.id) return false;
        if (spotId != that.spotId) return false;
        if (clientId != that.clientId) return false;
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
        int result = clientComment != null ? clientComment.hashCode() : 0;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (reservationDate != null ? reservationDate.hashCode() : 0);
        result = 31 * result + personCount;
        result = 31 * result + id;
        result = 31 * result + spotId;
        result = 31 * result + clientId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Spot_id", referencedColumnName = "id", nullable = false)
    public SpotEntity getSpot() {
        return spot;
    }

    public void setSpot(SpotEntity spot) {
        this.spot = spot;
    }

    @ManyToOne
    @JoinColumn(name = "Client_id", referencedColumnName = "id", nullable = false)
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
}
