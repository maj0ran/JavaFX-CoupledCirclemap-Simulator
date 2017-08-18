package com.anan_kobayashi.scalefree_network;

import com.anan_kobayashi.scalefree_network.JFXInterface.GraphicalInterface;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import com.anan_kobayashi.scalefree_network.backend.ScaleFreeNetwork;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // This is the pure data for the application. In this case the Scale-Free Network
    Data data;

    // this is the GUI for the application. It generates layout, the buttons and the chatrts
    GraphicalInterface gui;


    // this function gets executed at the beginning of launch(). launch() then enters a endless-loop for the GUI
    @Override
    public void start(Stage primaryStage) throws Exception {

        int network_size = 100;
        ScaleFreeNetwork<CircleMap> nw = new ScaleFreeNetwork<>();

       // nw = load_previous_network(network_size, "network_".concat(String.valueOf(network_size).concat(".sif")));
        nw.import_from_sif("default.sif");
        // set default Circlemap for all nodes
        for(Node<CircleMap> n : nw.get_nodes())
        {
            n.setElement(new CircleMap());
        }

        data = new Data(nw);
        gui = new GraphicalInterface(primaryStage, data);

    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void stop()
    {
        gui.bus.post(ViewControllerEvent.ProgramQuit, null);
    }



    /**
     * Generates a default networking with given amount of nodes and populates all nodes with default CircleMaps
     * @param network_size number of nodes
     * @return The Network
     */
/*    public ScaleFreeNetwork<CircleMap> generate_new_network(int network_size) {
        // generate the network structure
        ScaleFreeNetwork<CircleMap> nw = new ScaleFreeNetwork<>(network_size);
        // populate all nodes with circlemaps
        for(Node<CircleMap> n : nw.get_nodes())
            nw.bind_element_to_node(n, new CircleMap());

        return nw;
    }

    public ScaleFreeNetwork<CircleMap> load_previous_network(int network_size, String filepath) throws IOException {
        ScaleFreeNetwork<CircleMap> nw = new ScaleFreeNetwork<>();
        nw.import_from_sif(filepath);
        // populate all nodes with circlemaps
        for(Node<CircleMap> n : nw.get_nodes())
            nw.bind_element_to_node(n, new CircleMap(0, 1, 0.1));

        nw.get_node(0).getElement().setExternalFrequency(0.18);

        return nw;
    }*/
}
