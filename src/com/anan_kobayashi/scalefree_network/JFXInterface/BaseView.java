package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.EventBusSubscriber;
import com.anan_kobayashi.scalefree_network.backend.Data;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */

abstract class BaseView
{
    final Data data;
    private Pane panel; // the layout type, e.g. HBox, VBox, StackPane, GridPane, ...
    private Page owner;

    public BaseView(Data data, Page owner) {
        this.data = data;
        this.owner = owner;

    }


    protected void set_panel(Pane panel) {

        this.panel = panel;

    }

    protected void setRelativeWidth(double percentage)
    {
   //     this.get_panel().prefWidthProperty().bind(this.get_panel().getScene().widthProperty().multiply(percentage));
        this.get_panel().prefWidthProperty().bind(this.owner.getOwner().getScene().widthProperty().multiply(percentage));
    }

    protected void setRelativeHeight(double percentage)
    {
        this.get_panel().prefHeightProperty().bind(this.owner.getOwner().getScene().heightProperty().multiply(percentage));
   //     this.get_panel().prefHeightProperty().bind(this.get_panel().getScene().heightProperty().multiply(percentage));
    }

    protected Pane get_panel() {
        return this.panel;
    }

    protected void add_elements(Node... elements) {
        this.panel.getChildren().addAll(elements);
    }


}
