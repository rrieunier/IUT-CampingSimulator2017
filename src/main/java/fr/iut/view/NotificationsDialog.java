package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.NotificationsController;
import fr.iut.model.Notification;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shellcode on 2/14/17.
 */
public class NotificationsDialog extends Dialog<Integer> {

    private NotificationsController controller;

    public static final double NOTIFICATION_WIDTH = App.SCREEN_W / 3;
    public static final double NOTIFICATION_HEIGHT = App.SCREEN_H / 3;

    public NotificationsDialog(NotificationsController controller) {
        this.controller = controller;

        setTitle("Notifications");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(new File("res/style.css").toURI().toString());
        dialogPane.setMinHeight(NOTIFICATION_HEIGHT);

        VBox wrapper = new VBox();
        VBox notificationsWrapper = new VBox();
        ScrollPane scrollPane = new ScrollPane(notificationsWrapper);

        HeaderView header = new HeaderView("Notifications");
        header.setMinWidth(NOTIFICATION_WIDTH);
        wrapper.getChildren().add(header);

        for(Notification notification : controller.getNotifications()) {
            VBox notifBox = new VBox();
            Text title = new Text(notification.getTitle());
            Text content = new Text(notification.getContent());
            notifBox.getChildren().addAll(title, content);
            notificationsWrapper.getChildren().add(notifBox);
        }

        dialogPane.getButtonTypes().addAll(new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));

        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hbox = (HBox) dialogPane.lookup(".container");
        hbox.getChildren().add(spacer);


        wrapper.getChildren().add(scrollPane);
        dialogPane.setContent(wrapper);

        setResultConverter((ButtonType dialogButton) -> 0);
    }
}
