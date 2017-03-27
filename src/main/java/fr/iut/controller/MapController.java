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
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class MapController {

    private HomeController homeController;
    private MapCreatorView mapCreatorView;
    private MapDao daoMap = new MapDao();
    private GenericDAO<Spot, Integer> daoSpot = new GenericDAO<>(Spot.class);

    private Map currentMap = null;

    public MapController(HomeController homeController) {
        this.homeController = homeController;
        currentMap = daoMap.getMap();
    }

    public SubScene getView() {
        if(currentMap != null)
            return new MapCreatorView(this, currentMap);
        else
            return new MapCreatorView(this);
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

    public void storeSpot(Spot spot) {
        daoSpot.save(spot);
    }

    public void updateSpot(Spot spot) {
        daoSpot.update(spot);
    }

    public void removeSpot(Spot spot) {
        daoSpot.remove(spot);
    }

    public List<Spot> getAllSpots() {
        return daoSpot.findAll();
    }

    public void removeMapAndAllSpots() {
        daoMap.remove();
        daoSpot.removeAll();
    }

    public boolean isMapAlreadyCreated() {
        return currentMap != null;
    }

    public Window getMainWindow() {
        return homeController.getView().getWindow();
    }
}
