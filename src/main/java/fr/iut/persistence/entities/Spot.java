package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "SPOT")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Spot extends Location {

    /**
     * Spot's price per day.
     */
    @Column(nullable = false)
    private float pricePerDay = 10.f;

    /**
     * Spot's person capacity.
     */
    @Column(nullable = false)
    private int capacity;

    /**
     * Does this spot has water.
     */
    @Column(nullable = false)
    private boolean water = false;

    /**
     * Does this spot has electricity.
     */
    @Column(nullable = false)
    private boolean electricity = false;

    /**
     * Does this spot has shadow.
     */
    @Column(nullable = false)
    private boolean shadow = false;

    /**
     * Reservations made to this spot.
     */
    @OneToMany(mappedBy = "spot")
    private Set<Reservation> reservations = new HashSet<>();

    public float getPricePerDay() {
        return pricePerDay;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasWater() {
        return water;
    }

    public boolean hasElectricity() {
        return electricity;
    }

    public boolean hasShadow() {
        return shadow;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
