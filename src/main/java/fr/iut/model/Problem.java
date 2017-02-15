package fr.iut.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
public class Problem {
    private Timestamp appearanceDatetime;
    private String label;
    private Timestamp solutionDatetime;
    private String state;
    private int id;

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

        Problem problem = (Problem) o;

        if (id != problem.id) return false;
        if (appearanceDatetime != null ? !appearanceDatetime.equals(problem.appearanceDatetime) : problem.appearanceDatetime != null)
            return false;
        if (label != null ? !label.equals(problem.label) : problem.label != null) return false;
        if (solutionDatetime != null ? !solutionDatetime.equals(problem.solutionDatetime) : problem.solutionDatetime != null)
            return false;
        if (state != null ? !state.equals(problem.state) : problem.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appearanceDatetime != null ? appearanceDatetime.hashCode() : 0;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (solutionDatetime != null ? solutionDatetime.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
