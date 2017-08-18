package com.anan_kobayashi.scalefree_network.backend;

import java.io.Serializable;
import java.util.*;


/**
 * @author marian
 *
 * The Class to generate a Scale-Free-Network. This is a network with power-law-distribution of the nodes degrees.
 * broadly speaking, this means there are only a few nodes with hundreds of connections and hundreds of nodes with only a few connections.
 *
 * @param <T> the type of nodes inside this network
 */
public class ScaleFreeNetwork<T> extends Network<T> implements Serializable {

    public ScaleFreeNetwork() {
        super();
    }

    public ScaleFreeNetwork(int size)
    {
        super();
        generate_default_network(size);
    }


    /**
     * generate a small initial network which has a circle connection
     * should be at least 3 nodes
     *
     */
    private void generate_initial_network() {
        final int INIT_SIZE = 3;
        /* we generate {seed_size} nodes and generate edges from 0 to 1, 1 to 2, ... m-1 to m and m to 1 */
        // TODO: barabasi-albert-algorithm could also work with 2 nodes? but in this case, the initial network would make a double edge
        // add a node
        for (int i = 0; i < INIT_SIZE; i++) {
            this.add_node(new Node<>());
            // add an edge from new node to previous node.
            if (this.get_nodes().size() > 1) // first node has no previous node, so add no edge here
            {
                add_edge(this.get_nodes().get(i), this.get_nodes().get(i - 1));
            }
        }
        // add edge from last node to first node, making a circle
        add_edge(this.get_nodes().get(this.get_nodes().size() - 1), this.get_nodes().get(0));
    }


    /**
     * barabasi-albert-algorithm to add a node by a power-law-probability
     *
     */
    private void add_scalefree_node() {
        Random rng = new Random(); // rng decides if this node connects to other nodes

        Node<T> new_node = new Node<>();
        double network_link_factor = 0;

        for (Node<T> curr : this.get_nodes()) {
            network_link_factor += curr.neighbours.size();
        }

        this.add_node(new_node);
        while (this.get_nodes().get(this.get_nodes().size() - 1).neighbours.size() == 0)  // retry as long the new node gets at least 1 connection
        {
            for (Node<T> current_node : this.get_nodes()) {
                double node_link_factor = current_node.neighbours.size();
                double probability = node_link_factor / network_link_factor;

                double rand = rng.nextDouble();
                if (rand <= probability && current_node != this.get_nodes().get(this.get_nodes().size() - 1)) // no self-loops
                {
                    add_edge(this.get_nodes().get(this.get_nodes().size() - 1), current_node);
                }
            }
        }
    }

    /**
     * using the initial network and the barabasi-albert-algorithm to create a ScaleFree-Network
     * @param size number of nodes in the network
     */
    public void generate_default_network(int size)
    {
        this.reset();
        this.generate_initial_network();
        for (int i = this.get_nodes().size(); i < size; i++) {
            this.add_scalefree_node();
        }
    }


    public void bind_element_to_node(Node<T> n, T element)
    {
        n.setElement(element);
    }

}
