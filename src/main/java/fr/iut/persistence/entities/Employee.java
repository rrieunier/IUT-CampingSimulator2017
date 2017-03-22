package fr.iut.persistence.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table
public class Employee implements EntityModel<Integer> {

    /**
     * Employee's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    /**
     * Employee's firstname.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Employee's lastname.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Employee's phone number.
     */
    @Column(length = 11)
    private String phone;

    /**
     * Employee's email.
     */
    @Column(length = 45)
    private String email;

    /**
     * Employee's complete address.
     */
    @Column
    private String completeAddress;

    /**
     * Employee's login if it's a user.
     */
    @Column(length = 20, unique = true)
    private String login;

    /**
     * Employee's password if it's a user.
     */
    @Column
    private String password;

    /**
     * Authorizations granted to this employee if it's a user.
     */
    @Column
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Authorization> authorizations;

    /**
     * Notifications sent to this employee if it's a user.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Employee_Notification",
            joinColumns=@JoinColumn(name="employee_id"),
            inverseJoinColumns=@JoinColumn(name="notification_id")
    )
    private Set<Notification> notifications;

    /**
     * Tasks attributed to this employee.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    /**
     * Logs made by this employee if it's a user.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Log> logs;

    public Integer getId() {
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

    public void setId(Integer id) {
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
