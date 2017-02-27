package fr.iut.view;

import fr.iut.controller.StatisticsController;
import javafx.beans.NamedArg;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class ChartView extends HBox {

    private Chart chart;
    private TableView table;

    public ChartView(Chart chart, TableView table){
        this.chart = chart;
        this.table = table;
    }

    public Chart getChart() {
        return chart;
    }

    public TableView getTable() {
        return table;
    }
}
