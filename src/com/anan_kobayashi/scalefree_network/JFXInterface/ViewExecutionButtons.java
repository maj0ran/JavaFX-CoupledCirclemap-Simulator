package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ViewControllerEvent;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

/**
 *
 * Created by marian on 5/25/2017.
 *
 */
class ViewExecutionButtons extends BaseView {
    public ToggleButton play_button;
    public Button step_button;
    public Button reset_button;

    ViewExecutionButtons(Data data, Page owner) {

        super(data, owner);
        set_panel(new HBox(20));

        play_button = new ToggleButton();
        play_button.setText("Play");


        step_button = new Button();
        step_button.setText("Step");

        get_panel().setPadding(new Insets(0, 10, 10, 10));


        add_elements(play_button, step_button);
    }



}
