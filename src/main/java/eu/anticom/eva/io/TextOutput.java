package eu.anticom.eva.io;

import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.event.EventListener;
import eu.anticom.eva.event.OutputEvent;

public class TextOutput implements IOModule, EventListener {
    @Override
    public void boot() {}

    @Override
    public void shutdown() {}

    @Override
    public void recieveEvent(AbstractEvent event) {
        if(event instanceof OutputEvent) {
            System.out.println("eva > " + ((OutputEvent) event).getResult());
        }
    }
}
