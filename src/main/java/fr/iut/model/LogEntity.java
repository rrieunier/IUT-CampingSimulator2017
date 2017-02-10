package fr.iut.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by roman on 10/02/17.
 */
@Entity
@Table(name = "Log", schema = "CampingSimulator", catalog = "")
public class LogEntity {
    private int idLog;
    private Timestamp datetime;
    private String action;
    private String user;
    private UserEntity userByUser;

    @Id
    @Column(name = "idLog", nullable = false)
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntity logEntity = (LogEntity) o;

        if (idLog != logEntity.idLog) return false;
        if (datetime != null ? !datetime.equals(logEntity.datetime) : logEntity.datetime != null) return false;
        if (action != null ? !action.equals(logEntity.action) : logEntity.action != null) return false;
        if (user != null ? !user.equals(logEntity.user) : logEntity.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLog;
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "login", nullable = false)
    public UserEntity getUserByUser() {
        return userByUser;
    }

    public void setUserByUser(UserEntity userByUser) {
        this.userByUser = userByUser;
    }
}
