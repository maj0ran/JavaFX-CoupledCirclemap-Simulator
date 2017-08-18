package com.anan_kobayashi.scalefree_network.backend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.util.*;

/**
 *
 * Created by marian on 4/7/17.
 *
 */


public class Node<T> implements Comparable<Node>, Serializable {
    private static int id_counter = 0;

    Vector<Node<T>> neighbours; // items_list of adjacent Nodes, see "EdgeList"


    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleIntegerProperty degree = new SimpleIntegerProperty();
    private final SimpleObjectProperty<T> element = new SimpleObjectProperty<>();

    Node() {
        this.id.set(Node.id_counter);
        Node.id_counter++;
        this.neighbours = new Vector<>();
    }

    Node(T element) {
        this();
        this.element.set(element);
    }

    public int getId() {
        return id.get();
    }
    public void setId(int id) { this.id.set(id) ;}

    public T getElement() {
        return element.get();
    }
    public void setElement(T element) {
        this.element.set(element);
    }

    public int getDegree() {
        return this.neighbours.size();
    }

    public Vector<Node<T>> get_neighbours() {
        return this.neighbours;
    }


    static void reset_id_counter() {
        Node.id_counter = 0;
    }

    /**
     * BFS Algorithm with Traceback-Ability to get the shortest path and the distance from n1 to n2
     *
     * @param dest Find the shortest path to this node
     * @return Distance between both nodes; -1 if not found
     */
    public int calculate_distance(Node<?> dest) {


        Map<Node<T>, Node<T>> visited_nodes = new IdentityHashMap<>(); // value is the parent node of key to traceback the path
        Queue<Node<T>> queued_nodes = new PriorityQueue<>();

        visited_nodes.put(this, this); // first node is the parent of itself
        queued_nodes.add(this);
        int distance = 0;
        Node<T> current;

        while (!queued_nodes.isEmpty()) {
            current = queued_nodes.poll();
            if (current == dest) // found
            {
                Node traceback = dest; // set the traceback to find the way back
                while (traceback != this) {
                    traceback = visited_nodes.get(traceback);
                    distance++;
                }
                return distance;
            }
            for (Node<T> neighbor : current.get_neighbours()) {
                if (!visited_nodes.containsKey(neighbor)) {
                    visited_nodes.put(neighbor, current);
                    queued_nodes.add(neighbor);
                }
            }
        }
        return -1; // not found
    }

    /* make nodes sortable by ID */
    public int compareTo(Node n) {
        return this.id.get() - n.id.get();
    }
}



