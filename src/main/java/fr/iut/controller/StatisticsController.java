package fr.iut.controller;


import fr.iut.App;
import javafx.beans.NamedArg;

public class StatisticsController {

    private App app;
    private HomeController controller;

    public StatisticsController(@NamedArg("app") App app,
                                @NamedArg("controller") HomeController controller) {
        this.app = app;
        this.controller = controller;
    }


}
