package fr.iut.view;

import fr.iut.App;
import fr.iut.model.LocationEntity;
import fr.iut.model.SpotEntity;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/6/17.
 */
public class MapCreatorView extends Scene {

    private final static int MAP_VIEWPORT_WIDTH = (int)(App.SCREEN_W*3/4);
    private final static int MAP_VIEWPORT_HEIGHT = (int)(App.SCREEN_H/2);
    private App app;
    private ScrollPane mapViewPort;
    private ArrayList<LocationEntity> locations = new ArrayList<>();

    private StackPane mapPane;
    private File mapFile = null;

    private File selectedBigIconFile = null;
    private ImageView selectedIcon = null;
    private double mouseX ;
    private double mouseY ;

    public MapCreatorView(App app, String username) {
        super(new StackPane());
        this.app = app;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenue " + username);
        alert.setHeaderText(null);
        alert.setContentText("Cette interface permet de créer la carte de votre camping, glissez/deposez les éléments dessus.");

        Platform.runLater(alert::showAndWait);

        StackPane stackPane = (StackPane)getRoot();

        ScrollPane scrollPane = new ScrollPane();
        VBox verticalWrap = new VBox();
        scrollPane.setContent(verticalWrap);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        verticalWrap.setStyle("-fx-background-color: rgb(12, 27, 51);");


        HeaderView header = new HeaderView("Création de la carte");

        mapPane = new StackPane();
        mapPane.setAlignment(Pos.CENTER);
        mapPane.setPrefWidth(MAP_VIEWPORT_WIDTH);
        mapPane.setMinWidth(MAP_VIEWPORT_WIDTH);
        mapPane.setPrefHeight(MAP_VIEWPORT_HEIGHT);
        mapPane.setMinHeight(MAP_VIEWPORT_HEIGHT);
        mapPane.setStyle("-fx-background-color: black;");
        Text importMapText = new Text("Cliquez pour importer une carte...");
        importMapText.setFont(Font.font("DejaVu Sans", 20));
        importMapText.setFill(Color.WHITE);
        mapPane.getChildren().add(importMapText);

        mapViewPort = new ScrollPane();
        mapViewPort.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setContent(mapPane);
        mapViewPort.setMaxWidth(MAP_VIEWPORT_WIDTH);
        mapViewPort.setMaxHeight(MAP_VIEWPORT_HEIGHT);
        mapViewPort.setPannable(true);

        addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if(selectedIcon != null) {
                Bounds boundsInScene = selectedIcon.localToScene(selectedIcon.getBoundsInLocal());
                Bounds mapViewPort_coords = mapViewPort.sceneToLocal(boundsInScene);
                Bounds map_coords = mapPane.sceneToLocal(boundsInScene);

                //Si l'item est dans le viewport
                if(mapViewPort_coords.getMinX() > 0 && mapViewPort_coords.getMaxX() < mapViewPort.getWidth() && mapViewPort_coords.getMinY() > 0 && mapViewPort_coords.getMaxY() < mapViewPort.getHeight()) {

                    String fileStr = selectedBigIconFile.toURI().toString();
                    ImageView bigIcon = new ImageView(new Image(fileStr));

                    Optional<Map<String, String>> result = new LocationDialog(bigIcon).showAndWait();

                    result.ifPresent(mapResult -> {
                        ImageView imageView = new ImageView(selectedIcon.getImage());

                        imageView.setTranslateX(map_coords.getMinX());
                        imageView.setTranslateY(map_coords.getMinY());
                        mapPane.getChildren().add(imageView);

                        LocationEntity location = new LocationEntity();
                        SpotEntity spot = new SpotEntity();
                        location.setSpot(spot);

                        location.setName(mapResult.get("name"));
                        location.setPointX(map_coords.getMinX());
                        location.setPointY(map_coords.getMinY());
                        spot.setCapacity(Integer.parseInt(mapResult.get("capacity")));
                        spot.setElectricity(Boolean.parseBoolean(mapResult.get("elec")));
                        spot.setWater(Boolean.parseBoolean(mapResult.get("water")));
                        spot.setShadow(Boolean.parseBoolean(mapResult.get("shadow")));

                        imageView.setOnMouseClicked(mouseEvent1 -> {
                            Optional<Map<String, String>> edit_result = new LocationDialog(bigIcon, location).showAndWait();

                            edit_result.ifPresent(mapEditResult -> {

                                if(mapEditResult.containsKey("delete")) {
                                    mapPane.getChildren().remove(imageView);
                                    locations.remove(location);
                                }

                                else {
                                    location.setName(mapEditResult.get("name"));
                                    location.setPointX(map_coords.getMinX());
                                    location.setPointY(map_coords.getMinY());
                                    spot.setCapacity(Integer.parseInt(mapEditResult.get("capacity")));
                                    spot.setElectricity(Boolean.parseBoolean(mapEditResult.get("elec")));
                                    spot.setWater(Boolean.parseBoolean(mapEditResult.get("water")));
                                    spot.setShadow(Boolean.parseBoolean(mapEditResult.get("shadow")));
                                }
                            });
                        });

                        locations.add(location);
                    });
                }

                stackPane.getChildren().remove(selectedIcon);
                selectedIcon = null;
                selectedBigIconFile = null;
            }
        });

        FlowPane items = new FlowPane();
        items.setPadding(new Insets(30));
        items.setStyle("-fx-background-color: rgb(50, 50, 50); -fx-border-color: rgb(55, 77, 114); -fx-border-width: 5px;");
        items.setAlignment(Pos.CENTER);
        items.setHgap(80);
        items.setVgap(80);

        File[] files64 = new File("res/items/x64").listFiles();

        if(files64 == null) {
            System.out.println("Can't find any items !!!");
            return;
        }

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
        buttonReset.setDisable(true);
        Button buttonFinish = new Button("Terminer");
        buttonFinish.setDisable(true);
        buttonReset.setMinSize(App.SCREEN_W / 10, App.SCREEN_H / 18);
        buttonFinish.setMinSize(App.SCREEN_W / 10, App.SCREEN_H / 18);
        HBox.setMargin(buttonReset, new Insets(20));
        HBox.setMargin(buttonFinish, new Insets(20));
        buttonsBox.getChildren().addAll(buttonReset, buttonFinish);
        buttonsBox.setAlignment(Pos.TOP_CENTER);
        buttonsBox.setSpacing(100);
        buttonReset.getStylesheets().add(new File("res/style.css").toURI().toString());
        buttonReset.getStyleClass().add("record-sales");
        buttonFinish.getStylesheets().add(new File("res/style.css").toURI().toString());
        buttonFinish.getStyleClass().add("record-sales");

        buttonReset.setOnMouseClicked(mouseEvent -> {
            mapFile = null;
            mapPane.setAlignment(Pos.CENTER);
            mapPane.setPrefWidth(MAP_VIEWPORT_WIDTH);
            mapPane.setMinWidth(MAP_VIEWPORT_WIDTH);
            mapPane.setPrefHeight(MAP_VIEWPORT_HEIGHT);
            mapPane.setMinHeight(MAP_VIEWPORT_HEIGHT);
            mapPane.getChildren().removeAll(mapPane.getChildren());
            mapPane.setStyle("-fx-background-color: black;");
            importMapText.setFont(Font.font("DejaVu Sans", 20));
            importMapText.setFill(Color.WHITE);
            mapPane.getChildren().add(importMapText);
            buttonReset.setDisable(true);
            buttonFinish.setDisable(true);
            mapViewPort.setMaxWidth(MAP_VIEWPORT_WIDTH);
            mapViewPort.setMaxHeight(MAP_VIEWPORT_HEIGHT);
            locations.clear();
        });

        buttonFinish.setOnMouseClicked(mouseEvent -> {
            System.out.println("Saving map & points in database");
            /*
            for(Location location : locations)
                location.store();
                */

            app.start("dev");
        });

        mapPane.setOnMouseClicked(mouseEvent -> {
            if(mapFile == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png")); //On autorise que les images png
                mapFile = fileChooser.showOpenDialog(getWindow());

                if (mapFile != null) {
                    Image image = new Image(mapFile.toURI().toString());

                    buttonFinish.setDisable(false);
                    buttonReset.setDisable(false);

                    mapPane.getChildren().remove(importMapText);


                    if(image.getWidth() < mapViewPort.getWidth())
                        mapViewPort.setMaxWidth(image.getWidth());

                    if(image.getHeight() < mapViewPort.getHeight())
                        mapViewPort.setMaxHeight(image.getHeight());

                    mapPane.setPrefWidth(image.getWidth());
                    mapPane.setMinWidth(image.getWidth());
                    mapPane.setPrefHeight(image.getHeight());
                    mapPane.setMinHeight(image.getHeight());

                    mapPane.setAlignment(Pos.TOP_LEFT);
                    mapPane.getChildren().add(new ImageView(image));


                }
            }
        });

        VBox.setMargin(items, new Insets(10, App.SCREEN_W/5, 20, App.SCREEN_W/5));
        VBox.setMargin(mapViewPort, new Insets(20, 0, 20, 0));
        verticalWrap.getChildren().addAll(header, mapViewPort, items, buttonsBox);

        stackPane.getChildren().addAll(scrollPane);

        verticalWrap.setAlignment(Pos.TOP_CENTER);
    }
}