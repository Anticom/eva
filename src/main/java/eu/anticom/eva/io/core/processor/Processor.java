package eu.anticom.eva.io.core.processor;

import eu.anticom.eva.event.EventListener;

/**
 * Processors run as a task listening for incomming events
 * They can act on the output channels and/or fire other events
 */
public interface Processor extends EventListener {
    //public Voting input(Event event);
}
