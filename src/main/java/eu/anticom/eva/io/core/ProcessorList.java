package eu.anticom.eva.io.core;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.io.core.processor.Processor;

import java.util.ArrayList;

public class ProcessorList extends ArrayList<Processor> {
    public void passEvent(Event event) {
        for(Processor processor : this) {
            processor.recieveEvent(event);
        }
    }
}
