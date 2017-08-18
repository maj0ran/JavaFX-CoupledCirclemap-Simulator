package com.anan_kobayashi.scalefree_network.JFXInterface.compositions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public class InformationLabel {

    HBox item;

    Label description;
    Label information;


    public InformationLabel(String desc, String info) {
        item = new HBox();
        item.setMinWidth(300);
        item.setMaxWidth(300);

        description = new Label(desc);
        description.setPadding(new Insets(0, 20, 0, 0));
        description.setAlignment(Pos.CENTER_LEFT);
        description.setId("label-information");

        information = new Label(info);
        information.setAlignment(Pos.CENTER_RIGHT);
        information.setId("label-information");


        item.getChildren().addAll(description, information);
    }

    public HBox getItem() {
        return this.item;
    }

    public Label getDescription() {
        return description;
    }

    public Label getInformation() {
        return information;
    }

}
