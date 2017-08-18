package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.*;

/**
 *
 * Created by marian on 5/8/2017.
 *
 */

public class ViewOscillatorChart extends BaseView {

    public LineChart<Number, Number> chart;
    private NumberAxis time_axis; // x-direction, number of map-iterations
    private NumberAxis theta_axis; // y-direction, value of theta_n

    private long last_value_pos;


    public Map<Node<CircleMap>, XYChart.Series<Number, Number>> lines;
    private Map<XYChart.Series<Number, Number>, Color> color_of_line;

    public ViewOscillatorChart(Data data, Page owner) {

        super(data, owner);
        this.set_panel(new HBox(10));

        time_axis = new NumberAxis(0, 100, 1);
        time_axis.setLabel("time");

        theta_axis = new NumberAxis(0, 1, 0.1);
        theta_axis.setLabel("value");

        chart = new LineChart<Number, Number>(time_axis, theta_axis);

        chart.prefWidthProperty().bind(get_panel().prefWidthProperty());
        chart.prefHeightProperty().bind(get_panel().prefHeightProperty());
        chart.minHeightProperty().bind(get_panel().prefHeightProperty());

        this.add_elements(chart);

        init_chart_data();



    }

    void init_chart_data()
    {
        this.chart.getData().removeAll(this.chart.getData());
        this.lines = new IdentityHashMap<>();
        this.color_of_line = new HashMap<>();

        for (Node<CircleMap> n : data.network.get_nodes()) {
            lines.put(n, new XYChart.Series<Number, Number>());
            lines.get(n).getData().add(new XYChart.Data<Number, Number>(0, n.getElement().getPhase()));
        }
        // we use addAll after the loop instead of single add's inside the loop because this is _much_ faster
        chart.getData().addAll(lines.values());

        // we have to set the colors AFTER the lines are added to the chart, so unfortunately we need a second loop
        DistinctColorGenerator colorGenerator = new DistinctColorGenerator();
        for (Node<CircleMap> n : data.network.get_nodes()) {
            Color c = colorGenerator.nextColor();
            String rgb = String.format("-fx-stroke: #%02x%02x%02x;",
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255));

            lines.get(n).nodeProperty().get().setStyle(rgb);
            this.color_of_line.put(lines.get(n), c);
        }

        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);

        last_value_pos = 0;
        ((NumberAxis) chart.getXAxis()).setLowerBound(0);
        ((NumberAxis) chart.getXAxis()).setUpperBound(100);


    }


    public void add_phase_values() {
        for (Node<CircleMap> n : data.network.get_nodes()) {
            lines.get(n).getData().add(new XYChart.Data(last_value_pos, n.getElement().getPhase()));
            if (lines.get(n).getData().size() >= time_axis.getUpperBound() - time_axis.getLowerBound()) {
                time_axis.setUpperBound(time_axis.getUpperBound() + 1);
                time_axis.setLowerBound(time_axis.getLowerBound() + 1);
                for (Node<CircleMap> n_2 : data.network.get_nodes())
                    lines.get(n_2).getData().remove(0, 1);
            }
        }
        last_value_pos++;
    }

    public void set_visible_lines(List<Node<CircleMap>> nodes) {
        Set<XYChart.Series<Number, Number>> selected_lines = new HashSet<>();
        for (Node n : nodes) {
            selected_lines.add(this.lines.get(n));
        }

        this.chart.getData().removeAll(this.lines.values());
        this.chart.getData().addAll(selected_lines);

        for (XYChart.Series<Number, Number> line : selected_lines) {
            Color c = color_of_line.get(line);
            String rgb = String.format("-fx-stroke: #%02x%02x%02x;",
                    (int) (c.getRed() * 255),
                    (int) (c.getGreen() * 255),
                    (int) (c.getBlue() * 255));
            line.nodeProperty().get().setStyle(rgb);
        }
    }


}
