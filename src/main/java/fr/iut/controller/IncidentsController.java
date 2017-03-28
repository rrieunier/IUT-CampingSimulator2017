package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Problem;
import fr.iut.view.IncidentsManagerView;
import javafx.scene.SubScene;

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
    private HomeController homeController;

    /**
     * local list of problems
     */
    private ArrayList<Problem> problems = new ArrayList<>();

    /**
     * DAO of Incidents
     */
    private GenericDAO<Problem, Integer> daoIncidents = new GenericDAO<>(Problem.class);

    public IncidentsController(HomeController homeController) { this.homeController = homeController;}

    public SubScene getView() {
        return new IncidentsManagerView(this);
    }

    /**
     * get incidents of database and create in local
     */
    public void createIncidents(){
        problems = (ArrayList<Problem>) daoIncidents.findAll();
    }

    /**
     * @param p resolve an incident
     */
    public void resolveIncident(Problem p){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        p.setSolutionDatetime(currentTimestamp);

        daoIncidents.update(p);
    }

    public void updateIncident(Problem p, String desc, String app, String sol){
        p.setDescription(desc);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(app);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            p.setAppearanceDatetime(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            parsedDate = dateFormat.parse(sol);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            p.setSolutionDatetime(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        daoIncidents.update(p);
    }

    /**
     * @param sort_options selected sort method to sort the problem list
     */
    public void sortIncidents(int sort_options){
        problems.sort(new Comparator<Problem>() {
            @Override
            public int compare(Problem o1, Problem o2) {
                int result;
                switch (sort_options){
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
                    default:
                        result = o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase());
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
    }

    public ArrayList<Problem> getIncidents() {
        return problems;
    }
}
