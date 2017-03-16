package fr.iut.persistence.entities;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.exception.EmployeeAlreadyAssigned;
import fr.iut.persistence.exception.StartAfterEndException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Task extends EntityModel<Integer> {

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
    private Employee employee;

    /**
     * Location on which the task is planned.
     */
    @ManyToOne
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

    @PrePersist
    @PreUpdate
    void prePersistUpdate() throws StartAfterEndException, EmployeeAlreadyAssigned {
        if (starttime.getTime() > endtime.getTime()) {
            throw new StartAfterEndException();
        }

        GenericDAO<Task, Integer> dao = new GenericDAO<>(Task.class);

        List<Task> all = dao.findAll();

        boolean isEmployeeAlreadyAssigned = false;

        for (Task task : all) {
            if(task.getEmployee().getId() == getEmployee().getId()){
                isEmployeeAlreadyAssigned = true;
                break;
            }
        }

        if(isEmployeeAlreadyAssigned) {
            throw new EmployeeAlreadyAssigned();
        }
    }
}
