package eu.anticom.eva.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventListener;

public class Hands implements IOModule, EventListener {
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
    public void run() {

    }

    @Override
    public void recieveEvent(Event event) {
//        System.out.println(this.getClass().toString() + " recieved event:");
//        System.out.println(event);
    }
}
