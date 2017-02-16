package fr.iut.persistence.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "LOG")
public class Log {

    @Id @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private Timestamp datetime = new Timestamp(System.currentTimeMillis());

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
