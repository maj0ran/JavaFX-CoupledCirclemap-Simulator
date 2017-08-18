package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 *
 * Created by marian on 7/5/2017.
 *
 *
 */
public abstract class Page {

    private Pane root;
    Data data;
    ControllerEventBus bus;
    private Window owner;

    Page(Data data, ControllerEventBus bus)
    {
        this.setRoot(new Pane());
        this.data = data;
        this.bus = bus;
    }

    final Pane getRoot()
    {
        return this.root;
    }

    void setRoot(Pane pane)
    {
        this.root = pane;
    }

    abstract void init_widgets();

    void setOwner(Window w)
    {
        this.owner = w;
    }

    Window getOwner()
    {
        return this.owner;
    }

}
