package com.anan_kobayashi.scalefree_network.JFXInterface;

import com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus.ControllerEventBus;
import com.anan_kobayashi.scalefree_network.backend.Data;

/**
 *
 * Created by marian on 7/6/2017.
 *
 */
public class ApplicationController extends BaseController {


    public ApplicationController(Data model, BaseView view, ControllerEventBus bus) {
        super(model, view, bus);
    }



}
