package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Problem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by theo on 01/03/17.
 */
public class IncidentsController {

    /**
     * instance of the controller
     */
    HomeController homeController;

    /**
     * local list of problems
     */
    ArrayList<Problem> stub = new ArrayList<>();

    /**
     * DAO of Incidents
     */
    GenericDAO<Problem, Integer> daoIncidents = new GenericDAO<>(Problem.class);

    public IncidentsController(HomeController homeController) { this.homeController = homeController;}

    /**
     * get incidents of database and create in local
     */
    public void createIncidents(){
        stub = (ArrayList<Problem>) daoIncidents.findAll();
    }

    /**
     * @param p resolve an incident
     */
    public void resolveIncident(Problem p){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        p.setSolutionDatetime(currentTimestamp);

        stub.set(stub.indexOf(p), p);
        daoIncidents.save(p);
    }

    /**
     * @param sort_options selected sort method to sort the problem list
     */
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

    /**
     * @param problem create the incident and save it
     */
    public void saveIncident(Problem problem){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        problem.setAppearanceDatetime(currentTimestamp);

        daoIncidents.save(problem);
        stub.add(problem);
    }

    public ArrayList<Problem> getIncidents() {
        return stub;
    }
}
