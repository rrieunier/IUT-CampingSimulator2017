package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Spot", schema = "CampingSimulator", catalog = "")
public class SpotEntity {
    private int idSpot;
    private double pricePerDay;
    private int capacity;
    private byte water;
    private byte electricity;
    private byte shadow;
    private int table1SpotType;
    private Collection<ReservationEntity> reservationsByIdSpot;
    private LocationEntity locationByIdSpot;
    private SpotTypeEntity spotTypeByTable1SpotType;

    @Id
    @Column(name = "idSpot", nullable = false)
    public int getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(int idSpot) {
        this.idSpot = idSpot;
    }

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

    @Basic
    @Column(name = "table1_SpotType", nullable = false)
    public int getTable1SpotType() {
        return table1SpotType;
    }

    public void setTable1SpotType(int table1SpotType) {
        this.table1SpotType = table1SpotType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotEntity that = (SpotEntity) o;

        if (idSpot != that.idSpot) return false;
        if (Double.compare(that.pricePerDay, pricePerDay) != 0) return false;
        if (capacity != that.capacity) return false;
        if (water != that.water) return false;
        if (electricity != that.electricity) return false;
        if (shadow != that.shadow) return false;
        if (table1SpotType != that.table1SpotType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idSpot;
        temp = Double.doubleToLongBits(pricePerDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + (int) water;
        result = 31 * result + (int) electricity;
        result = 31 * result + (int) shadow;
        result = 31 * result + table1SpotType;
        return result;
    }

    @OneToMany(mappedBy = "spotByIdSpot")
    public Collection<ReservationEntity> getReservationsByIdSpot() {
        return reservationsByIdSpot;
    }

    public void setReservationsByIdSpot(Collection<ReservationEntity> reservationsByIdSpot) {
        this.reservationsByIdSpot = reservationsByIdSpot;
    }

    @OneToOne
    @JoinColumn(name = "idSpot", referencedColumnName = "idLocation", nullable = false)
    public LocationEntity getLocationByIdSpot() {
        return locationByIdSpot;
    }

    public void setLocationByIdSpot(LocationEntity locationByIdSpot) {
        this.locationByIdSpot = locationByIdSpot;
    }

    @ManyToOne
    @JoinColumn(name = "table1_SpotType", referencedColumnName = "idSpotType", nullable = false)
    public SpotTypeEntity getSpotTypeByTable1SpotType() {
        return spotTypeByTable1SpotType;
    }

    public void setSpotTypeByTable1SpotType(SpotTypeEntity spotTypeByTable1SpotType) {
        this.spotTypeByTable1SpotType = spotTypeByTable1SpotType;
    }
}
