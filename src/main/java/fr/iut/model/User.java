package fr.iut.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class User {
    private String login;
    private String password;
    private int id;
    private Integer employeeId;
    private Log logByLogin;
    private Employee employeeByEmployeeId;
    private Collection<UserHasAuthorization> userHasAuthorizationsById;

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

        User user = (User) o;

        if (id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (employeeId != null ? !employeeId.equals(user.employeeId) : user.employeeId != null) return false;

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
    public Log getLogByLogin() {
        return logByLogin;
    }

    public void setLogByLogin(Log logByLogin) {
        this.logByLogin = logByLogin;
    }

    @ManyToOne
    @JoinColumn(name = "Employee_id", referencedColumnName = "id")
    public Employee getEmployeeByEmployeeId() {
        return employeeByEmployeeId;
    }

    public void setEmployeeByEmployeeId(Employee employeeByEmployeeId) {
        this.employeeByEmployeeId = employeeByEmployeeId;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserHasAuthorization> getUserHasAuthorizationsById() {
        return userHasAuthorizationsById;
    }

    public void setUserHasAuthorizationsById(Collection<UserHasAuthorization> userHasAuthorizationsById) {
        this.userHasAuthorizationsById = userHasAuthorizationsById;
    }
}
