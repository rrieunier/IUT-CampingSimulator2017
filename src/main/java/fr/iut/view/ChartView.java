package fr.iut.view;

import com.mysql.cj.api.x.Table;
import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.dao.impl.GenericDAOImpl;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;


public class ChartView extends HBox {

    private Chart chart;
    private TableView<ObservableEntity> table = new TableView<ObservableEntity>();
    private TableColumn strColumn;
    private TableColumn fltColumn;

    public ChartView(ChartType type,
                     ArrayList<String> objects,
                     ArrayList<Float> comparative,
                     String title,
                     String firstColName, String scdColName) {

        switch (type) {
            case PIE:
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(); // données observables
                for (int i = 0 ; i < (objects.size() > 7 ? 7 : objects.size()) ; i++ ){
                    pieChartData.add(new PieChart.Data(objects.get(i), comparative.get(i)));
                }

                if (objects.size() > 7) {
                    float sum = 0;
                    for (Float i : comparative.subList(7, comparative.size()))
                        sum += i;
                    sum = sum / comparative.subList(7, comparative.size()).size() + 1;
                    pieChartData.add(new PieChart.Data("Others", sum));
                }

                chart = new PieChart(pieChartData);
                chart.setLegendSide(Side.RIGHT);
                chart.setPrefSize(HomeView.TAB_CONTENT_W / 2, HomeView.TAB_CONTENT_H * 15 / 20);
                chart.getStylesheets().add(new File("res/style.css").toURI().toString());
                chart.getStyleClass().add("pie-chart");
                chart.setTitle(title);

                ObservableList<ObservableEntity> data = FXCollections.observableArrayList();
                for (int i = 0 ; i < objects.size() ; i++) {
                    data.add(new ObservableEntity(objects.get(i), comparative.get(i)));
                }

                strColumn = new TableColumn(firstColName.equals("") ? "Nom" : firstColName);
                fltColumn = new TableColumn(scdColName.equals("") ? "Nombre" : scdColName);
                strColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                fltColumn.setPrefWidth(HomeView.TAB_CONTENT_W / 6);
                strColumn.setCellValueFactory(new PropertyValueFactory<>("str"));
                fltColumn.setCellValueFactory(new PropertyValueFactory<>("flt"));

                table.getColumns().addAll(strColumn, fltColumn);
                table.setItems(data);

                break;

            case LINE:
                break;

            case BAR:
                break;
        }
    }

    public void setLegendSide(Side side) { chart.setLegendSide(side); }

    public Chart getChart() {
        return chart;
    }

    public TableView getTable() {
        return table;
    }

    public static class ObservableEntity {
            private final SimpleStringProperty str;
            private final SimpleFloatProperty flt;

            private ObservableEntity(String str, Float flt) {
                this.str = new SimpleStringProperty(str);
                this.flt= new SimpleFloatProperty(flt);
            }

            public String getStr() { return str.get(); }
            public SimpleStringProperty strProperty() { return this.str; }

            public Float getFlt() { return flt.get(); }
            public SimpleFloatProperty fltProperty() { return this.flt; }
    }
}
