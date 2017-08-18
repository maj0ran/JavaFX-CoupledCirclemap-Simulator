package com.anan_kobayashi.scalefree_network.JFXInterface.compositions;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class LabeledTextfield {
    private VBox item;

    private Label label;
    private TextField textfield;

    public LabeledTextfield(String headline) {
        item = new VBox();

        this.label = new Label(headline);
        this.label.setId("label-textfield-description");

        this.textfield = new TextField();
        item.getChildren().addAll(label, textfield);
    }

    public VBox getItem() {
        return item;
    }

    public TextField getTextfield() {
        return textfield;
    }

    public Label getLabel() {
        return label;
    }


}
