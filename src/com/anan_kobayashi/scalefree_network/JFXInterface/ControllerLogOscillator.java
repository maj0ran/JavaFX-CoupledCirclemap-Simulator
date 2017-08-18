package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.CircleMap;
import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.backend.Node;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class ControllerLogOscillator extends BaseController {
    public ControllerLogOscillator(Data model, ViewLogOscillator view, ControllerEventBus bus) {
        super(model, view, bus);

        EventHandler<MouseEvent> event_start_log = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // get the desired amount of timesteps from field
                int timesteps = Integer.valueOf(view.input_timesteps.getTextfield().getText());
                // get the deisred filename from the field
                String filename = view.input_filename.getTextfield().getText();
                Path file = Paths.get("Logging\\" + filename);


                new File("Logging").mkdirs();

                LinkedList<String> output = new LinkedList<>();
                // Transition State
                for (int i = 0; i < 1000; i++) {
                    model.handler.oscillate_network();
                }

                // Actual Logging
                for (int i = 0; i < timesteps; i++) {
                    model.handler.oscillate_network();
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(i));
                    for (Node<CircleMap> n : model.network.get_nodes()) {
                        sb.append(" " + String.format("%.5f", n.getElement().getPhase()));
                    }

                    output.add(sb.toString());
                }

                try {
                    Files.write(file, output, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        this.bindEvent(view.btn_start_logging, MouseEvent.MOUSE_CLICKED, event_start_log);
    }
}
