package fr.iut.controller;


import fr.iut.App;
import javafx.beans.NamedArg;

public class StatisticsController implements ControllerInterface{

    private HomeController controller;

    public StatisticsController(@NamedArg("controller") HomeController controller) {
        this.controller = controller;
    }


    @Override
    public Object getView() {
        return null;
    }

    @Override
    public void finish() {

    }
}
