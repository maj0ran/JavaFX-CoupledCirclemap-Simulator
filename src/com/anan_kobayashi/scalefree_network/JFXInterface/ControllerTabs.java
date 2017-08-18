package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerTabs extends BaseController<ViewTabs> {


    public ControllerTabs(Data model, ViewTabs view, ControllerEventBus bus) {
        super(model, view, bus);

        view.tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if(newValue == view.tab_overview)
                {
                    post(ViewControllerEvent.TabClicked, PageIdentifier.overview);
                }
                if(newValue == view.tab_logging)
                {
                    post(ViewControllerEvent.TabClicked, PageIdentifier.logging);
                }

                post(ViewControllerEvent.TabFocusChanged, newValue); // super shitty workaround for the tab switch bug, but too lazy for doing it better
            }
        });


        ControllerCallback cb_change_tab = event -> {
            view.tabPane.getSelectionModel().clearSelection();
        };

        bus.register(ViewControllerEvent.TabFocusChanged, cb_change_tab);
        }
    }