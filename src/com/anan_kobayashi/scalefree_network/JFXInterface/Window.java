package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author : marian
 * @date : 7/6/2017
 */

public class Window {
    private Stage stage;
    private Scene scene;

    private ControllerEventBus bus;
    private HashMap<PageIdentifier, Page> pages;

    public Window(Stage stage, ControllerEventBus bus) {
        this.stage = stage;

        this.scene = new Scene(new Pane());
        this.scene.getStylesheets().add("style.css");
        this.stage.setScene(this.scene);

        bus.register(ViewControllerEvent.TabClicked, event_data -> { this.setPage((PageIdentifier) event_data); });

        this.pages = new HashMap<>();
        this.stage.show();
    }

    public Window(Stage stage, ControllerEventBus bus, String title) {
        this(stage, bus);
        this.setTitle(title);
    }

    void addPage(PageIdentifier name, Page p)
    {
        this.pages.put(name, p);
        p.setOwner(this);
    }



    void setPage(PageIdentifier name) {
        Page p = this.pages.get(name);
        this.scene.setRoot(p.getRoot());
    }

    Stage getStage()
    {
        return this.stage;
    }

    Scene getScene()
    {
        return this.scene;
    }

    public void setTitle(String title) {
        this.stage.setTitle(title);
    }


}
