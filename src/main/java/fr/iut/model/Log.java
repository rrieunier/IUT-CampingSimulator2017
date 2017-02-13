package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by Sydpy on 2/13/17.
 */
@Entity
public class Log {
    private Timestamp datetime;
    private String action;
    private String user;
    private int id;
    private Collection<User> users;

    @Basic
    @Column(name = "datetime", nullable = false)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "action", nullable = false, length = 150)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "user", nullable = false, length = 20)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (id != log.id) return false;
        if (datetime != null ? !datetime.equals(log.datetime) : log.datetime != null) return false;
        if (action != null ? !action.equals(log.action) : log.action != null) return false;
        if (user != null ? !user.equals(log.user) : log.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = datetime != null ? datetime.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "logByLogin")
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
