package fr.iut.view;

import fr.iut.App;
import fr.iut.controller.MapController;
import fr.iut.persistence.dao.EmployeeDAO;
import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Spot;
import fr.iut.persistence.entities.SpotType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shellcode on 2/6/17.
 */
public class MapCreatorView extends SubScene {

    private final static int MAP_VIEWPORT_WIDTH = (int)(App.SCREEN_W*3/4);
    private final static int MAP_VIEWPORT_HEIGHT = (int)(App.SCREEN_H / 2 + App.SCREEN_H / 10);
    private MapController controller;

    /**
     * We use a StackPane as root in order to drag & drop pictures everywhere in it
     */
    private StackPane stackPaneRoot;
    private ScrollPane mapViewPort;

    private StackPane mapPane;
    private File mapFile = null;

    private ImageView buttonReset;
    private Text importMapText;

    /**
     * Selected/Dragging current item
     */
    private ItemMap selectedItem;
    private double mouseX;
    private double mouseY;

    /**
     * true if the employee in the DB has the permission to edit the map
     */
    private boolean employeeHasUpdateMapPermission;

    /**
     * The available items are all the SpotType values
     */
    private ItemMap [] availableItems = new ItemMap[7];
    private ImageView draggingIcon;

    private fr.iut.persistence.entities.Map mapInBdd;

    /**
     * Build the CreatorView from existing Map in the DB
     */
    public MapCreatorView(MapController controller, fr.iut.persistence.entities.Map mapInBdd) {
        this(controller);
        this.mapInBdd = mapInBdd;

        //We create the image from bytes in DB
        Image image = new Image(new ByteArrayInputStream(mapInBdd.getImage()));

        buttonReset.setVisible(true);

        adaptMapPaneToImage(image);


        //We create the spots on the map from the DB
        for(Spot spot : controller.getAllSpots()) {

            ItemMap correspondingItem = null;

            for(ItemMap itemMap : availableItems)
                if(itemMap.getType().equals(spot.getSpotType())) {
                    correspondingItem = itemMap;
                    break;
                }

            if(correspondingItem == null) {
                System.out.println("This cannot be possible !!!");
                System.exit(1);
            }

            ImageView imageOnMap = new ImageView(correspondingItem.getSmallImage());
            mapPane.getChildren().add(imageOnMap);
            imageOnMap.setTranslateX(spot.getPointX());
            imageOnMap.setTranslateY(spot.getPointY());

            ItemMap finalCorrespondingItem = correspondingItem;
            imageOnMap.setOnMouseClicked(mouseEvent1 -> {
                editItem(new ImageView(finalCorrespondingItem.getBigImage()), imageOnMap, spot);
            });
        }
    }

