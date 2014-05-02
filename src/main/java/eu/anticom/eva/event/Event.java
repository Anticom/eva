package eu.anticom.eva.event;

import java.util.Date;

public class Event {
    protected Date created;

    protected EventType eventType;
    protected EventEmitter origin;

    protected Object data;

    public Event(EventType eventType, EventEmitter origin, Object data) {
        this.created = new Date();

        this.eventType = eventType;
        this.origin = origin;

        this.data = data;
    }

    public Date getCreated() {
        return created;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventEmitter getOrigin() {
        return origin;
    }

    public Object getData() {
        return data;
    }
}
