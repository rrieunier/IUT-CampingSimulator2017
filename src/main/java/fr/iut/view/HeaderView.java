package fr.iut.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Header of all the windows
 */
public class HeaderView extends BorderPane {

    public HeaderView(String t){

        setPadding(new Insets(15));
        setStyle("-fx-background-color: #336699;");
        Text title = new Text(t);
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        setCenter(title);
    }
}
