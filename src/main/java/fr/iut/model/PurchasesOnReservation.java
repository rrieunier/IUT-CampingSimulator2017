package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class PurchasesOnReservation {
    private int reservationId;
    private int purchaseId;

    @Basic
    @Column(name = "Reservation_id", nullable = false)
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    @Basic
    @Column(name = "Purchase_id", nullable = false)
    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasesOnReservation that = (PurchasesOnReservation) o;

        if (reservationId != that.reservationId) return false;
        if (purchaseId != that.purchaseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + purchaseId;
        return result;
    }
}
