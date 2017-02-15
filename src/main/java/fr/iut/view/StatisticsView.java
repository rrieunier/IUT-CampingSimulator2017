package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.HomeController;
import fr.iut.controller.StatisticsController;
import javafx.beans.NamedArg;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StatisticsView extends SubScene {

    public static double STATISTICS_W = App.SCREEN_W * 5 / 6;
    public static double STATISTICS_H = App.SCREEN_H * 7 / 9;

    private StatisticsController controller;

    private VBox statistics_box;
    private VBox details = new VBox();

    public StatisticsView(@NamedArg("controller") StatisticsController controller) {
        super(new AnchorPane(), STATISTICS_W, STATISTICS_H);

        this.controller = controller;
        AnchorPane components = (AnchorPane) getRoot();

        VBox wrapper = new VBox();
        HeaderView header = new HeaderView("Statistiques");
        HBox body = new HBox();

        wrapper.setPrefSize(STATISTICS_W, STATISTICS_H);

        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);
    }
}
