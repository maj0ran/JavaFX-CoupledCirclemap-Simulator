package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.collections.ObservableList;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerPhaseOverview extends BaseController {

    public ControllerPhaseOverview(Data model, ViewPhaseOverview view, ControllerEventBus bus)
    {
        super(model, view, bus);

        ControllerCallback cb_redraw = event -> view.redraw();

        ControllerCallback cb_shownodes = (Object event) -> {
            ObservableList<Node<CircleMap>> selected_nodes = (ObservableList<Node<CircleMap>>)event;
            view.set_visible_nodes(selected_nodes);
        };

        ControllerCallback cb_reload = event -> { view.init_visual_nodes(); };

        bus.register(ViewControllerEvent.NodeParameterChanged, cb_redraw);
        bus.register(ViewControllerEvent.VisibleNodesChanged, cb_shownodes);
        bus.register(ViewControllerEvent.ModelReloaded, cb_reload);

    }
}
