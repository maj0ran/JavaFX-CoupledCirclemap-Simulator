package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by marian on 6/17/2017.
 *
 */

class ControllerNodeParameters extends BaseController<ViewNodeParameters> {


    private EventHandler<KeyEvent> event_textfield_phase_enter;
    private EventHandler<KeyEvent> event_textfield_external_frequency_enter;
    private EventHandler<KeyEvent> event_textfield_show_distance_enter;
    private EventHandler<MouseEvent> event_btn_show_nodes_click;

    public ControllerNodeParameters(Data data, ViewNodeParameters view, ControllerEventBus bus) {
        super(data, view, bus);


        /*
         *
         * This Event manages the logic when the Textfield for the Current Phase is selected and the user presses tne Enter_Key
         * After pressing enter, it catches the current number in the TextField and the selected Nodes in the TableView.
         * Then it it sets the Phase-Parameter of all selected Nodes in the model to the new value.
         * At last, it posts the message to the other controllers that the values have been changed by the user.
         *
         */
        event_textfield_phase_enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // get the value from TextField
                    double new_phase = Double.valueOf(view.current_phase.getTextfield().getCharacters().toString());

                    // get the selected nodes
                    ObservableList<Node<CircleMap>> selected_nodes = view.table_parameters.getSelectionModel().getSelectedItems();
                    if (!selected_nodes.isEmpty()) // only execute when at least one node is selected
                    {
                        for (Node<CircleMap> n : selected_nodes)
                        {
                            n.getElement().setPhase(new_phase);
                        }
                        post(ViewControllerEvent.NodeParameterChanged, null);
                    }
                }
            }
        };


        /*
         *
         * This Event manages the logic when the Textfield for the External Frequency is selected and the user presses the Enter_Key.
         * After pressing enter, it catches the current number in the TextField for External Frequency and the selected Nodes in the TableView.
         * it sets the External Frequency of all selected nodes to the new value
         *
         */
        event_textfield_external_frequency_enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // get the value from TextField
                    double new_ext_freq = Double.valueOf(view.external_frequency.getTextfield().getCharacters().toString());

                    // get the selected nodes
                    ObservableList<Node<CircleMap>> selected_nodes = view.table_parameters.getSelectionModel().getSelectedItems();
                    if (!selected_nodes.isEmpty()) // only execute when at least one node is selected
                    {
                        for (Node<CircleMap> n : selected_nodes)
                        {
                            n.getElement().setExternalFrequency(new_ext_freq);
                        }
                    }
                }
            }
        };

        /*
         *
         * This Event manages the logic when the Textfield for showing the Distance is selected and the user presses the Enter_Key.
         * After pressing enter, it catches the current number in the TextField, which should be a valid Node ID.
         * It extracts the Node with the correspondent ID from the Model and calculated the distance from this node to all other nodes.
         * It then rebuilds the TableView with the lasst column set to show the distance to all nodes from the selected node.
         *
         */
        event_textfield_show_distance_enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // get the node from the textfield
                    Node<?> n = data.network.get_node(Integer.valueOf(view.show_distance_to.getTextfield().getCharacters().toString()));

                    // build up all new TableColumns
                    TableColumn column_id = new TableColumn("ID");
                    TableColumn column_ext_freq = new TableColumn("Ext. Freq.");
                    TableColumn column_degree = new TableColumn("Degree");
                    TableColumn column_phase = new TableColumn("Phase");
                    TableColumn column_distance = new TableColumn("Distance to ".concat(String.valueOf(n.getId())));

                    // set up the cell factory
                    column_id.setCellValueFactory(new PropertyValueFactory<Node, Integer>("id"));
                    column_degree.setCellValueFactory(new PropertyValueFactory<Node, Integer>("degree"));
                    column_ext_freq.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Node<CircleMap>, Double>, ObservableValue<Number>>() {
                        @Override
                        public ObservableValue<Number> call(TableColumn.CellDataFeatures<Node<CircleMap>, Double> param) {
                            return param.getValue().getElement().externalFrequencyProperty();
                        }
                    });
                    column_phase.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Node<CircleMap>, Double>, ObservableValue<Number>>() {
                        @Override
                        public ObservableValue<Number> call(TableColumn.CellDataFeatures<Node<CircleMap>, Double> param) {
                            return param.getValue().getElement().phaseProperty();
                        }
                    });

                    column_distance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Node<?>, Integer>, ObservableValue>() {
                        @Override
                        public ObservableValue call(TableColumn.CellDataFeatures<Node<?>, Integer> param) {
                            SimpleIntegerProperty distance = new SimpleIntegerProperty();
                            distance.setValue(n.calculate_distance(param.getValue()));
                            return distance;
                        }
                    });


                    // remove all old columns
                    view.table_parameters.getColumns().removeAll(view.table_parameters.getColumns());
                    // add the new columns
                    view.table_parameters.getColumns().addAll(column_id, column_degree,  column_phase, column_ext_freq, column_distance);

                }
            }
        };

        /*
         *
         * This Event manages the Logic when the button to show selected Nodes is clicked with the Mouse.
         * It catches all selected nodes from the tableview and posts an event with the selected nodes attached as data
         */
        event_btn_show_nodes_click = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                // get the selected nodes
                ObservableList<Node<CircleMap>> selected_nodes = view.table_parameters.getSelectionModel().getSelectedItems();
                if (!selected_nodes.isEmpty()) // only execute when at least one node is selected
                {
                    post(ViewControllerEvent.VisibleNodesChanged, selected_nodes);
                }
            }

        };


        bindEvent(view.current_phase.getTextfield(), KeyEvent.KEY_RELEASED, event_textfield_phase_enter);
        bindEvent(view.external_frequency.getTextfield(), KeyEvent.KEY_RELEASED, event_textfield_external_frequency_enter);
        bindEvent(view.show_distance_to.getTextfield(), KeyEvent.KEY_RELEASED, event_textfield_show_distance_enter);
        bindEvent(view.btn_show_selected_nodes, MouseEvent.MOUSE_CLICKED, event_btn_show_nodes_click);

        ControllerCallback cb_reload = event -> { view.init_table(); };

        bus.register(ViewControllerEvent.ModelReloaded, cb_reload);


    }



}
