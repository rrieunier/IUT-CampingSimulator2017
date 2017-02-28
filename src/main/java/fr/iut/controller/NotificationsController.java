package fr.iut.controller;

import fr.iut.persistence.entities.Notification;
import fr.iut.view.NotificationsDialog;
import javafx.scene.control.Dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shellcode on 2/14/17.
 */
public class NotificationsController implements ControllerInterface {

    private HomeController homeController;
    private NotificationsDialog notificationsDialog = new NotificationsDialog(this);

    private List<Notification> notifications = new ArrayList<>();

    private boolean querying = true;

    public NotificationsController(HomeController homeController) {
        this.homeController = homeController;

        updateNotificationsAsynchronously();
    }

    @Override
    public Dialog<Integer> getView() {
        return notificationsDialog;
    }

    @Override
    public void finish() {

    }

    public List<Notification> getNotifications() {

        //TODO : Remove
        ArrayList<Notification> stub = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            Notification notification = new Notification();
            notification.setTitle("Notif " + (i+1));
            notification.setContent("Contenu : blablablabla" + (i+1));
            stub.add(notification);
        }

        return stub;
        //TODO : -------


        //return notifications;
    }

    private void updateNotificationsAsynchronously() {
        new Thread(() -> {

            while(querying) {
                //TODO : query database to saveOrUpdate notifications
                System.out.println("querying notifications in database...");

                try {
                    Thread.sleep(1000); //saveOrUpdate toutes les secondes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void stopQuerying() {
        querying = false;
    }
}
