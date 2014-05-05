package eu.anticom.eva.module.io;

import eu.anticom.eva.event.Event;

public class Hands extends Module {
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
//        System.out.println(this.getClass().toString() + " recieved event:");
//        System.out.println(event);
    }
}
