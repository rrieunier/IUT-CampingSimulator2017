package fr.iut.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class HeaderView extends BorderPane {

    private Text title;

    public HeaderView(String title){

        setPadding(new Insets(20));
        setStyle("-fx-background-color: #336699;");
        //setWidth(App.SCREEN_W);
        this.title = new Text(title);
        this.title.setFill(Color.WHITESMOKE);
        this.title.setFont(Font.font("DejaVu Sans", 30));
        setCenter(this.title);
    }
}
