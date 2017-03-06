package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Problem;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        daoIncidents.open();
        stub = (ArrayList<Problem>) daoIncidents.findAll();
        daoIncidents.close();
    }

    /**
     * @param id id of the incident to resolve
     */
    public void resolveIncident(int id){
        daoIncidents.open();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        Problem p = daoIncidents.findById(id);
        p.setSolutionDatetime(currentTimestamp);
        daoIncidents.saveOrUpdate(p);
        daoIncidents.close();
    }

    public void updateIncident(int id, String description, String appeareance, String solution){
        daoIncidents.open();
        Problem p = daoIncidents.findById(id);
        p.setDescription(description);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        try {
            Date parsedAppearanceDate = dateFormat.parse(appeareance);
            Timestamp timestamp = new Timestamp(parsedAppearanceDate.getTime());
            p.setAppearanceDatetime(timestamp);
            if(p.isSolved()){
                Date parsedSolutionDate = dateFormat.parse(solution);
                timestamp = new Timestamp(parsedSolutionDate.getTime());
                p.setSolutionDatetime(timestamp);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        daoIncidents.saveOrUpdate(p);
        daoIncidents.close();
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

        daoIncidents.open();
        daoIncidents.saveOrUpdate(problem);
        daoIncidents.close();
        stub.add(problem);
    }

    public ArrayList<Problem> getIncidents() {
        return stub;
    }
}
