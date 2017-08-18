package com.anan_kobayashi.scalefree_network.backend;

import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * Created by marian on 5/7/2017.
 *
 */


public class CirclemapNetworkHandler {

    private Network<CircleMap> network;
    private double coupling_strength = 0;

    public CirclemapNetworkHandler(Network<CircleMap> nw) {
        this.network = nw;
    }

    public Network<CircleMap> getNetwork() {
        return network;
    }

    public void setNetwork(Network<CircleMap> network) {
        this.network = network;
    }


    // setter for the coupling srength
    public void set_coupling_strength(double val) {
        this.coupling_strength = val;
    }
    public final double get_coupling_strength() {
        return this.coupling_strength;
    }


    // oscilliate a single node which is coupled by the Kuramoto Model
    private double oscillate_node_by_kuramoto(Node<CircleMap> n) {
        double kuramoto_coupling = 0;
        for (Node<CircleMap> linked_node : n.neighbours) {
            kuramoto_coupling += (linked_node.getElement().getPhase() - n.getElement().getPhase());
        }

        kuramoto_coupling *= coupling_strength / n.neighbours.size();
        double next_val = n.getElement().get_next_val() + kuramoto_coupling;
        next_val = (next_val % 1 + 1) % 1;
        return next_val;
    }


    // oscilliate all nodes in the network with Kuramoto Model Coupling


    public void oscillate_network() {
        Map<Node<CircleMap>, Double> new_values = new HashMap<>();

        for (Node<CircleMap> current_node : this.network.get_nodes()) {
            new_values.put(current_node, this.oscillate_node_by_kuramoto(current_node));
        }

        for (Node<CircleMap> current_node : this.network.get_nodes()) {
            current_node.getElement().setPhase(new_values.get(current_node));
        }

    }

    /**
     * Calculate the order Parameter of the given set of nodes
     *
     * @param nodes set of nodes
     * @return the order parameter as Complex Number
     */
    public ComplexNumber calculate_order_parameter(ArrayList<Node<CircleMap>> nodes) {
        double avg_pos_x = 0;
        double avg_pos_y = 0;

        for (Node<CircleMap> n : nodes) {
            double pos_x = Math.cos((n.getElement().getPhase()) * 2 * Math.PI);
            double pos_y = Math.sin((n.getElement().getPhase()) * 2 * Math.PI);

            avg_pos_x += pos_x;
            avg_pos_y += pos_y;
        }


        avg_pos_x = avg_pos_x / nodes.size();
        avg_pos_y = avg_pos_y / nodes.size();

        return new ComplexNumber(avg_pos_x, avg_pos_y);

    }

    public double get_synchronity_measure(ComplexNumber z) {
        return Math.sqrt(z.real * z.real + z.imaginary * z.imaginary);
    }
}

