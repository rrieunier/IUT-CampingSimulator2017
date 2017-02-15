package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Location {
    private String name;
    private Double pointX;
    private Double pointY;
    private int id;
    private Spot spotById;
    private Collection<Task> tasksById;

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

        Location location = (Location) o;

        if (id != location.id) return false;
        if (name != null ? !name.equals(location.name) : location.name != null) return false;
        if (pointX != null ? !pointX.equals(location.pointX) : location.pointX != null) return false;
        if (pointY != null ? !pointY.equals(location.pointY) : location.pointY != null) return false;

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

    @OneToOne(mappedBy = "locationById")
    public Spot getSpotById() {
        return spotById;
    }

    public void setSpotById(Spot spotById) {
        this.spotById = spotById;
    }

    @OneToMany(mappedBy = "locationByLocationId")
    public Collection<Task> getTasksById() {
        return tasksById;
    }

    public void setTasksById(Collection<Task> tasksById) {
        this.tasksById = tasksById;
    }
}
