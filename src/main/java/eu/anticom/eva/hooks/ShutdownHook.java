package eu.anticom.eva.hooks;

import eu.anticom.eva.Eva;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.OutputEvent;

import java.io.FileWriter;
import java.io.IOException;

public class ShutdownHook extends EventEmitter implements Runnable {
    protected Eva eva;

    public ShutdownHook(Eva eva) {
        this.eva = eva;
    }

    @Override
    public void run() {
        //TODO emit an output event instead of direct speaking
        System.out.println("Executing shutdown hook");
        eva.getAudioOutput().speak("Shutting down my System.");

        //shutdown io's
        //TODO
    }
}
