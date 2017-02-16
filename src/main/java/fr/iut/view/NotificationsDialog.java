package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.NotificationsController;
import fr.iut.persistence.entities.Notification;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;

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
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        VBox wrapper = new VBox();
        VBox notificationsWrapper = new VBox();
        notificationsWrapper.setSpacing(3);
        ScrollPane scrollPane = new ScrollPane(notificationsWrapper);
        scrollPane.setMaxHeight(NOTIFICATION_HEIGHT);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HeaderView header = new HeaderView("Notifications");
        header.setMinWidth(NOTIFICATION_WIDTH);
        wrapper.getChildren().add(header);

        int i = 0;
        for(Notification notification : controller.getNotifications()) {
            BorderPane borderPane = new BorderPane();
            borderPane.setMinWidth(NOTIFICATION_WIDTH);

            if(i++ % 2 == 1)
                borderPane.setStyle("-fx-background-color: #336699;");
            else
                borderPane.setStyle("-fx-background-color: #0F355C;");

            VBox notifBox = new VBox();
            notifBox.setPadding(new Insets(20));
            HBox.setHgrow(notifBox, Priority.ALWAYS);

            Text title = new Text(notification.getTitle());
            title.setFont(new Font(18));
            title.setFill(Color.WHITE);
            Text content = new Text(notification.getContent());
            content.setFill(Color.WHITE);
            notifBox.getChildren().addAll(title, content);

            Button button = new Button("✔ Résolu");
            button.getStyleClass().add("record-sales");
            BorderPane.setMargin(button, new Insets(0, 30, 0, 0));
            BorderPane.setAlignment(button, Pos.CENTER);

            borderPane.setLeft(notifBox);
            borderPane.setRight(button);
            notificationsWrapper.getChildren().add(borderPane);
        }

        dialogPane.getButtonTypes().addAll(new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));
        wrapper.getChildren().add(scrollPane);
        dialogPane.setContent(wrapper);

        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hbox = (HBox) dialogPane.lookup(".container");
        hbox.getChildren().add(spacer);

        setResultConverter((ButtonType dialogButton) -> 0);
    }
}
