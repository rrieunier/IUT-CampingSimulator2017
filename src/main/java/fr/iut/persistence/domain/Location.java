package fr.iut.persistence.domain;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "LOCATION")
@Inheritance(strategy = InheritanceType.JOINED)
public class Location {

    @Id @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column
    private String name;

    @Column(nullable = false)
    private Double pointX;

    @Column(nullable = false)
    private Double pointY;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPointX() {
        return pointX;
    }

    public Double getPointY() {
        return pointY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPointX(Double pointX) {
        this.pointX = pointX;
    }

    public void setPointY(Double pointY) {
        this.pointY = pointY;
    }
}
