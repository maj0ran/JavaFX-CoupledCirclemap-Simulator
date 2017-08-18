package com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus;


import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Created by marian on 6/28/2017.
 *
 */
public class ControllerEventBus {



    private HashMap<ViewControllerEvent, ArrayList<ControllerCallback>> subscribers;
    public ControllerEventBus()
    {
        this.subscribers = new HashMap<>();
        // add all possible events
        for(ViewControllerEvent e : ViewControllerEvent.values())
        {
            this.subscribers.put(e, new ArrayList<>());
        }
    }

    public void attach(EventBusSubscriber subscriber)
    {
        subscriber.bus = this;
    }
    public void register(ViewControllerEvent event, ControllerCallback cb)
    {
        this.subscribers.get(event).add(cb);
    }

    public void post(ViewControllerEvent event, Object parameters)
    {
        for(ControllerCallback cb : subscribers.get(event))
        {
            cb.callback(parameters);
        }
    }

}
