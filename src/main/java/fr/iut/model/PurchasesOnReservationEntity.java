package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "PurchasesOnReservation", schema = "CampingSimulator", catalog = "")
public class PurchasesOnReservationEntity {
    private int idClient;
    private int idReservation;
    private int idSpot;
    private int idPurchase;

    @Basic
    @Column(name = "idClient", nullable = false)
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Basic
    @Column(name = "idReservation", nullable = false)
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Basic
    @Column(name = "idSpot", nullable = false)
    public int getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(int idSpot) {
        this.idSpot = idSpot;
    }

    @Basic
    @Column(name = "idPurchase", nullable = false)
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasesOnReservationEntity that = (PurchasesOnReservationEntity) o;

        if (idClient != that.idClient) return false;
        if (idReservation != that.idReservation) return false;
        if (idSpot != that.idSpot) return false;
        if (idPurchase != that.idPurchase) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + idReservation;
        result = 31 * result + idSpot;
        result = 31 * result + idPurchase;
        return result;
    }
}
