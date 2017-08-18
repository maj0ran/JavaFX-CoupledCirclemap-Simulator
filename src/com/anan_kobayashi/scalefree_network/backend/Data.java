package com.anan_kobayashi.scalefree_network.backend;

/**
 *
 * Created by marian on 4/5/17.
 *
 */

public class Data {
    public ScaleFreeNetwork<CircleMap> network;
    public CirclemapNetworkHandler handler; // includes the function to oscillate the whole network


    public Data(ScaleFreeNetwork<CircleMap> network) {
        this.network = network;
        this.handler = new CirclemapNetworkHandler(network);
    }
}
