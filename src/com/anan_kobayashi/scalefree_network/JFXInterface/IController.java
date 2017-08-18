package com.anan_kobayashi.scalefree_network.JFXInterface;

import javafx.event.EventHandler;
import javafx.event.EventType;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
interface IController {
    void bindEvent(javafx.scene.Node gui_item, EventType event_type, EventHandler<?> event_handler);
}
