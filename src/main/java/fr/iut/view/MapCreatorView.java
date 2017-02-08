package fr.iut.view;

import fr.iut.App;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/6/17.
 */
public class MapCreatorView extends Scene {

    App app;
    ScrollPane mapViewPort;
    Canvas map;
    File mapFile = null;

    File selectedBigIconFile = null;
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


        StackPane header = new StackPane();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #336699;");
        Text title = new Text("Création de la carte");
        title.setFill(Color.WHITESMOKE);
        title.setFont(Font.font("DejaVu Sans", 30));
        header.getChildren().addAll(title);

        map = new Canvas(800, 500);
        map.getGraphicsContext2D().setFill(Color.BLACK);
        map.getGraphicsContext2D().fillRect(0, 0, map.getWidth(), map.getHeight());
        map.getGraphicsContext2D().setFill(Color.WHITE);
        map.getGraphicsContext2D().setFont(Font.font("DejaVu Sans", 20));
        map.getGraphicsContext2D().fillText("Cliquez pour importer une carte...", 220, 235);

        mapViewPort = new ScrollPane();
        mapViewPort.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setContent(map);
        mapViewPort.setMaxWidth(map.getWidth());
        mapViewPort.setMaxHeight(map.getHeight());
        mapViewPort.setPannable(true);

        map.setOnMouseClicked(mouseEvent -> {
            if(mapFile == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png")); //On autorise que les images png
                mapFile = fileChooser.showOpenDialog(getWindow());

                if (mapFile != null) {
                    Image image = new Image(mapFile.toURI().toString());

                    if (image.getWidth() > App.SCREEN_W - 100) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Oops!");
                        alert.setHeaderText(null);
                        alert.setContentText("L'image est trop grande ! Merci d'en prendre une faisant maximum " + (int) (App.SCREEN_W - 100) + " pixels de largeur !");
                        alert.showAndWait();
                        mapFile = null;
                        return;
                    }

                    map.setWidth(image.getWidth());
                    map.setHeight(image.getHeight());
                    map.getGraphicsContext2D().drawImage(image, 0, 0);

                    centerNodeInScrollPane(mapViewPort, map);
                }
            }
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if(selectedIcon != null) {
                Bounds boundsInScene = selectedIcon.localToScene(selectedIcon.getBoundsInLocal());
                Bounds mapViewPort_coords = mapViewPort.sceneToLocal(boundsInScene);
                Bounds map_coords = map.sceneToLocal(boundsInScene);

                //Si l'item est dans le canvas
                if(mapViewPort_coords.getMinX() > 0 && mapViewPort_coords.getMaxX() < mapViewPort.getWidth() && mapViewPort_coords.getMinY() > 0 && mapViewPort_coords.getMaxY() < mapViewPort.getHeight()) {

                    String fileStr = selectedBigIconFile.toURI().toString();
                    ImageView bigIcon = new ImageView(new Image(fileStr));

                    Optional<Map<String, String>> result = new LocationDialog(bigIcon).showAndWait();

                    result.ifPresent(mapResult -> {
                        map.getGraphicsContext2D().drawImage(selectedIcon.getImage(), map_coords.getMinX(), map_coords.getMinY());
                        //TODO : ajouter l'emplacement à une liste temporaire pour ensuite ajouter à la BDD
                    });
                }

                stackPane.getChildren().remove(selectedIcon);
                selectedIcon = null;
                selectedBigIconFile = null;
            }
        });

        FlowPane items = new FlowPane();
        items.setPadding(new Insets(30));
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
                        String fileStr = new File("res/items/x32/" + file.getName()).toURI().toString();
                        selectedIcon = new ImageView(new Image(fileStr));
                        selectedIcon.setManaged(false); //très important, permet de dire au parent (Le stackpane, de ne pas gérer de la position de l'image
                        selectedBigIconFile = file;
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

                items.getChildren().add(item);
            }
        }

        HBox buttonsBox = new HBox();
        Button buttonReset = new Button("Réinitialiser la carte");
        Button buttonFinish = new Button("Terminer");
        buttonReset.setMinSize(App.SCREEN_W / 10, App.SCREEN_H / 18);
        buttonFinish.setMinSize(App.SCREEN_W / 10, App.SCREEN_H / 18);
        HBox.setMargin(buttonReset, new Insets(20));
        HBox.setMargin(buttonFinish, new Insets(20));
        buttonsBox.getChildren().addAll(buttonReset, buttonFinish);
        buttonsBox.setAlignment(Pos.TOP_CENTER);
        buttonsBox.setSpacing(100);

        buttonReset.setOnMouseClicked(mouseEvent -> {
            mapFile = null;
            map.setWidth(800);
            map.setHeight(500);
            map.getGraphicsContext2D().setFill(Color.BLACK);
            map.getGraphicsContext2D().fillRect(0, 0, map.getWidth(), map.getHeight());
            map.getGraphicsContext2D().setFill(Color.WHITE);
            map.getGraphicsContext2D().setFont(Font.font("DejaVu Sans", 20));
            map.getGraphicsContext2D().fillText("Cliquez pour importer une carte...", 220, 235);
        });

        VBox.setMargin(items, new Insets(10, App.SCREEN_W/4, 20, App.SCREEN_W/4));
        VBox.setMargin(mapViewPort, new Insets(20, 0, 20, 0));
        verticalWrap.getChildren().addAll(header, mapViewPort, items, buttonsBox);

        stackPane.getChildren().addAll(scrollPane);

        verticalWrap.setAlignment(Pos.TOP_CENTER);
    }

    private void centerNodeInScrollPane(ScrollPane scrollPane, Node node) {
        double h = scrollPane.getContent().getBoundsInLocal().getHeight();
        double y = (node.getBoundsInParent().getMaxY() + node.getBoundsInParent().getMinY()) / 2.0;
        double v = scrollPane.getViewportBounds().getHeight();
        scrollPane.setVvalue(scrollPane.getVmax() * ((y - 0.5 * v) / (h - v)));
    }
}