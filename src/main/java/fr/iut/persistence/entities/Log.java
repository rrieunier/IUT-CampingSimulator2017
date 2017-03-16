package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Log extends EntityModel<Integer> {

    /**
     * Log's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Log's action description.
     */
    @Column(nullable = false)
    private String action;

    /**
     * Log's date and time of action.
     */
    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    /**
     * Employee responsible for this action.
     */
    @ManyToOne(optional = false)
    private Employee employee;

    public Integer getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getAction() {
        return action;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
