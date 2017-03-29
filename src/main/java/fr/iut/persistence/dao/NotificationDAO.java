package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Notification;

/**
 * Created by shellcode on 3/13/17.
 */
public class NotificationDAO extends GenericDAO<Notification, Integer> {
    /**
     * Constructor.
     */
    public NotificationDAO() {
        super(Notification.class);
    }

    public void addNotification(Notification notification) {
        save(notification);
    }

    public void setSolved(Notification notification) {
        remove(notification);
    }
}
