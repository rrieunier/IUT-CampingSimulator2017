package fr.iut.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Sydpy on 2/15/17.
 */
public class UserHasNotificationPK implements Serializable {
    private int userId;
    private int notificationId;

    @Column(name = "User_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "Notification_id", nullable = false)
    @Id
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

        UserHasNotificationPK that = (UserHasNotificationPK) o;

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
