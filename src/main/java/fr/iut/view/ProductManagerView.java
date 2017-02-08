package fr.iut.view;


import fr.iut.App;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProductManagerView extends Scene{

    App app;

    public ProductManagerView (App app) {
        super(new AnchorPane());
        this.app = app;

        AnchorPane components = (AnchorPane)getRoot();
        components.setStyle("-fx-background-color: rgb(12, 27, 51);");

        StackPane header = new StackPane();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #336699;");
        Text title = new Text("Création de la carte");
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title);

        components.getChildren().add(header);
    }

}
