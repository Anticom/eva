package eu.anticom.eva.event;

import java.util.Date;

public class Event extends AbstractEvent {
    protected Date created;

    public Event() {
        this.created = new Date();
    }

    public Date getCreated() {
        return created;
    }
}
