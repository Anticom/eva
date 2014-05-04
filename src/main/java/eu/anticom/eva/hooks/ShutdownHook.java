package eu.anticom.eva.hooks;

import eu.anticom.eva.Eva;
import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.EventType;

public class ShutdownHook implements Runnable {
    protected Eva eva;

    public ShutdownHook(Eva eva) {
        this.eva = eva;
    }

    @Override
    public void run() {
        //TODO emit an output event instead of direct speaking
        this.eva.getEventBus().broadcast(
                new Event(
                        EventType.OUTPUT,
                        this,
                        "Executing shutdown hook"
                )
        );

        //shutdown io's
        //TODO
    }
}
