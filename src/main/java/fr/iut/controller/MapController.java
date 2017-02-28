package fr.iut.controller;

import fr.iut.App;
import fr.iut.State;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Map;
import fr.iut.view.MapCreatorView;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MapController implements ControllerInterface {

    private App app;
    private MapCreatorView mapCreatorView = new MapCreatorView(this);
    private GenericDAOImpl<Map, Integer> daoMap = new GenericDAOImpl<>(Map.class);
    private GenericDAOImpl<Location, Integer> daoLocation = new GenericDAOImpl<>(Location.class);

    private boolean mapAlreadyCreated;

    public MapController(App app) {
        this.app = app;
        daoMap.open();
        mapAlreadyCreated = daoMap.findAll().size() > 0;
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
            return;
        }

        daoMap.persist(map);
        mapAlreadyCreated = true;

        daoMap.close();

        daoLocation.open();
        locations.forEach(daoLocation::persist);
        daoLocation.close();
    }

    @Override
    public void finish() {
        app.switchState(State.HOME);
    }

    public boolean isMapAlreadyCreated() {
        return mapAlreadyCreated;
    }
}
