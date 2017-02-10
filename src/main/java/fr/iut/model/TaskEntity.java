package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Task", schema = "CampingSimulator", catalog = "")
public class TaskEntity {
    private int idTask;
    private Timestamp starttime;
    private Timestamp endtime;
    private String label;
    private int idEmployee;
    private Integer idLocation;
    private EmployeeEntity employeeByIdEmployee;
    private LocationEntity locationByIdLocation;

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
    @Column(name = "idEmployee", nullable = false)
    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Basic
    @Column(name = "idLocation", nullable = true)
    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        if (idTask != that.idTask) return false;
        if (idEmployee != that.idEmployee) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (idLocation != null ? !idLocation.equals(that.idLocation) : that.idLocation != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTask;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + idEmployee;
        result = 31 * result + (idLocation != null ? idLocation.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idEmployee", referencedColumnName = "idEmployee", nullable = false)
    public EmployeeEntity getEmployeeByIdEmployee() {
        return employeeByIdEmployee;
    }

    public void setEmployeeByIdEmployee(EmployeeEntity employeeByIdEmployee) {
        this.employeeByIdEmployee = employeeByIdEmployee;
    }

    @ManyToOne
    @JoinColumn(name = "idLocation", referencedColumnName = "idLocation")
    public LocationEntity getLocationByIdLocation() {
        return locationByIdLocation;
    }

    public void setLocationByIdLocation(LocationEntity locationByIdLocation) {
        this.locationByIdLocation = locationByIdLocation;
    }
}
