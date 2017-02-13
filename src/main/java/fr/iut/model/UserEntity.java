package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
@Table(name = "User", schema = "CampingSimulator", catalog = "")
public class UserEntity {
    private String login;
    private String password;
    private int id;
    private Integer employeeId;
    private LogEntity log;
    private EmployeeEntity employee;
    private Collection<UserHasAuthorizationEntity> userHasAuthorizations;

    @Basic
    @Column(name = "login", nullable = false, length = 20)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Employee_id", nullable = true)
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "login", referencedColumnName = "user", nullable = false)
    public LogEntity getLog() {
        return log;
    }

    public void setLog(LogEntity log) {
        this.log = log;
    }

    @ManyToOne
    @JoinColumn(name = "Employee_id", referencedColumnName = "id")
    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "user")
    public Collection<UserHasAuthorizationEntity> getUserHasAuthorizations() {
        return userHasAuthorizations;
    }

    public void setUserHasAuthorizations(Collection<UserHasAuthorizationEntity> userHasAuthorizations) {
        this.userHasAuthorizations = userHasAuthorizations;
    }
}
