package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;

/**
 * @author : marian
 * @date : 7/5/2017
 */

public class Widget {
    final private BaseView view;
    final private BaseController controller;
    ControllerEventBus bus;

    Widget(BaseView view, BaseController controller) {
        this.view = view;
        this.controller = controller;
    }

    BaseView getView()
    {
        return this.view;
    }
}
