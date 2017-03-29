package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Map;

import java.util.List;

/**
 * Created by Sydpy on 2/28/17.
 */
public class MapDao extends GenericDAO<Map, Integer> {

    /**
     * Constructor.
     */
    public MapDao() {
        super(Map.class);
    }

    /**
     * @return The Map stored in database.
     */
    public Map getMap(){
        List<Map> all = findAll();

        return (all.isEmpty() ? null : all.get(0));
    }

    /**
     * @param map Update the map stored in database.
     */
    public void setMap(Map map){

        removeAll();
        save(map);
    }

    /**
     * Removes the Map from database.
     */
    public void remove(){
        removeAll();
    }
}
