package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.JFXInterface.compositions.LabeledTextfield;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author : marian
 * @date : 7/7/2017
 */
public class ViewNodeParameters extends BaseView {

    private Label label_headline;

    TableView<Node<CircleMap>> table_parameters = new TableView();
    TableColumn column_id = new TableColumn<>("ID");
    TableColumn column_ext_freq = new TableColumn("Ext. Freq.");
    TableColumn column_degree = new TableColumn("Degree");
    TableColumn column_phase = new TableColumn("Phase");
    TableColumn column_distance = new TableColumn("Distance");

    LabeledTextfield external_frequency = new LabeledTextfield("External Frequency");
    LabeledTextfield current_phase = new LabeledTextfield("Current Phase");
    LabeledTextfield show_distance_to = new LabeledTextfield("Show Distance To");

    Button btn_show_selected_nodes = new Button("Show Selected Nodes Only");


    ViewNodeParameters(Data data, Page owner) {
        super(data, owner);

        set_panel(new GridPane());

        // set the width to the longest text. Yeah, it's dirty ...
        double width = new Text("External Frequency (omega)").getLayoutBounds().getWidth();

        label_headline = new Label("Node Settings:");
        label_headline.setId("label-headline");

        get_panel().setMinWidth(500);
        get_panel().setMaxWidth(500);

        table_parameters.getColumns().addAll(column_id, column_degree,  column_phase, column_ext_freq, column_distance);
        table_parameters.setId("tables");
        table_parameters.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        GridPane ref = (GridPane)get_panel();
        ref.setVgap(10);
        ref.setHgap(10);
        ref.add(label_headline, 0, 0, 2, 1);
        ref.add(table_parameters, 0, 1, 1, 3);
        ref.add(current_phase.getItem(), 1, 1, 1, 1);
        ref.add(external_frequency.getItem(), 1, 2, 1, 1);
        ref.add(show_distance_to.getItem(), 1, 3, 1, 1);
        ref.add(btn_show_selected_nodes, 0, 4, 1, 1);

        init_table();

    }

    void init_table()
    {

        table_parameters.getItems().removeAll();

        // set the data for the tableview
        ObservableList<Node<CircleMap>> node_list = FXCollections.observableArrayList(); // Observable List with the data
        node_list.addAll(data.network.get_nodes());
        table_parameters.setItems(node_list);

        // generate the columns
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
    }


}
