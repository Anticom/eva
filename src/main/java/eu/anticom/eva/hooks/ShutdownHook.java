package eu.anticom.eva.hooks;

import eu.anticom.eva.Eva;
import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventType;
import eu.anticom.eva.module.io.IModule;

import java.util.Enumeration;

public class ShutdownHook implements Runnable {
    protected Eva eva;

    public ShutdownHook(Eva eva) {
        this.eva = eva;
    }

    @Override
    public void run() {
        //TODO emit an output event instead of direct speaking
        eva.getEventBus().broadcast(
                new Event(
                        EventType.OUTPUT,
                        this,
                        "Executing shutdown hook"
                )
        );

        //shutdown io's
        Enumeration<IModule> modules = eva.getModules().elements();
        while(modules.hasMoreElements()) {
            IModule module = modules.nextElement();
            System.out.printf("Shutting down module: [%s]\n", module.getClass().toString());
            module.shutdown();
        }
    }
}
