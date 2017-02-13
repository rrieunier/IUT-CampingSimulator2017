package fr.iut.view;

import fr.iut.App;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by theo on 08/02/17.
 */
public class HeaderView extends StackPane{

    private Text title;

    public HeaderView(String title){

        setPadding(new Insets(20));
        setStyle("-fx-background-color: #336699;");
        //setWidth(App.SCREEN_W);
        this.title = new Text(title);
        this.title.setFill(Color.WHITESMOKE);
        this.title.setFont(Font.font("DejaVu Sans", 30));
        getChildren().addAll(this.title);
    }
}
