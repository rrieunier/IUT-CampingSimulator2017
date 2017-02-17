package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "location")
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany(
            targetEntity=Problem.class,
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="location_id"),
            inverseJoinColumns=@JoinColumn(name="problem_id")
    )
    private Set<Problem> problems = new HashSet<>();

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

    public Set<Task> getTasks() {
        return tasks;
    }

    public Set<Problem> getProblems() {
        return problems;
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

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }
}
