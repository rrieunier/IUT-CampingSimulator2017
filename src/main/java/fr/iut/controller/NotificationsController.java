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

    NotificationsUpdatedListener listener;

    private HomeController homeController;
    private NotificationDAO dao = new NotificationDAO();

    private boolean querying = true;
    private List<Notification> notifications = new ArrayList<>();

    public NotificationsController(HomeController homeController) {
        this.homeController = homeController;

        updateNotificationsAsynchronously();
    }

    public Dialog<Integer> getView() {
        return new NotificationsDialog(this);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setOnUnsolvedNotificationsCountChangedListener(NotificationsUpdatedListener listener) {
        this.listener = listener;
    }

    public void solve(Notification notification) {
        notifications.remove(notification);
        dao.remove(notification);
    }

    public int getNotificationsCount() {
        return notifications.size();
    }

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
                    //La boucle permet de pas attendre 10 secondes la fin du programme si jamais les requetes doivent s'arrêter
                    for(int i = 0; i < 10 && querying; i++) //saveOrUpdate toutes les 10 secondes
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
