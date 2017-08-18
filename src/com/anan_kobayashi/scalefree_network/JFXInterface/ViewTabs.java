package com.anan_kobayashi.scalefree_network.JFXInterface;


import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 * Created by marian on 7/5/2017.
 *
 */
public class ViewTabs extends BaseView {

    TabPane tabPane = new TabPane();
    Tab tab_overview = new Tab("Overview");
 //   Tab tab_network = new Tab("Network");
    Tab tab_logging = new Tab("Logging");

    ViewTabs(Data data, Page owner)
    {
        super(data, owner);
        this.set_panel(new HBox());

        tabPane.getTabs().addAll(tab_overview, /* tab_network, */ tab_logging);
        setAllTabsClosable(false);

        this.get_panel().getChildren().addAll(tabPane);
        this.get_panel().setPrefHeight(40);
        this.get_panel().setMinHeight(40);
        this.get_panel().setMaxHeight(40);

    }

    private void setAllTabsClosable(boolean value)
    {
        for (Tab t: tabPane.getTabs()) {
            t.setClosable(value);
        }
    }
}
