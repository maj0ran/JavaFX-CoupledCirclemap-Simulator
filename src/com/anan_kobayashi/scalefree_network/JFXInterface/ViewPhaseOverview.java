package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.ComplexNumber;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.*;

/**
 * @author : marian
 * @date : 7/7/2017
 */


public class ViewPhaseOverview extends BaseView {

    private DoubleProperty radius;

    private Circle phase_circle;
    private Map<Node<CircleMap>, Circle> visual_nodes;
    private Line visual_order_parameter;

    public ViewPhaseOverview(Data data, Page owner) {
        super(data, owner);
        this.set_panel(new StackPane());



        this.radius = new SimpleDoubleProperty(0);
        this.radius.bind(this.get_panel().prefHeightProperty());
        // the unit circle showing the phase
        phase_circle = new Circle(this.getRadius());
        phase_circle.radiusProperty().bind(this.radius);
        phase_circle.setStroke(Color.rgb(30, 30, 30));
        phase_circle.setFill(Color.rgb(255, 255, 255));




        // adding a cross inside the circle for visual enhancement
        Line horizontal_center = new Line(phase_circle.getCenterX() - getRadius(), phase_circle.getCenterY(),
                phase_circle.getCenterX() + getRadius(), phase_circle.getCenterY());


        horizontal_center.startXProperty().bind(phase_circle.centerXProperty().subtract(this.radius));
        horizontal_center.startYProperty().bind(phase_circle.centerYProperty());
        horizontal_center.endXProperty().bind(phase_circle.centerYProperty().add(this.radius));
        horizontal_center.endYProperty().bind(phase_circle.centerYProperty());

        horizontal_center.setStrokeWidth(0.5);
        horizontal_center.setFill(Color.rgb(0xA0, 0xA0, 0xA0));
        horizontal_center.setStroke(Color.rgb(0xA0, 0xA0, 0xA0));


        Line vertical_center = new Line(phase_circle.getCenterX(), phase_circle.getCenterY() - getRadius(),
                phase_circle.getCenterX(), phase_circle.getCenterY() + getRadius());

        vertical_center.startXProperty().bind(phase_circle.centerXProperty());
        vertical_center.startYProperty().bind(phase_circle.centerYProperty().subtract(this.radius));
        vertical_center.endXProperty().bind(phase_circle.centerYProperty());
        vertical_center.endYProperty().bind(phase_circle.centerYProperty().add(this.radius));

        vertical_center.setStrokeWidth(0.5);
        vertical_center.setFill(Color.rgb(0x80, 0x80, 0x80));
        vertical_center.setStroke(Color.rgb(0x80, 0x80, 0x80));

        Label phase_right = new Label("0.0");
        phase_right.translateXProperty().bind(this.radius.subtract(10));

        Label phase_up = new Label("0.25");
        phase_up.translateYProperty().bind(this.radius.multiply(-1).add(10));

        Label phase_left = new Label("0.5");
        phase_left.translateXProperty().bind(this.radius.multiply(-1).add(10));

        Label phase_down = new Label("0.75");
        phase_down.translateYProperty().bind(this.radius.subtract(10));


        this.add_elements(phase_circle, horizontal_center, vertical_center, phase_right, phase_up, phase_left, phase_down);
        this.init_visual_nodes();

    }

    void init_visual_nodes()
    {
       // this.get_panel().getChildren().remove(3, this.get_panel().getChildren().size());
        if(visual_nodes != null) {
            this.get_panel().getChildren().removeAll(visual_nodes.values());
            this.get_panel().getChildren().removeAll(visual_order_parameter);
        }
         /*
        for every data-node, draw a visualized node
        Every Node is visualized as a small circle. Its Theta-Value is the position on the big circle with 0,1 = 90 degree (i think)
         */
        visual_nodes = new IdentityHashMap<Node<CircleMap>, Circle>(); // map data-node to the visual node
        DistinctColorGenerator color_maker = new DistinctColorGenerator(); // generator for the node-colors

        for (Node<CircleMap> node : data.network.get_nodes()) {
            Circle visualized_node = new Circle(5); // every node is visualized as a small circle
            visualized_node.setFill(color_maker.nextColor());

            this.add_elements(visualized_node); // add the visual node to the pane (draws it)
            this.visual_nodes.put(node, visualized_node); // connect the visual node with the data-node
        }

        // the line that shows the synchronicity of all nodes by the order parameter
        ComplexNumber order_parameter = this.data.handler.calculate_order_parameter(data.network.get_nodes());

        visual_order_parameter = new Line(
                phase_circle.getCenterX(),
                phase_circle.getCenterY(),
                (phase_circle.getCenterX() + order_parameter.real) * this.getRadius(),
                (phase_circle.getCenterX() + order_parameter.imaginary) * this.getRadius()
        );

        visual_order_parameter.setStrokeWidth(2);
        visual_order_parameter.setFill(Color.rgb(0, 0, 255));
        visual_order_parameter.setStroke(Color.rgb(0, 0, 255));

        this.add_elements(visual_order_parameter);

        this.redraw(); // calculate the intial positions and draw
    }

    /* This function catches the theta-values from the data-visual_nodes and updates visual_nodes */
    public void redraw() {
        for (Node<CircleMap> node : data.network.get_nodes()) {
            double pos_x = Math.cos((node.getElement().getPhase() * 2 * Math.PI)) * this.getRadius();
            double pos_y = Math.sin((node.getElement().getPhase() * 2 * Math.PI)) * this.getRadius();

            this.visual_nodes.get(node).setTranslateX(pos_x);
            this.visual_nodes.get(node).setTranslateY(-pos_y);
        }


        ComplexNumber order_parameter = this.data.handler.calculate_order_parameter(data.network.get_nodes());
        visual_order_parameter.setEndX((phase_circle.getCenterX() + order_parameter.real) * this.getRadius());
        visual_order_parameter.setEndY(-((phase_circle.getCenterY() + order_parameter.imaginary) * this.getRadius()));

        visual_order_parameter.setTranslateX(visual_order_parameter.getEndX() / 2);
        visual_order_parameter.setTranslateY(visual_order_parameter.getEndY() / 2);
    }


    public void set_visible_nodes(List<Node<CircleMap>> nodes) {
        for (Node n : this.visual_nodes.keySet()) {
            this.visual_nodes.get(n).setOpacity(0);
        }
        for (Node n : nodes) {
            this.visual_nodes.get(n).setOpacity(1);
        }
    }

    public void setRadius(double radius) {
        this.radius.setValue(radius);
    }

    public double getRadius() {
        return this.radius.get();
    }

}