    private void adaptMapPaneToImage(Image image) {
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

    /**
     * Build MapCreatorView from scratch, with no existing map in the DB
     */
    public MapCreatorView(MapController controller) {
        super(new StackPane(), App.SCREEN_W * 2/3, App.SCREEN_H * 3/4);
        this.controller = controller;

        employeeHasUpdateMapPermission = EmployeeDAO.getConnectedUser().hasPermission(Authorization.MAP_UPDATE);

        availableItems[0] = new ItemMap(SpotType.HOUSE);
        availableItems[1] = new ItemMap(SpotType.TRAILER);
        availableItems[2] = new ItemMap(SpotType.RESTAURANT);
        availableItems[3] = new ItemMap(SpotType.PARKING);
        availableItems[4] = new ItemMap(SpotType.POOL);
        availableItems[5] = new ItemMap(SpotType.TENT);
        availableItems[6] = new ItemMap(SpotType.SPORT);

        stackPaneRoot = (StackPane)getRoot();
        stackPaneRoot.setStyle("-fx-background-color:transparent;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color:transparent;");
        VBox verticalWrap = new VBox();
        scrollPane.setContent(verticalWrap);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        verticalWrap.setStyle("-fx-background-color: rgb(12, 27, 51);");

        mapPane = new StackPane();
        mapPane.setAlignment(Pos.CENTER);
        mapPane.setPrefWidth(MAP_VIEWPORT_WIDTH);
        mapPane.setMinWidth(MAP_VIEWPORT_WIDTH);
        mapPane.setPrefHeight(MAP_VIEWPORT_HEIGHT);
        mapPane.setMinHeight(MAP_VIEWPORT_HEIGHT);
        mapPane.setStyle("-fx-background-color: black;");

        importMapText = new Text("Cliquez pour importer une carte...");
        importMapText.setFont(Font.font("DejaVu Sans", 20));
        importMapText.setFill(Color.WHITE);
        mapPane.getChildren().add(importMapText);

        StackPane scrollContainer = new StackPane();

        mapViewPort = new ScrollPane();
        mapViewPort.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapViewPort.setContent(mapPane);
        mapViewPort.setMaxWidth(MAP_VIEWPORT_WIDTH);
        mapViewPort.setMaxHeight(MAP_VIEWPORT_HEIGHT);
        mapViewPort.setPannable(true);
        scrollContainer.getChildren().add(mapViewPort);

        if(employeeHasUpdateMapPermission)
            handleDropItem();

        HBox items = new HBox();
        buildDraggableItems(items);

        buttonReset = new ImageView(new Image(new File("res/reset-icon.png").toURI().toString()));
        buttonReset.setCursor(Cursor.CROSSHAIR);
        buttonReset.setVisible(false);
        StackPane.setAlignment(buttonReset, Pos.TOP_RIGHT);
        StackPane.setMargin(buttonReset, new Insets(20, 20, 0, 0));
        scrollContainer.getChildren().add(buttonReset);

        if(employeeHasUpdateMapPermission) {
            buttonReset.setOnMouseClicked(mouseEvent -> {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText("Vous êtes sur le point de supprimer la carte et tous les emplacements crées, cette opération est irréversible.");
                alert.setContentText("Voulez vous vraiment tout supprimer ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    mapFile = null;
                    mapInBdd = null;
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
                    buttonReset.setVisible(false);
                    mapViewPort.setMaxWidth(MAP_VIEWPORT_WIDTH);
                    mapViewPort.setMaxHeight(MAP_VIEWPORT_HEIGHT);
                    controller.removeMapAndAllSpots();
                }
            });

            mapPane.setOnMouseClicked(mouseEvent -> {
                if (mapFile == null && mapInBdd == null) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png")); //On autorise que les images png
                    mapFile = fileChooser.showOpenDialog(controller.getMainWindow());

                    if (mapFile != null) {

                        System.out.println("map picture size : " + mapFile.length());

                        if (mapFile.length() > 16000000) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("Votre image est trop volumineuse ! Maximum : 16Mo");
                            mapFile = null;
                            return;
                        }

                        Image image = new Image(mapFile.toURI().toString());

                        buttonReset.setVisible(true);

                        adaptMapPaneToImage(image);

                        controller.storeMap(mapFile);
                    }
                }
            });
        }

        VBox.setMargin(items, new Insets(30, 0, 20, 0));
        verticalWrap.getChildren().addAll(scrollContainer, items);

        stackPaneRoot.getChildren().addAll(scrollPane);

