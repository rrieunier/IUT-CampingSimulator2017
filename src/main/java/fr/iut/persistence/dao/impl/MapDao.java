package fr.iut.persistence.dao.impl;

import fr.iut.persistence.entities.Map;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Sydpy on 2/28/17.
 */
public class MapDao extends GenericDAOImpl<Map, Integer>{

    public MapDao() {
        super(Map.class);
    }

    @Transactional(rollbackOn = Exception.class)
    public Map getMap(){
        List<Map> all = findAll();

        return (all.isEmpty() ? null : all.get(0));
    }

    @Transactional(rollbackOn = Exception.class)
    public void setMap(Map map){
        removeAll();
        saveOrUpdate(map);
    }

    @Transactional(rollbackOn = Exception.class)
    public void remove(){
        removeAll();
    }
}
