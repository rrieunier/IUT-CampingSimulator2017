package fr.iut.model;

import javax.persistence.*;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Location_has_Problem", schema = "CampingSimulator", catalog = "")
@IdClass(LocationHasProblemEntityPK.class)
public class LocationHasProblemEntity {
    private int idLocation;
    private int idProblem;
    private LocationEntity locationByIdLocation;
    private ProblemEntity problemByIdProblem;

    @Id
    @Column(name = "idLocation", nullable = false)
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    @Id
    @Column(name = "idProblem", nullable = false)
    public int getIdProblem() {
        return idProblem;
    }

    public void setIdProblem(int idProblem) {
        this.idProblem = idProblem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationHasProblemEntity that = (LocationHasProblemEntity) o;

        if (idLocation != that.idLocation) return false;
        if (idProblem != that.idProblem) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLocation;
        result = 31 * result + idProblem;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idLocation", referencedColumnName = "idLocation", nullable = false)
    public LocationEntity getLocationByIdLocation() {
        return locationByIdLocation;
    }

    public void setLocationByIdLocation(LocationEntity locationByIdLocation) {
        this.locationByIdLocation = locationByIdLocation;
    }

    @ManyToOne
    @JoinColumn(name = "idProblem", referencedColumnName = "idProblem", nullable = false)
    public ProblemEntity getProblemByIdProblem() {
        return problemByIdProblem;
    }

    public void setProblemByIdProblem(ProblemEntity problemByIdProblem) {
        this.problemByIdProblem = problemByIdProblem;
    }
}
