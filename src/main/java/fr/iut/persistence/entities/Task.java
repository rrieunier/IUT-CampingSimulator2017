package fr.iut.persistence.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Task implements EntityModel<Integer> {

    /**
     * Task's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Task's start date and time.
     */
    @Column(nullable = false)
    private Timestamp starttime;

    /**
     * Task's end date and time.
     */
    @Column(nullable = false)
    private Timestamp endtime;

    /**
     * Task's label.
     */
    @Column(nullable = false)
    private String label;

    /**
     * Employee who is attributed to this task.
     */
    @ManyToOne(optional = false)
    @Cascade(CascadeType.ALL)
    private Employee employee;

    /**
     * Location on which the task is planned.
     */
    @ManyToOne(optional = true)
    @Cascade(CascadeType.ALL)
    private Location location;

    public Integer getId() {
        return id;
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

    public Employee getEmployee() {
        return employee;
    }

    public Location getLocation() {
        return location;
    }

    public void setId(Integer idTask) {
        this.id = idTask;
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
