package eu.anticom.eva.event;

import java.util.Date;
import java.util.UUID;

public class Event {
    protected UUID uuid;
    protected Date created;

    protected EventType eventType;
    protected Object origin;

    protected boolean handled = false;
    protected Object data;

    public Event(EventType eventType, Object origin, Object data) {
        this.uuid = UUID.randomUUID();
        this.created = new Date();

        this.eventType = eventType;
        this.origin = origin;

        this.data = data;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getCreated() {
        return created;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Object getOrigin() {
        return origin;
    }

    public void markHandled() {
        handled = true;
    }

    public void markHandled(boolean handled) {
        this.handled = handled;
    }

    public boolean isHandled() {
        return handled;
    }

    public Object getData() {
        return data;
    }
}
