package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Problem;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by theo on 01/03/17.
 */
public class IncidentsController {

    HomeController homeController;

    ArrayList<Problem> stub = new ArrayList<>();

    GenericDAO<Problem, Integer> daoIncidents = new GenericDAO<>(Problem.class);

    public IncidentsController(HomeController homeController) { this.homeController = homeController;}

    public void createIncidents(){
        daoIncidents.open();
        stub = (ArrayList<Problem>) daoIncidents.findAll();
        daoIncidents.close();
    }

    public void resolveIncident(Problem p){
        p.setResolved(true);
        stub.set(stub.indexOf(p), p);

        daoIncidents.open();
        daoIncidents.saveOrUpdate(p);
        daoIncidents.close();
    }

    public void sortIncidents(int sort_options){
        stub.sort(new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                int result = 0;
                switch (sort_options){
                    default:
                        result = o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase());
                        break;
                    case 1:
                        result = o2.getDescription().toLowerCase().compareTo(o1.getDescription().toLowerCase());
                        break;
                    case 2:
                        result = o1.getAppearanceDatetime().compareTo(o2.getAppearanceDatetime());
                        break;
                    case 3:
                        result = o2.getAppearanceDatetime().compareTo(o1.getAppearanceDatetime());
                        break;
                    case 4:
                        result = o1.getSolutionDatetime().compareTo(o2.getSolutionDatetime());
                        break;
                    case 5:
                        result = o2.getSolutionDatetime().compareTo(o1.getSolutionDatetime());
                        break;
                }
                return result;
            }
        });
    }

    public void saveIncident(Problem problem){
        daoIncidents.open();
        daoIncidents.saveOrUpdate(problem);
        daoIncidents.close();
        stub.add(problem);
    }

    public ArrayList<Problem> getIncidents() {
        return stub;
    }
}
