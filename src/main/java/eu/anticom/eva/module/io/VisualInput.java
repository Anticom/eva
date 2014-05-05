package eu.anticom.eva.module.io;

public class VisualInput extends Module implements IModule {
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
