package com.anan_kobayashi.scalefree_network.JFXInterface.ViewControllerBus;

/*
 *
 * Created by marian on 5/7/2017.
 *
 */
public abstract class EventBusSubscriber
{

    public ControllerEventBus bus;
    protected EventBusSubscriber(ControllerEventBus bus)
    {
        this.bus = bus;
    }

    protected void attach(ControllerEventBus bus)
    {
        this.bus = bus;
    }

    protected void registerEvent(ViewControllerEvent event, ControllerCallback cb)
    {
        bus.register(event, cb);
    }

    public void post(ViewControllerEvent event, Object parameters)
    {
        this.bus.post(event, parameters);
    }
}
