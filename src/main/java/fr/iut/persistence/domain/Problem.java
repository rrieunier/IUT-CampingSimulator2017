package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "PROBLEM")
public class Problem {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Timestamp appearanceDatetime;

    @Column
    private Timestamp solutionDatetime;

    @Column(nullable = false, length = 45)
    private String state = "Non corrigé";

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getAppearanceDatetime() {
        return appearanceDatetime;
    }

    public Timestamp getSolutionDatetime() {
        return solutionDatetime;
    }

    public String getState() {
        return state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppearanceDatetime(Timestamp appearanceDatetime) {
        this.appearanceDatetime = appearanceDatetime;
    }

    public void setSolutionDatetime(Timestamp solutionDatetime) {
        this.solutionDatetime = solutionDatetime;
    }

    public void setState(String state) {
        this.state = state;
    }
}
