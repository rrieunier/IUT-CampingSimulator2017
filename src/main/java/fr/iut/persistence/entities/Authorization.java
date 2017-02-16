package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "AUTHORIZATION")
public class Authorization {
    @Id
    @Column(nullable = false, length = 45)
    private String label;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "authorizations",
            targetEntity = Employee.class
    )
    private Set<Employee> employees = new HashSet<>();

    public String getLabel() {
        return label;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
