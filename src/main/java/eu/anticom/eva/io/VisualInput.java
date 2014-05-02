package eu.anticom.eva.io;

import eu.anticom.eva.event.EventEmitter;

public class VisualInput extends EventEmitter implements IOModule {
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
}
