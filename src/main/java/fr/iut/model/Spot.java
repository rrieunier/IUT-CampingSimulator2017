package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Spot {
    private double pricePerDay;
    private int capacity;
    private boolean water;
    private boolean electricity;
    private boolean shadow;
    private int id;
    private int spotTypeId;
    private Collection<Reservation> reservationsById;
    private Location locationById;
    private SpotType spotTypeBySpotTypeId;

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
    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    @Basic
    @Column(name = "electricity", nullable = false)
    public boolean isElectricity() {
        return electricity;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    @Basic
    @Column(name = "shadow", nullable = false)
    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
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

        Spot spot = (Spot) o;

        if (Double.compare(spot.pricePerDay, pricePerDay) != 0) return false;
        if (capacity != spot.capacity) return false;
        if (water != spot.water) return false;
        if (electricity != spot.electricity) return false;
        if (shadow != spot.shadow) return false;
        if (id != spot.id) return false;
        if (spotTypeId != spot.spotTypeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(pricePerDay);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + capacity;
        result = 31 * result + (water ? 1 : 0);
        result = 31 * result + (electricity ? 1 : 0);
        result = 31 * result + (shadow ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + spotTypeId;
        return result;
    }

    @OneToMany(mappedBy = "spotBySpotId")
    public Collection<Reservation> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<Reservation> reservationsById) {
        this.reservationsById = reservationsById;
    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    public Location getLocationById() {
        return locationById;
    }

    public void setLocationById(Location locationById) {
        this.locationById = locationById;
    }

    @ManyToOne
    @JoinColumn(name = "SpotType_id", referencedColumnName = "id", nullable = false)
    public SpotType getSpotTypeBySpotTypeId() {
        return spotTypeBySpotTypeId;
    }

    public void setSpotTypeBySpotTypeId(SpotType spotTypeBySpotTypeId) {
        this.spotTypeBySpotTypeId = spotTypeBySpotTypeId;
    }
}
