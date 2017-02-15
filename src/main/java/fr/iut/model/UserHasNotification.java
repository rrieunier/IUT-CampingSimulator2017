package fr.iut.model;

import javax.persistence.*;

/**
 * Created by Sydpy on 2/15/17.
 */
@Entity
@Table(name = "User_has_Notification", schema = "CampingSimulator", catalog = "")
@IdClass(UserHasNotificationPK.class)
public class UserHasNotification {
    private int userId;
    private int notificationId;

    @Id
    @Column(name = "User_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "Notification_id", nullable = false)
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHasNotification that = (UserHasNotification) o;

        if (userId != that.userId) return false;
        if (notificationId != that.notificationId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + notificationId;
        return result;
    }
}
