package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Notification extends EntityModel<Integer> {

    /**
     * Notification's id.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Notification's title.
     */
    @Column(nullable = false, length = 45)
    private String title;

    /**
     * Notification's content.
     */
    @Column(nullable = false)
    private String content;

    /**
     * Employees concerned about this notification.
     */
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "notifications",
            targetEntity = Employee.class
    )
    private Set<Employee> employees = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
