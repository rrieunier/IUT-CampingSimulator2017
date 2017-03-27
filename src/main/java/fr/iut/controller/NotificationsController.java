package fr.iut.controller;

import fr.iut.persistence.dao.NotificationDAO;
import fr.iut.persistence.entities.Notification;
import fr.iut.view.HomeView;
import fr.iut.view.NotificationsDialog;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shellcode on 2/14/17.
 */
public class NotificationsController {


    public interface NotificationsUpdatedListener {
        void onUnsolvedNotificationsCountChanged(int count);
    }

    /**
     * Listener which enables the view to update when the notifications count changes
     */
    NotificationsUpdatedListener listener;

    /**
     * Parent controller
     */
    private HomeController homeController;

    private NotificationDAO dao = new NotificationDAO();

    /**
     * boolean to stop threads nicely :)
     */
    private boolean querying = true;

    /**
     * Notifications in memory, clone of the actual DB state (update every 3 seconds)
     */
    private List<Notification> notifications = new ArrayList<>();

    public NotificationsController(HomeController homeController) {
        this.homeController = homeController;

        updateNotificationsAsynchronously();
    }

    /**
     * @return the corresponding view which is a Dialog
     */
    public Dialog<Integer> getView() {
        return new NotificationsDialog(this);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setOnUnsolvedNotificationsCountChangedListener(NotificationsUpdatedListener listener) {
        this.listener = listener;
    }

    /**
     * The notification is set solved and removed from the DB
     */
    public void solve(Notification notification) {
        notifications.remove(notification);
        dao.setSolved(notification);
    }

    public int getNotificationsCount() {
        return notifications.size();
    }

    /**
     * Run a thread which perform requests to the database every 3 seconds to keep notifications updated
     */
    private void updateNotificationsAsynchronously() {
        new Thread(() -> {

            while(querying) {
                System.out.println("querying notifications in database...");

                int old_notifs_count = notifications.size();
                notifications = dao.findAll();

                System.out.println("old size : " + old_notifs_count + " and  " + notifications.size() + " notifications waiting...");

                if(listener != null && notifications.size() != old_notifs_count)
                    listener.onUnsolvedNotificationsCountChanged(notifications.size());

                try {
                    //La boucle permet de pas attendre 3 secondes la fin du programme si jamais les requetes doivent s'arrêter
                    for(int i = 0; i < 3 && querying; i++) //saveOrUpdate toutes les 3 secondes
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Thread stopped");

        }).start();
    }

    public void stopQuerying() {
        querying = false;
        ((HomeView)homeController.getView()).stopBlinkAnimation();
    }
}
