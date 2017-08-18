package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author : marian
 * @date : 7/7/2017
 */
public class PageLogging extends Page {
    PageLogging(Data data, ControllerEventBus bus) {
        super(data, bus);
        this.setRoot(new GridPane());
        this.getRoot().setPadding(new Insets(0, 10, 0, 10));
    }

    void init_widgets()
    {
        ViewTabs view_tabs = new ViewTabs(data, this);
        ControllerTabs ctrl_tabs = new ControllerTabs(data, view_tabs, bus);

        ViewLogOscillator view_log_oscillator = new ViewLogOscillator(data, this);
        ControllerLogOscillator ctrl_log_oscillator = new ControllerLogOscillator(data, view_log_oscillator, bus);

        ViewLogSynchronization view_log_sync = new ViewLogSynchronization(data, this);
        ControllerLogSynchronization ctrl_log_sync = new ControllerLogSynchronization(data, view_log_sync, bus);

        GridPane ref = (GridPane)this.getRoot();

    //    ref.getChildren().addAll(view_tabs.get_panel(), view_log_oscillator.get_panel());
        ref.add(view_tabs.get_panel(), 0, 0);
        ref.add(view_log_oscillator.get_panel(), 0, 1);
        ref.add(view_log_sync.get_panel(), 0, 2);
        view_log_oscillator.setRelativeWidth(0.2);
        view_log_sync.setRelativeWidth(0.2);

    }

}
