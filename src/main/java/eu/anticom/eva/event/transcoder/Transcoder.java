package eu.anticom.eva.event.transcoder;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.RemoteEvent;

public interface Transcoder {
    public RemoteEvent encode(Event event);
    public Event decode(RemoteEvent event);
}
