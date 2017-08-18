package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;

import java.util.List;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerOscillatorChart extends BaseController {
    public ControllerOscillatorChart(Data model, ViewOscillatorChart view, ControllerEventBus bus) {
        super(model, view, bus);

        ControllerCallback update_cb = event -> view.add_phase_values();
        ControllerCallback shownodes_cb = event -> view.set_visible_lines((List<Node<CircleMap>>) event);
        ControllerCallback load_chart = event -> view.init_chart_data();

        bus.register(ViewControllerEvent.NodeParameterChanged, update_cb);
        bus.register(ViewControllerEvent.VisibleNodesChanged, shownodes_cb);
        bus.register(ViewControllerEvent.ModelReloaded, load_chart);
    }
}
