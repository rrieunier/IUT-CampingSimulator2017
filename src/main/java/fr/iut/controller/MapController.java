package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.dao.impl.MapDao;
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
    private GenericDAOImpl<Location, Integer> daoLocation = new GenericDAOImpl<>(Location.class);

    private Map currentMap = null;

    public MapController(App app) {
        this.app = app;
        daoMap.open();
        currentMap = daoMap.getMap();
        daoMap.close();
    }

    @Override
    public Scene getView() {
        return mapCreatorView;
    }

    public void store(File mapFile, ArrayList<Location> locations) {
        daoMap.open();

        Map map = new Map();

        try {
            map.setImage(Files.readAllBytes(Paths.get(mapFile.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
            daoMap.close();
            return;
        }

        System.out.println("persist map...");
        daoMap.setMap(map);
        System.out.println("Done !");
        currentMap = map;

        daoMap.close();

        daoLocation.open();
        locations.forEach(daoLocation::saveOrUpdate);
        daoLocation.close();

        //TODO : saveOrUpdate map and locations in the bdd
    }

    @Override
    public void finish() {
        app.switchState(State.HOME);
    }

    public boolean isMapAlreadyCreated() {
        return currentMap != null;
    }
}
