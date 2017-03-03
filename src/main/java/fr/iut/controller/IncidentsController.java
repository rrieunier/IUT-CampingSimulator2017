package fr.iut.controller;

import fr.iut.persistence.entities.Problem;
import fr.iut.view.IncidentsManagerView;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by theo on 01/03/17.
 */
public class IncidentsController {

    HomeController homeController;

    ArrayList<Problem> stub = new ArrayList<>();

    public IncidentsController(HomeController homeController) { this.homeController = homeController;}

    public void createIncidents(){
        for (int i = 0; i < 50; i++) {
            Problem problem = new Problem();
            problem.setDescription("Description" + i);
            if (i % 2 == 1)
                problem.setState("Non résolu");
            else
                problem.setState("Résolu");
            stub.add(problem);
        }
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

    public ArrayList<Problem> getIncidents() {
        return stub;
    }
}
