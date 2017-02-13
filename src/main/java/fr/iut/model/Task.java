package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Task {
    private int idTask;
    private Timestamp starttime;
    private Timestamp endtime;
    private String label;
    private int employeeId;
    private Integer locationId;
    private Employee employeeByEmployeeId;
    private Location locationByLocationId;

    @Id
    @Column(name = "idTask", nullable = false)
    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Basic
    @Column(name = "starttime", nullable = false)
    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "endtime", nullable = false)
    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "Employee_id", nullable = false)
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "Location_id", nullable = true)
    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (idTask != task.idTask) return false;
        if (employeeId != task.employeeId) return false;
        if (starttime != null ? !starttime.equals(task.starttime) : task.starttime != null) return false;
        if (endtime != null ? !endtime.equals(task.endtime) : task.endtime != null) return false;
        if (label != null ? !label.equals(task.label) : task.label != null) return false;
        if (locationId != null ? !locationId.equals(task.locationId) : task.locationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTask;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + employeeId;
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Employee_id", referencedColumnName = "id", nullable = false)
    public Employee getEmployeeByEmployeeId() {
        return employeeByEmployeeId;
    }

    public void setEmployeeByEmployeeId(Employee employeeByEmployeeId) {
        this.employeeByEmployeeId = employeeByEmployeeId;
    }

    @ManyToOne
    @JoinColumn(name = "Location_id", referencedColumnName = "id")
    public Location getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(Location locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }
}
