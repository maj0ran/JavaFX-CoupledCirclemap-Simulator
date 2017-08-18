package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.compositions.LabeledTextfield;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author : marian
 * @date : 7/7/2017
 */
public class ViewLogOscillator extends BaseView {

    LabeledTextfield input_timesteps = new LabeledTextfield("Timesteps");
    LabeledTextfield input_filename = new LabeledTextfield("Output Filename");
    Button btn_start_logging = new Button("Start Logging");

    public ViewLogOscillator(Data data, Page owner) {
        super(data, owner);
        this.set_panel(new VBox());
        Label headline  = new Label("Log Oscillator");
        headline.setId("label-headline");

        VBox panel = (VBox)this.get_panel();
        panel.setSpacing(10);

        panel.getChildren().addAll(headline, input_timesteps.getItem(), input_filename.getItem(), btn_start_logging);
    }


}
