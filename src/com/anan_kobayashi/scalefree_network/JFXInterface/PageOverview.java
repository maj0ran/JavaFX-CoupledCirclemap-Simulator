package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * Created by marian on 7/6/2017.
 *
 */
public class PageOverview extends Page {





    public PageOverview(Data data, ControllerEventBus bus) {

        super(data, bus);

        this.setRoot(new GridPane());
        this.getRoot().setPadding(new Insets(5, 5, 5, 5));
        ((GridPane)this.getRoot()).setHgap(10);
    }

    public void init_widgets()
    {


        ViewOscillatorChart oscillator_chart = new ViewOscillatorChart(data, this);
        ControllerOscillatorChart ctrl_oscillator_chart = new ControllerOscillatorChart(data, oscillator_chart, bus);
  //      Widget OscillatorChart = new Widget(oscillator_chart, ctrl_oscillator_chart);

        ViewPhaseOverview phase_overview = new ViewPhaseOverview(data, this);
        ControllerPhaseOverview ctrl_phase_overview = new ControllerPhaseOverview(data, phase_overview, bus);

        ViewExecutionButtons view_exec_buttons = new ViewExecutionButtons(data, this);
        ControllerExecutionButtons ctrl_exec_buttons = new ControllerExecutionButtons(data, view_exec_buttons, bus);

        ViewNodeParameters node_parameters = new ViewNodeParameters(data, this);
        ControllerNodeParameters controller_node_parameters = new ControllerNodeParameters(data, node_parameters, bus);

        ViewNetworkParameters network_settings_panel = new ViewNetworkParameters(data, this);
        ControllerNetworkParameters ctrl_network_settings = new ControllerNetworkParameters(data, network_settings_panel, bus);

        ViewNetworkStatistics network_stats = new ViewNetworkStatistics(data, this);
        ControllerNetworkStatistics ctrl_network_stats = new ControllerNetworkStatistics(data, network_stats, bus);


        // Tabs, obviously
        ViewTabs tabs = new ViewTabs(data, this);
        ControllerTabs tabs_ctrl = new ControllerTabs(data, tabs, bus);

        // add all views to the page
        GridPane pane = (GridPane)this.getRoot(); // reference
        pane.add(view_exec_buttons.get_panel(),        0, 2, 1, 1);
        pane.add(phase_overview.get_panel(),           0, 3, 1, 1);
        pane.add(node_parameters.get_panel(),          2, 3, 1, 1);
        pane.add(network_settings_panel.get_panel(),   3, 3, 1, 1);
        pane.add(network_stats.get_panel(),            4, 3, 1 ,1);
        pane.add(oscillator_chart.get_panel(),         0, 4, 5, 1);
        pane.add(tabs.get_panel(),                     0, 1, 5, 1);



        oscillator_chart.setRelativeWidth(1.0);
        oscillator_chart.setRelativeHeight(0.4);

        phase_overview.setRelativeHeight(0.25);

        tabs.setRelativeWidth(1.0);

        phase_overview.redraw(); // workaround to set the phase circles properly after rescaling
    }
}