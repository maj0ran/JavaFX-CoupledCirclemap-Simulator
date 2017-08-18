package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.JFXInterface.compositions.InformationLabel;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author : marian
 * @date : 7/7/2017
 */
public class ViewNetworkStatistics extends BaseView {

    LineChart<Number, Number> degree_distribution_chart;

    InformationLabel amount_nodes = new InformationLabel("Number of Nodes:", String.valueOf(data.network.get_nodes().size()));
    XYChart.Series<Number, Number> degree_distribution_values = new XYChart.Series<>();

    ViewNetworkStatistics(Data data, Page owner) {
        super(data, owner);
        set_panel(new VBox(10));


        NumberAxis degree_distribution_degree = new NumberAxis(1, 1, 1);
        NumberAxis degree_distribution_amount_of_nodes = new NumberAxis(0, 1, 1);
        // add the axis: X is degree, Y is amount of nodes
        degree_distribution_chart = new LineChart<Number, Number>(degree_distribution_degree, degree_distribution_amount_of_nodes);

        init_data();

        degree_distribution_values.nodeProperty().get().setStyle("-fx-stroke: #ff0000;");
        degree_distribution_chart.setAnimated(false);
        degree_distribution_chart.setCreateSymbols(false);
        degree_distribution_chart.setLegendVisible(false);
        degree_distribution_chart.setTitle("Degree Distribution");

        get_panel().getChildren().addAll(amount_nodes.getItem(), degree_distribution_chart);
    }

    void init_data()
    {
        amount_nodes.getInformation().setText(String.valueOf(data.network.get_nodes().size()));

        degree_distribution_chart.getData().removeAll(degree_distribution_chart.getData());
        degree_distribution_values.getData().removeAll(degree_distribution_values.getData());
        // find the highest degree to size the linechart properly
        int highest_degree = data.network.get_highest_degree_node().getDegree();
        NumberAxis ref = (NumberAxis)degree_distribution_chart.getXAxis();
        ref.setUpperBound(highest_degree);


        // find the most common degree to size the linechart properly
        int most_common_degree = 0;
        for (int i = 0; i <= highest_degree; i++) {
            int it_degree = data.network.get_nodes_by_degree(i).size();
            if (most_common_degree < it_degree) {
                most_common_degree = it_degree;
            }
        }

        ref = (NumberAxis)degree_distribution_chart.getYAxis();
        ref.setUpperBound(most_common_degree);


        // add the data
        for (int i = 1; i <= highest_degree; i++) {
            degree_distribution_values.getData().add(new XYChart.Data<>(i, data.network.get_nodes_by_degree(i).size()));
        }

        degree_distribution_chart.getData().add(degree_distribution_values);


    }

}
