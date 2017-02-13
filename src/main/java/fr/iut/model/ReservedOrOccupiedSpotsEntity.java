package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "ReservedOrOccupiedSpots", schema = "CampingSimulator", catalog = "")
public class ReservedOrOccupiedSpotsEntity {
    private int id;

    @Basic
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

        ReservedOrOccupiedSpotsEntity that = (ReservedOrOccupiedSpotsEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
