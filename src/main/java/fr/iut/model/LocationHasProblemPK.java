package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sydpy on 2/15/17.
 */
public class LocationHasProblemPK implements Serializable {
    private int locationId;
    private int problemId;

    @Column(name = "Location_id", nullable = false)
    @Id
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Column(name = "Problem_id", nullable = false)
    @Id
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationHasProblemPK that = (LocationHasProblemPK) o;

        if (locationId != that.locationId) return false;
        if (problemId != that.problemId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + problemId;
        return result;
    }
}
