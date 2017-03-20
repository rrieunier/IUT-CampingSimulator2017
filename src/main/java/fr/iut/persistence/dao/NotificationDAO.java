package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Notification;

import javax.transaction.Transactional;

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

    @Transactional(rollbackOn = Exception.class)
    public void addNotification(Notification notification) {
        save(notification);
    }

    @Transactional(rollbackOn = Exception.class)
    public void setSolved(Notification notification) {
        remove(notification);
    }
}
