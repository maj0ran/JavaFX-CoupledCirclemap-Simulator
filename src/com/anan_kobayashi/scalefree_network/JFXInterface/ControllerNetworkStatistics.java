package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.Data;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerNetworkStatistics extends BaseController<ViewNetworkStatistics> {

    ControllerNetworkStatistics(Data data, ViewNetworkStatistics view, ControllerEventBus bus)
    {
        super(data, view, bus);

        ControllerCallback cb_reload = event -> { view.init_data(); };

        bus.register(ViewControllerEvent.ModelReloaded, cb_reload);

    }
}
