package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Map;

import java.util.List;

/**
 * Created by Sydpy on 2/28/17.
 */
public class MapDao extends GenericDAOImpl<Map, Integer>{

    public MapDao() {
        super(Map.class);
    }

    public Map getMap(){
        List<Map> all = findAll();

        return (all.isEmpty() ? null : all.get(0));
    }

    public boolean setMap(Map map){
        return deleteAll() && persist(map);
    }

    public boolean remove(){
        return deleteAll();
    }
}
