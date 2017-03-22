package fr.iut.controller;

import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.MapDao;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Map;
import fr.iut.persistence.entities.Spot;
import fr.iut.view.MapCreatorView;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class MapController {

    private HomeController homeController;
    private MapCreatorView mapCreatorView = new MapCreatorView(this);
    private MapDao daoMap = new MapDao();
    private GenericDAO<Location, Integer> daoLocation = new GenericDAO<>(Location.class);

    private Map currentMap = null;

    public MapController(HomeController homeController) {
        this.homeController = homeController;
        currentMap = daoMap.getMap();
    }

    public SubScene getView() {
        return mapCreatorView;
    }

    public void storeMap(File mapFile) {

        Map map = new Map();

        try {
            map.setImage(Files.readAllBytes(Paths.get(mapFile.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("persist map...");
        daoMap.setMap(map);
        System.out.println("Done !");
        currentMap = map;
    }

    public void storeLocation(Location location) {
        daoLocation.save(location);
    }

    public boolean isMapAlreadyCreated() {
        return currentMap != null;
    }

    public Window getMainWindow() {
        return homeController.getView().getWindow();
    }
}
