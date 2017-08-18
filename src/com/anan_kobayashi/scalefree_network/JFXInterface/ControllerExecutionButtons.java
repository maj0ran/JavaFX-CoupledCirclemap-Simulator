package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerCallback;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.EventBusSubscriber;
import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by marian on 6/15/2017.
 *
 */
class ControllerExecutionButtons extends BaseController {


    private EventHandler<MouseEvent> step_button_click_event;
    private EventHandler<MouseEvent> play_button_click_event;

    ScheduledExecutorService timer;


    ControllerExecutionButtons(Data model, ViewExecutionButtons view, ControllerEventBus bus){
        super(model, view, bus);

        step_button_click_event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                model.handler.oscillate_network();
                post(ViewControllerEvent.NodeParameterChanged, null);
            }
        };

        play_button_click_event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ToggleButton btn = (ToggleButton) event.getSource();
                if (btn.isSelected()) {
                    timer = Executors.newScheduledThreadPool(1);

                    timer.scheduleAtFixedRate(() -> {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                model.handler.oscillate_network();
                                post(ViewControllerEvent.NodeParameterChanged, null);
                            }
                        });

                    }, 0, 100, TimeUnit.MILLISECONDS);
                } else {
                    timer.shutdown();
                }
            }
        };

        bindEvent(view.play_button, MouseEvent.MOUSE_CLICKED, play_button_click_event);
        bindEvent(view.step_button, MouseEvent.MOUSE_CLICKED, step_button_click_event);


        ControllerCallback cb_stop_autoplay = event -> { this.timer.shutdown(); };
        bus.register(ViewControllerEvent.ProgramQuit, cb_stop_autoplay);
    }




}
