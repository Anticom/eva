package eu.anticom.eva.event.transcoder;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.RemoteEvent;

public class SimpleTranscoder implements Transcoder {
    @Override
    public RemoteEvent encode(Event event) {
        StringBuilder body = new StringBuilder();
        body
                .append("uuid:").append(event.getUuid().toString()).append(",")
                .append("created:").append(event.getCreated().toString()).append(",")
                .append("origin:").append(event.getOrigin().getClass().toString()).append(",")
                .append("data:").append(event.getData().toString());

        return new RemoteEvent(body.toString());
    }

    @Override
    public Event decode(RemoteEvent event) {
        return null;
    }
}
