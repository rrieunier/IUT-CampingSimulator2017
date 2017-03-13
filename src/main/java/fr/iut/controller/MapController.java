package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.MapDao;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Map;
import fr.iut.view.MapCreatorView;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class MapController implements ControllerInterface {

    private App app;
    private MapCreatorView mapCreatorView = new MapCreatorView(this);
    private MapDao daoMap = new MapDao();
    private GenericDAO<Location, Integer> daoLocation = new GenericDAO<>(Location.class);

    private Map currentMap = null;

    public MapController(App app) {
        this.app = app;
        currentMap = daoMap.getMap();
    }

    @Override
    public Scene getView() {
        return mapCreatorView;
    }

    public void store(File mapFile, ArrayList<Location> locations) {

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

        locations.forEach(daoLocation::save);

        //TODO : save map and locations in the bdd
    }

    @Override
    public void finish() {
        app.switchState(State.HOME);
    }

    public boolean isMapAlreadyCreated() {
        return currentMap != null;
    }
}
