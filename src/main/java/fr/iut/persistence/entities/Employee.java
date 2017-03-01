package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(length = 11)
    private String phone;

    @Column(length = 45)
    private String email;

    @Column
    private String completeAddress;

    @Column(length = 20, unique = true)
    private String login;

    @Column
    private String password;

    @ManyToMany(
            targetEntity=Authorization.class,
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="employee_id"),
            inverseJoinColumns=@JoinColumn(name="authorization_id")
    )
    private Set<Authorization> authorizations = new HashSet<>();

    @ManyToMany(
            targetEntity=Notification.class,
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            joinColumns=@JoinColumn(name="employee_id"),
            inverseJoinColumns=@JoinColumn(name="notification_id")
    )
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "employee" )
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "employee" )
    private Set<Log> logs = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<Authorization> getAuthorizations() {
        return authorizations;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorizations(Set<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }
}