        verticalWrap.setAlignment(Pos.TOP_CENTER);
    }

    private void buildDraggableItems(HBox items) {
        items.setPadding(new Insets(30));
        items.setStyle("-fx-background-color: rgb(50, 50, 50); -fx-border-color: rgb(55, 77, 114); -fx-border-width: 5px;");
        items.setAlignment(Pos.CENTER);
        items.setSpacing(80);

        for(ItemMap availableItem : availableItems) {

            ImageView itemBigImage = new ImageView(availableItem.getBigImage());
            ImageView itemSmallImage = new ImageView(availableItem.getSmallImage());

            if(employeeHasUpdateMapPermission) {
                itemBigImage.setOnMousePressed(mouseEvent -> {
                    if (mapFile != null || mapInBdd != null) {
                        selectedItem = availableItem;
                        draggingIcon = itemSmallImage;
                        draggingIcon.setManaged(false);//très important, permet de dire au parent (Le stackpane, de ne pas gérer de la position de l'image) lors de son déplacement drag&drop
                        stackPaneRoot.getChildren().add(draggingIcon);

                        Point2D mouse_pos = sceneToLocal(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                        mouseX = mouse_pos.getX();
                        mouseY = mouse_pos.getY();

                        draggingIcon.relocate(mouseX - draggingIcon.getImage().getWidth() / 2, mouseY - draggingIcon.getImage().getHeight() / 2);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Impossible");
                        alert.setHeaderText("Merci de choisir une carte avant de créer des emplacements.");
                        alert.setContentText(null);
                        alert.show();
                    }
                });

                itemBigImage.setOnMouseDragged(mouseEvent -> {
                    if (selectedItem != null) {
                        Point2D mouse_pos = sceneToLocal(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                        double deltaX = mouse_pos.getX() - mouseX;
                        double deltaY = mouse_pos.getY() - mouseY;

                        draggingIcon.relocate(draggingIcon.getLayoutX() + deltaX, draggingIcon.getLayoutY() + deltaY);

                        mouseX = mouse_pos.getX();
                        mouseY = mouse_pos.getY();
                    }
                });
            }

            items.getChildren().add(itemBigImage);
        }
    }

    private void handleDropItem() {
        addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            if(selectedItem != null) {
                Bounds boundsInScene = draggingIcon.localToScene(draggingIcon.getBoundsInLocal());
                Bounds mapViewPort_coords = mapViewPort.sceneToLocal(boundsInScene);
                Bounds map_coords = mapPane.sceneToLocal(boundsInScene);

                //Si l'item est dans le viewport
                if(mapViewPort_coords.getMinX() > 0 && mapViewPort_coords.getMaxX() < mapViewPort.getWidth() && mapViewPort_coords.getMinY() > 0 && mapViewPort_coords.getMaxY() < mapViewPort.getHeight()) {
                    createItem(map_coords.getMinX(), map_coords.getMinY());
                }

                stackPaneRoot.getChildren().remove(draggingIcon);
                draggingIcon = null;
                selectedItem = null;
            }
        });
    }

    private void createItem(double x, double y) {
        ImageView bigIcon = new ImageView(selectedItem.getBigImage());

        Optional<Map<String, String>> result = new LocationDialog(bigIcon).showAndWait();

        result.ifPresent(mapResult -> {
            ImageView imageOnMap = new ImageView(selectedItem.getSmallImage());
            mapPane.getChildren().add(imageOnMap);
            imageOnMap.setTranslateX(x);
            imageOnMap.setTranslateY(y);

            Spot spot = new Spot();
            spot.setSpotType(selectedItem.getType());
            spot.setName(mapResult.get("name"));
            spot.setPointX(x);
            spot.setPointY(y);
            spot.setCapacity(Integer.parseInt(mapResult.get("capacity")));
            spot.setElectricity(Boolean.parseBoolean(mapResult.get("elec")));
            spot.setWater(Boolean.parseBoolean(mapResult.get("water")));
            spot.setShadow(Boolean.parseBoolean(mapResult.get("shadow")));


            imageOnMap.setOnMouseClicked(mouseEvent1 -> {
                editItem(bigIcon, imageOnMap, spot);
            });


            controller.storeSpot(spot);
        });
    }

    private void editItem(ImageView bigIcon, ImageView imageOnMap, Spot spot) {
        Optional<Map<String, String>> edit_result = new LocationDialog(bigIcon, spot).showAndWait();

        edit_result.ifPresent(mapEditResult -> {

            if(!employeeHasUpdateMapPermission) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Impossible");
                alert.setHeaderText("Vous n'avez pas la permission de modifier les emplacements.");
                alert.setContentText(null);
                alert.show();
                return;
            }

            if(mapEditResult.containsKey("remove")) {
                mapPane.getChildren().remove(imageOnMap);
                controller.removeSpot(spot);
            }

            else {
                spot.setName(mapEditResult.get("name"));
                spot.setCapacity(Integer.parseInt(mapEditResult.get("capacity")));
                spot.setElectricity(Boolean.parseBoolean(mapEditResult.get("elec")));
                spot.setWater(Boolean.parseBoolean(mapEditResult.get("water")));
                spot.setShadow(Boolean.parseBoolean(mapEditResult.get("shadow")));
                controller.updateSpot(spot);
            }
        });
    }

    /**
     * Class which represents an item (a location) on the map
     */
    class ItemMap {

        private SpotType type;
        private Image smallImage, bigImage;

        ItemMap(SpotType type) {
            this.type = type;

            String filename = null;

            switch (type) {
                case HOUSE: filename = "cabin.png"; break;
                case TRAILER: filename = "trailer.png"; break;
                case RESTAURANT: filename = "cutlery.png"; break;
                case PARKING: filename = "parking.png"; break;
                case POOL: filename = "swimming-pool.png"; break;
                case SPORT: filename = "kayak.png"; break;
                case TENT: filename = "tent.png"; break;
            }

            smallImage = new Image(new File("res/items/x32/" + filename).toURI().toString());
            bigImage = new Image(new File("res/items/x64/" + filename).toURI().toString());
        }

        SpotType getType() {
            return type;
        }

        Image getSmallImage() {
            return smallImage;
        }

        Image getBigImage() {
            return bigImage;
        }
    }
}