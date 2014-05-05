package eu.anticom.eva.module.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventBus;

public abstract class Module implements IModule {
    
    protected EventBus eventBus;

    @Override
    public void recieveEvent(Event event) {}

    @Override
    public void boot() {}

    @Override
    public void shutdown() {}

    @Override
    public void run() {}

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    protected void emit(Event event) {
        eventBus.broadcast(event);
    }
}
