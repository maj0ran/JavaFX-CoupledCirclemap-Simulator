package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.EventBusSubscriber;
import com.anan_kobayashi.scalefree_network.backend.Data;

import javafx.event.EventHandler;
import javafx.event.EventType;


/*
 *
 * Created by marian on 5/7/2017.
 *
 */
abstract class BaseController<V extends BaseView> extends EventBusSubscriber implements IController {

    private Data data;
    protected V view;

    public BaseController(Data model, V view, ControllerEventBus bus)
    {
        super(bus);
        this.data = model;
        this.view = view;
    }

    public void bindEvent(javafx.scene.Node gui_item, EventType event_type, EventHandler event_handler) {
        gui_item.addEventHandler(event_type, event_handler);
    }

    public V getView()
    {
        return this.view;
    }




}
