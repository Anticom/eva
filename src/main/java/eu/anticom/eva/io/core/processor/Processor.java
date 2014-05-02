package eu.anticom.eva.io.core.processor;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.EventListener;

/**
 * Processors run as a task listening for incomming events
 * They can act on the output channels and/or fire other events
 */
public abstract class Processor extends EventEmitter implements EventListener {
    @Override
    public abstract void recieveEvent(Event event);
}
