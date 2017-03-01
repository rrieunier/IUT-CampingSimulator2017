package fr.iut.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "LOG")
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    @ManyToOne(optional = false)
    private Employee employee;

    public int getId() {
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

    public void setId(int id) {
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
