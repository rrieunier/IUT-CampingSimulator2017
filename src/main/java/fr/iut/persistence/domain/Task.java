package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "TASK")
public class Task {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int idTask;

    @Column(nullable = false)
    private Timestamp starttime;

    @Column(nullable = false)
    private Timestamp endtime;

    @Column(nullable = false)
    private String label;

    public int getIdTask() {
        return idTask;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public String getLabel() {
        return label;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
