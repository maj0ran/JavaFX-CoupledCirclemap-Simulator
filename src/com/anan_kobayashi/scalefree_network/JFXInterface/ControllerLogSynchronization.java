package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerLogSynchronization extends BaseController<ViewLogSynchronization> {

    public ControllerLogSynchronization(Data model, ViewLogSynchronization view, ControllerEventBus bus) {
        super(model, view, bus);

        EventHandler<MouseEvent> btn_start_logging_event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // get the desired amount of timesteps from field
                int timesteps = Integer.valueOf(view.input_timesteps.getTextfield().getText());
                // get the deisred filename from the field
                String filename = view.input_filename.getTextfield().getText();
                Path file = Paths.get("Logging\\" + filename);

                // save the current phase of all Nodes
//                ArrayList<Node<CircleMap>> saved_nodes = (ArrayList<Node<CircleMap>>)model.network.get_nodes().clone();

                ArrayList<Double> init_phase = new ArrayList<>();
                for(Node<CircleMap> n : model.network.get_nodes())
                {
                    init_phase.add(n.getElement().getPhase());
                }
                LinkedList<String> log = new LinkedList<>();

                double step_size = 0.001;
                double temp_coupling_str = -1.0;

                while (temp_coupling_str <= 1.0 + step_size) {
                    // after every increase of the coupling, we have to restore the previous Phase of all nodes
                    for(int i = 0; i < init_phase.size(); i++)
                    {
                        model.network.get_node(i).getElement().setPhase(init_phase.get(i));
                    }

                    // transition state after changed coupling
                    model.handler.set_coupling_strength(temp_coupling_str);
                    for (int i = 0; i < 1000; i++) {
                        model.handler.oscillate_network();
                    }

                    // stable state after transition state; this is what we actually log
                    for (int i = 0; i < timesteps; i++) {
                        model.handler.oscillate_network();
                        double r = model.handler.get_synchronity_measure(model.handler.calculate_order_parameter(model.network.get_nodes()));
                        log.add(String.format("%.4f", model.handler.get_coupling_strength()) + " " + String.format("%.4f", r));
                    }
                    temp_coupling_str += step_size;
                }

                // reset the coupling to zero
                model.handler.set_coupling_strength(0);
                // reset the phases of all nodes
                for(int i = 0; i < init_phase.size(); i++)
                {
                    model.network.get_node(i).getElement().setPhase(init_phase.get(i));
                }

                try {
                    Files.write(file, log, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        bindEvent(this.view.btn_start_logging, MouseEvent.MOUSE_CLICKED, btn_start_logging_event);
    }
}
