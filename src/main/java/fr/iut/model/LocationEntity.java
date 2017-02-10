package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Location", schema = "CampingSimulator", catalog = "")
public class LocationEntity {
    private int idLocation;
    private String name;
    private double pointX;
    private double pointY;
    private Collection<LocationHasProblemEntity> locationHasProblemsByIdLocation;
    private SpotEntity spotByIdLocation;
    private Collection<TaskEntity> tasksByIdLocation;

    @Id
    @Column(name = "idLocation", nullable = false)
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "point_x", nullable = false, precision = 0)
    public double getPointX() {
        return pointX;
    }

    public void setPointX(double pointX) {
        this.pointX = pointX;
    }

    @Basic
    @Column(name = "point_y", nullable = false, precision = 0)
    public double getPointY() {
        return pointY;
    }

    public void setPointY(double pointY) {
        this.pointY = pointY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (idLocation != that.idLocation) return false;
        if (Double.compare(that.pointX, pointX) != 0) return false;
        if (Double.compare(that.pointY, pointY) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idLocation;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(pointX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pointY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @OneToMany(mappedBy = "locationByIdLocation")
    public Collection<LocationHasProblemEntity> getLocationHasProblemsByIdLocation() {
        return locationHasProblemsByIdLocation;
    }

    public void setLocationHasProblemsByIdLocation(Collection<LocationHasProblemEntity> locationHasProblemsByIdLocation) {
        this.locationHasProblemsByIdLocation = locationHasProblemsByIdLocation;
    }

    @OneToOne(mappedBy = "locationByIdSpot")
    public SpotEntity getSpotByIdLocation() {
        return spotByIdLocation;
    }

    public void setSpotByIdLocation(SpotEntity spotByIdLocation) {
        this.spotByIdLocation = spotByIdLocation;
    }

    @OneToMany(mappedBy = "locationByIdLocation")
    public Collection<TaskEntity> getTasksByIdLocation() {
        return tasksByIdLocation;
    }

    public void setTasksByIdLocation(Collection<TaskEntity> tasksByIdLocation) {
        this.tasksByIdLocation = tasksByIdLocation;
    }
}
