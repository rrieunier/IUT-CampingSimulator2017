package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/12/17.
 */
@Entity
@Table(name = "Location", schema = "CampingSimulator", catalog = "")
public class LocationEntity {
    private String name;
    private Double pointX;
    private Double pointY;
    private int id;
    private SpotEntity spot;

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "point_x", nullable = true, precision = 0)
    public Double getPointX() {
        return pointX;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    @Basic
    @Column(name = "point_y", nullable = true, precision = 0)
    public Double getPointY() {
        return pointY;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (pointX != null ? !pointX.equals(that.pointX) : that.pointX != null) return false;
        if (pointY != null ? !pointY.equals(that.pointY) : that.pointY != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (pointX != null ? pointX.hashCode() : 0);
        result = 31 * result + (pointY != null ? pointY.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @OneToOne(mappedBy = "location")
    public SpotEntity getSpot() {
        return spot;
    }

    public void setSpot(SpotEntity spot) {
        this.spot = spot;
    }
}
