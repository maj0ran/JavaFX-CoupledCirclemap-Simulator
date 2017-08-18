package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import com.anan_kobayashi.scalefree_network.backend.ScaleFreeNetwork;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 *
 * Created by marian on 6/20/2017.
 *
 */
class ControllerNetworkParameters extends BaseController {

    EventHandler<KeyEvent> event_coupling_textfield_enter;

    ControllerNetworkParameters(Data data, ViewNetworkParameters view, ControllerEventBus bus) {

        super(data, view, bus);

        event_coupling_textfield_enter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    // get data from owned view
                    double coupling_strength = Double.valueOf(view.coupling_strength.getTextfield().getCharacters().toString());
                    // set new value in model
                    data.handler.set_coupling_strength(coupling_strength);
                    // post change to other controllers
                    post(ViewControllerEvent.NetworkParameterChanged, null);
                }
            }
        };

        // Force the Field for setting the Coupling Strength Parameter to only accept valid Values (number with max. 1 Point)
        view.coupling_strength.getTextfield().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("^\\-?\\d*\\.?\\d*")) {
                    view.coupling_strength.getTextfield().setText(newValue.replaceAll(newValue, oldValue));
                }
            }
        });

        EventHandler<MouseEvent> event_btn_new_network = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int size = Integer.valueOf(view.textfield_new_network_size.getTextfield().getText());
                data.network = new ScaleFreeNetwork<>(size);
                data.handler.setNetwork(data.network);
                for(Node<CircleMap> n : data.network.get_nodes())
                {
                    n.setElement(new CircleMap());
                }
                post(ViewControllerEvent.ModelReloaded, null);
            }
        };

        EventHandler<MouseEvent> event_btn_import_sif = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filename = view.filename_import.getTextfield().getText();
                try {
                    data.network.import_from_sif(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(Node<CircleMap> n : data.network.get_nodes())
                {
                    n.setElement(new CircleMap());
                }

                post(ViewControllerEvent.ModelReloaded, null);
            }
        };

        EventHandler<MouseEvent> event_btn_export_sif = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String filename = view.filename_export.getTextfield().getText();
                try {
                    data.network.export_to_sif(filename);
                 } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        bindEvent(view.coupling_strength.getTextfield(), KeyEvent.KEY_RELEASED, event_coupling_textfield_enter);
        bindEvent(view.btn_new_network, MouseEvent.MOUSE_CLICKED, event_btn_new_network);
        bindEvent(view.btn_export_to_sif, MouseEvent.MOUSE_CLICKED, event_btn_export_sif);
        bindEvent(view.btn_import_from_sif, MouseEvent.MOUSE_CLICKED, event_btn_import_sif);
    }



}
