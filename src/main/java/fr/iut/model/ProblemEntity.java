package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Problem", schema = "CampingSimulator", catalog = "")
public class ProblemEntity {
    private int idProblem;
    private Timestamp appearanceDatetime;
    private String label;
    private Timestamp solutionDatetime;
    private String state;
    private Collection<ClientHasProblemEntity> clientHasProblemsByIdProblem;
    private Collection<LocationHasProblemEntity> locationHasProblemsByIdProblem;

    @Id
    @Column(name = "idProblem", nullable = false)
    public int getIdProblem() {
        return idProblem;
    }

    public void setIdProblem(int idProblem) {
        this.idProblem = idProblem;
    }

    @Basic
    @Column(name = "appearance_datetime", nullable = false)
    public Timestamp getAppearanceDatetime() {
        return appearanceDatetime;
    }

    public void setAppearanceDatetime(Timestamp appearanceDatetime) {
        this.appearanceDatetime = appearanceDatetime;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 45)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "solution_datetime", nullable = true)
    public Timestamp getSolutionDatetime() {
        return solutionDatetime;
    }

    public void setSolutionDatetime(Timestamp solutionDatetime) {
        this.solutionDatetime = solutionDatetime;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 45)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProblemEntity that = (ProblemEntity) o;

        if (idProblem != that.idProblem) return false;
        if (appearanceDatetime != null ? !appearanceDatetime.equals(that.appearanceDatetime) : that.appearanceDatetime != null)
            return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (solutionDatetime != null ? !solutionDatetime.equals(that.solutionDatetime) : that.solutionDatetime != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProblem;
        result = 31 * result + (appearanceDatetime != null ? appearanceDatetime.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (solutionDatetime != null ? solutionDatetime.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "problemByIdProblem")
    public Collection<ClientHasProblemEntity> getClientHasProblemsByIdProblem() {
        return clientHasProblemsByIdProblem;
    }

    public void setClientHasProblemsByIdProblem(Collection<ClientHasProblemEntity> clientHasProblemsByIdProblem) {
        this.clientHasProblemsByIdProblem = clientHasProblemsByIdProblem;
    }

    @OneToMany(mappedBy = "problemByIdProblem")
    public Collection<LocationHasProblemEntity> getLocationHasProblemsByIdProblem() {
        return locationHasProblemsByIdProblem;
    }

    public void setLocationHasProblemsByIdProblem(Collection<LocationHasProblemEntity> locationHasProblemsByIdProblem) {
        this.locationHasProblemsByIdProblem = locationHasProblemsByIdProblem;
    }
}
