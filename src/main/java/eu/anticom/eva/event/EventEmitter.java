package eu.anticom.eva.event;

public class EventEmitter {
    protected EventBus eventBus;

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    protected void emit(Event event) throws Exception {
        if(eventBus == null) {
            throw new Exception("EventBus not set");
        }
        eventBus.broadcast(event, this);
    }
}
