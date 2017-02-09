package fr.iut.view;

/**
 * Created by Yannini on 09/02/2017.
 */

import fr.iut.App;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NewClientView extends Scene {


    public static final double WINDOW_WIDTH = 500;
    public static final double WINDOW_HEIGHT = 750;
    private static final Color COLOR_BACKGROUND = Color.rgb(42, 42, 42);

    private Group components;
    private StackPane header = new StackPane();

    private VBox fields = new VBox();

    private Rectangle title_back = new Rectangle();
    private Text title = new Text("Nouveau client");

    private TextField last_name = new TextField();
    private TextField first_name = new TextField();
    private TextField address = new TextField();
    private TextField phone = new TextField();

    private Button confirm = new Button("Valider");

    private App app;



    public NewClientView (App app){
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        components = (Group) getRoot();
        this.app = app;

        setFill(COLOR_BACKGROUND);

        title_back.setWidth(WINDOW_WIDTH);
        title_back.setHeight(WINDOW_HEIGHT / 6);
        title_back.setFill(Color.rgb(55, 77, 114));
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title_back, title);

       // last_name.setMaxWidth(WINDOW_WIDTH / 1.1);
        last_name.setMinHeight(WINDOW_HEIGHT / 8);
        last_name.setLayoutX(WINDOW_WIDTH/2);
        last_name.setPromptText("Nom");
        last_name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        first_name.setMaxWidth(WINDOW_WIDTH / 1.1);
        first_name.setMinHeight(WINDOW_HEIGHT / 8);
        first_name.setPromptText("Prénom");
        first_name.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        address.setMaxWidth(WINDOW_WIDTH / 1.1);
        address.setMinHeight(WINDOW_HEIGHT / 8);
        address.setPromptText("Adresse");
        address.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        phone.setMaxWidth(WINDOW_WIDTH / 1.1);
        phone.setMinHeight(WINDOW_HEIGHT / 8);
        phone.setPromptText("N° de téléphone");
        phone.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 17px;");

        fields.getChildren().addAll(last_name, first_name, address, phone);
        fields.setSpacing(WINDOW_HEIGHT / 10);
        fields.setAlignment(Pos.CENTER);

        confirm.setMinSize(WINDOW_WIDTH / 6, WINDOW_HEIGHT / 10);
        confirm.setLayoutX((WINDOW_WIDTH - confirm.getMinWidth()) / 2);
        confirm.setLayoutY(WINDOW_HEIGHT / 1.2);
        confirm.setText("Valider");

        components.getChildren().addAll(title, title_back, fields, confirm);


    }

}
