package com.anan_kobayashi.scalefree_network.JFXInterface;

/*
 * @author : marian
 * @date : 3/31/17
 */


import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.stage.Stage;


public class GraphicalInterface {
    final public Data data;

    public ControllerEventBus bus = new ControllerEventBus();


    public GraphicalInterface(Stage primary_stage, Data data) {
        this.data = data; // attach the backend data

        // create the main window
        Window window_main = new Window(primary_stage, bus, "Kobayashi Simulator");

        /* create the pages */
        PageOverview page_overview = new PageOverview(data, bus);
        PageLogging page_logging = new PageLogging(data, bus);


        window_main.addPage(PageIdentifier.overview, page_overview);
        window_main.addPage(PageIdentifier.logging, page_logging);

        window_main.setPage(PageIdentifier.overview);

        page_overview.init_widgets();
        page_logging.init_widgets();
    }
}
