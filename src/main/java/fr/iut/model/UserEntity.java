package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "User", schema = "CampingSimulator", catalog = "")
public class UserEntity {
    private String login;
    private String password;
    private Integer employeeIdEmployee;
    private Collection<LogEntity> logsByLogin;
    private EmployeeEntity employeeByEmployeeIdEmployee;
    private Collection<UserHasAuthorizationEntity> userHasAuthorizationsByLogin;

    @Id
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

    @Basic
    @Column(name = "Employee_idEmployee", nullable = true)
    public Integer getEmployeeIdEmployee() {
        return employeeIdEmployee;
    }

    public void setEmployeeIdEmployee(Integer employeeIdEmployee) {
        this.employeeIdEmployee = employeeIdEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (employeeIdEmployee != null ? !employeeIdEmployee.equals(that.employeeIdEmployee) : that.employeeIdEmployee != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (employeeIdEmployee != null ? employeeIdEmployee.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByUser")
    public Collection<LogEntity> getLogsByLogin() {
        return logsByLogin;
    }

    public void setLogsByLogin(Collection<LogEntity> logsByLogin) {
        this.logsByLogin = logsByLogin;
    }

    @ManyToOne
    @JoinColumn(name = "Employee_idEmployee", referencedColumnName = "idEmployee")
    public EmployeeEntity getEmployeeByEmployeeIdEmployee() {
        return employeeByEmployeeIdEmployee;
    }

    public void setEmployeeByEmployeeIdEmployee(EmployeeEntity employeeByEmployeeIdEmployee) {
        this.employeeByEmployeeIdEmployee = employeeByEmployeeIdEmployee;
    }

    @OneToMany(mappedBy = "userByLogin")
    public Collection<UserHasAuthorizationEntity> getUserHasAuthorizationsByLogin() {
        return userHasAuthorizationsByLogin;
    }

    public void setUserHasAuthorizationsByLogin(Collection<UserHasAuthorizationEntity> userHasAuthorizationsByLogin) {
        this.userHasAuthorizationsByLogin = userHasAuthorizationsByLogin;
    }
}
