package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by shellcode on 2/6/17.
 */
public class MapCreatorView extends Scene {

    App app;
    Canvas map;
    File mapFile = null;

    ImageView selectedIcon = null;
    private double mouseX ;
    private double mouseY ;

    public MapCreatorView(App app) {
        super(new StackPane());
        this.app = app;

        StackPane stackPane = (StackPane)getRoot();

        ScrollPane scrollPane = new ScrollPane();
        VBox verticalWrap = new VBox();
        scrollPane.setContent(verticalWrap);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        verticalWrap.setStyle("-fx-background-color: rgb(12, 27, 51);");


        HeaderView header = new HeaderView("Création de la carte");

        map = new Canvas(800, 500);
        map.getGraphicsContext2D().setFill(Color.BLACK);
        map.getGraphicsContext2D().fillRect(0, 0, map.getWidth(), map.getHeight());
        map.getGraphicsContext2D().setFill(Color.WHITE);
        map.getGraphicsContext2D().setFont(Font.font("DejaVu Sans", 20));
        map.getGraphicsContext2D().fillText("Cliquez pour importer une carte...", 220, 235);

        map.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png")); //On autorise que les images png
            mapFile = fileChooser.showOpenDialog(getWindow());

            if (mapFile != null) {
                Image image = new Image(mapFile.toURI().toString());

                if(image.getWidth() > App.SCREEN_W - 100) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Oops!");
                    alert.setHeaderText(null);
                    alert.setContentText("L'image est trop grande ! Merci d'en prendre une faisant maximum " + (int)(App.SCREEN_W-100) + " pixels de largeur !");
                    alert.showAndWait();
                    mapFile = null;
                    return;
                }

                map.setWidth(image.getWidth());
                map.setHeight(image.getHeight());
                map.getGraphicsContext2D().drawImage(image, 0, 0);

                //Petit trick pour refresh le canvas
                app.getStage().setWidth(App.SCREEN_W);
                app.getStage().setHeight(App.SCREEN_H);
            }
        });

        FlowPane items = new FlowPane();
        items.setPadding(new Insets(50));
        items.setStyle("-fx-background-color: rgb(50, 50, 50);");
        items.setAlignment(Pos.CENTER);
        items.setHgap(80);
        items.setVgap(80);

        File[] files64 = new File("res/items/x64").listFiles();

        for (File file : files64) {
            if (file.isFile()) {
                ImageView item = new ImageView(new Image(file.toURI().toString()));

                item.setOnMousePressed(mouseEvent -> {
                    if(mapFile != null) {
                        String filreStr = new File("res/items/x32/" + file.getName()).toURI().toString();
                        selectedIcon = new ImageView(new Image(filreStr));
                        selectedIcon.setManaged(false); //très important, permet de dire au parent (Le stackpane, de ne pas se charger de la position de l'image
                        stackPane.getChildren().add(selectedIcon);

                        mouseX = mouseEvent.getSceneX();
                        mouseY = mouseEvent.getSceneY();
                        selectedIcon.relocate(mouseX - selectedIcon.getImage().getWidth() / 2, mouseY - selectedIcon.getImage().getHeight() / 2);
                    }
                });

                item.setOnMouseDragged(mouseEvent -> {
                    if(selectedIcon != null) {
                        double deltaX = mouseEvent.getSceneX() - mouseX ;
                        double deltaY = mouseEvent.getSceneY() - mouseY ;

                        selectedIcon.relocate(selectedIcon.getLayoutX() + deltaX, selectedIcon.getLayoutY() + deltaY);

                        mouseX = mouseEvent.getSceneX() ;
                        mouseY = mouseEvent.getSceneY() ;
                    }
                });

                item.setOnMouseReleased(mouseEvent -> {
                    if(selectedIcon != null) {
                        //stackPane.getChildren().remove(selectedIcon);
                        selectedIcon = null;
                    }
                });

                items.getChildren().add(item);
            }
        }

        verticalWrap.setMargin(items, new Insets(20, App.SCREEN_W/4, 20, App.SCREEN_W/4));
        verticalWrap.setMargin(map, new Insets(20, 0, 20, 0));
        verticalWrap.getChildren().addAll(header, map, items);

        stackPane.getChildren().addAll(scrollPane);

        verticalWrap.setAlignment(Pos.TOP_CENTER);
    }
}