package eu.anticom.eva.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventBus;

public abstract class Module implements IModule {
    protected EventBus eventBus;

    @Override
    public void recieveEvent(Event event) {
        //do nothing by default
    }

    @Override
    public abstract void boot();

    @Override
    public abstract void shutdown();

    @Override
    public abstract void run();

    protected void emit(Event event) throws Exception {
        if(eventBus == null) {
            throw new Exception("EventBus not set");
        }
        eventBus.broadcast(event, this);
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
