package eu.anticom.eva.io;

import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.event.EventListener;

public class Hands implements IOModule, EventListener {
    public Hands() {

    }

    @Override
    public void boot() {}

    @Override
    public void shutdown() {}

    @Override
    public void recieveEvent(AbstractEvent event) {
//        System.out.println(this.getClass().toString() + " recieved event:");
//        System.out.println(event);
    }
}
