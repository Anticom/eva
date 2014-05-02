package eu.anticom.eva.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventListener;
import eu.anticom.eva.event.EventType;

public class TextOutput extends Module {
    protected boolean running;

    @Override
    public void boot() {
        running = true;
    }

    @Override
    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {}

    @Override
    public void recieveEvent(Event event) {
        if(event.getEventType() == EventType.OUTPUT) {
            System.out.println("eva > " + event.getData());
        }
    }
}
