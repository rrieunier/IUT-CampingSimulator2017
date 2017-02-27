package fr.iut.view;
/**
 * Created by roman on 11/02/17.
 */

import fr.iut.App;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NewReservationView extends Scene {
    public static final double HEADER_WIDTH = App.SCREEN_W / 2;

    private App app;
    private TextField firstName, lastName;
    private DatePicker arrival, departure;
    private Button pickLocation, add, cancel;
    private static final Color COLOR_BACKGROUND = Color.rgb(42, 42, 42);

    public NewReservationView(App app) {
        super(new GridPane(), App.SCREEN_W, App.SCREEN_H);
        this.app = app;

        GridPane gridPane = (GridPane) getRoot();
        HeaderView header = new HeaderView("Permissions");
        header.setMinWidth(HEADER_WIDTH);
        gridPane.addRow(0, header);
    }

}
