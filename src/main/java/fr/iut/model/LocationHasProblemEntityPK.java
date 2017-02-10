package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by roman on 10/02/17.
 */
public class LocationHasProblemEntityPK implements Serializable {
    private int idLocation;
    private int idProblem;

    @Column(name = "idLocation", nullable = false)
    @Id
    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    @Column(name = "idProblem", nullable = false)
    @Id
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

        LocationHasProblemEntityPK that = (LocationHasProblemEntityPK) o;

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
}
