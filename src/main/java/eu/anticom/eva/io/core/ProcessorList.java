package eu.anticom.eva.io.core;

import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.io.core.processor.Processor;

import java.util.ArrayList;

public class ProcessorList extends ArrayList<Processor> {
    public void passEvent(AbstractEvent event) {
        for(Processor processor : this) {
            processor.recieveEvent(event);
        }
    }
}
