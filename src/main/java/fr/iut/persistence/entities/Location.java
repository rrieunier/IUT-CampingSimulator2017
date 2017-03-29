package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Location implements EntityModel<Integer> {

    /**
     * Locations's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Location's name.
     */
    @Column
    private String name;

    /**
     * Location's x coordinate on map picture.
     */
    @Column(nullable = false)
    private Double pointX;

    /**
     * Location's y coordinate on map picture.
     */
    @Column(nullable = false)
    private Double pointY;


    /**
     * Tasks planned on this location.
     */
    @OneToMany(orphanRemoval = true, mappedBy = "location", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    /**
     * Problems encountered on this location.
     */
    @ManyToMany(
            targetEntity=Problem.class
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="location_id"),
            inverseJoinColumns=@JoinColumn(name="problem_id")
    )
    private Set<Problem> problems;

    public Integer getId() {
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

    public void setId(Integer id) {
        this.id = id;
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
