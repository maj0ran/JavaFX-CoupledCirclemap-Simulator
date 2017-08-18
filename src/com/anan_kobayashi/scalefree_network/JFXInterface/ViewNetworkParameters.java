package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.backend.Data;
import com.anan_kobayashi.scalefree_network.JFXInterface.compositions.LabeledTextfield;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by marian on 6/20/2017.
 *
 */
public class ViewNetworkParameters extends BaseView {


    LabeledTextfield coupling_strength = new LabeledTextfield("Coupling Strength");

    Button btn_new_network = new Button("Generate New Network");
    LabeledTextfield textfield_new_network_size = new LabeledTextfield("New Network Size");

    LabeledTextfield filename_import = new LabeledTextfield("Filename for Import");
    Button btn_import_from_sif = new Button("Import from sif");
    LabeledTextfield filename_export = new LabeledTextfield("Filename for export");
    Button btn_export_to_sif = new Button("Export to sif");

    ViewNetworkParameters(Data data, Page owner) {
        super(data, owner);
        set_panel(new VBox(10));

        get_panel().setMinWidth(200);
        get_panel().setMaxWidth(200);

        Label headline = new Label("Network Settings");
        headline.setId("label-headline");

        get_panel().getChildren().addAll(headline,
                                        coupling_strength.getItem(),
                                        textfield_new_network_size.getItem(),
                                        btn_new_network,
                                        filename_export.getItem(),
                                        btn_export_to_sif,
                                        filename_import.getItem(),
                                        btn_import_from_sif
                                        );


    }


}
