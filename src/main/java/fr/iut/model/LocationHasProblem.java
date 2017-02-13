package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "Location_has_Problem", schema = "CampingSimulator", catalog = "")
@IdClass(LocationHasProblemPK.class)
public class LocationHasProblem {
    private int locationId;
    private int problemId;
    private Location locationByLocationId;
    private Problem problemByProblemId;

    @Id
    @Column(name = "Location_id", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Id
    @Column(name = "Problem_id", nullable = false)
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

        LocationHasProblem that = (LocationHasProblem) o;

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

    @ManyToOne
    @JoinColumn(name = "Location_id", referencedColumnName = "id", nullable = false)
    public Location getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(Location locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }

    @ManyToOne
    @JoinColumn(name = "Problem_id", referencedColumnName = "id", nullable = false)
    public Problem getProblemByProblemId() {
        return problemByProblemId;
    }

    public void setProblemByProblemId(Problem problemByProblemId) {
        this.problemByProblemId = problemByProblemId;
    }
}