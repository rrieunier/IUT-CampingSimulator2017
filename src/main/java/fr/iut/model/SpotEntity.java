package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/12/17.
 */
@Entity
@Table(name = "Spot", schema = "CampingSimulator", catalog = "")
public class SpotEntity {
    private double pricePerDay;
    private int capacity;
    private byte water;
    private byte electricity;
    private byte shadow;
    private int id;
    private int spotTypeId;
    private LocationEntity location;

    @Basic
    @Column(name = "price_per_day", nullable = false, precision = 0)
    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Basic
    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "water", nullable = false)
    public byte getWater() {
        return water;
    }

    public void setWater(byte water) {
        this.water = water;
    }

    @Basic
    @Column(name = "electricity", nullable = false)
    public byte getElectricity() {
        return electricity;
    }

    public void setElectricity(byte electricity) {
        this.electricity = electricity;
    }

    @Basic
    @Column(name = "shadow", nullable = false)
    public byte getShadow() {
        return shadow;
    }

    public void setShadow(byte shadow) {
        this.shadow = shadow;
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
    @Column(name = "SpotType_id", nullable = false)
    public int getSpotTypeId() {
        return spotTypeId;
    }

    public void setSpotTypeId(int spotTypeId) {
        this.spotTypeId = spotTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotEntity that = (SpotEntity) o;

        if (Double.compare(that.pricePerDay, pricePerDay) != 0) return false;
        if (capacity != that.capacity) return false;
        if (water != that.water) return false;
        if (electricity != that.electricity) return false;
        if (shadow != that.shadow) return false;
        if (id != that.id) return false;
        if (spotTypeId != that.spotTypeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(pricePerDay);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + (int) water;
        result = 31 * result + (int) electricity;
        result = 31 * result + (int) shadow;
        result = 31 * result + id;
        result = 31 * result + spotTypeId;
        return result;
    }

    @OneToOne
    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
