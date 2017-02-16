package fr.iut.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "SPOT")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Spot extends Location {

    @Column(nullable = false)
    private float pricePerDay = 10.f;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private boolean water = false;

    @Column(nullable = false)
    private boolean electricity = false;

    @Column(nullable = false)
    private boolean shadow = false;

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
}
