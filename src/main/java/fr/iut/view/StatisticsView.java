package fr.iut.view;


import fr.iut.App;
import fr.iut.controller.StatisticsController;
import javafx.beans.NamedArg;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * the statistics screen
 */
public class StatisticsView extends SubScene {

    /**
     * width of the StatisticsView Subscene
     */
    public static double STATISTICS_W = App.SCREEN_W * 5 / 6;
    /**
     * height of the StatisticsView Subscene
     */
    public static double STATISTICS_H = App.SCREEN_H * 7 / 9;
    /**
     * instance of the StatisticsController
     */
    private StatisticsController controller;
    /**
     * vbox containing displayable statistics
     */
    private VBox statistics_box;
    /**
     * vbox containing diagrams and details of the selected statistic
     */
    private VBox details = new VBox();

    /**
     * @param controller
     */
    public StatisticsView(@NamedArg("controller") StatisticsController controller) {
        super(new VBox(), STATISTICS_W, STATISTICS_H);

        this.controller = controller;
        VBox components = (VBox) getRoot();

        VBox wrapper = new VBox();
        HeaderView header = new HeaderView("Statistiques");
        HBox body = new HBox();

        wrapper.setPrefSize(STATISTICS_W, STATISTICS_H);


        wrapper.getChildren().addAll(header, body);
        components.getChildren().add(wrapper);
    }
}
