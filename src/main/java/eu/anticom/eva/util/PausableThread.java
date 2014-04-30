package eu.anticom.eva.util;

public class PausableThread extends Thread {
    private volatile boolean running = true; // Run unless told to pause

    @Override
    public void run() {
        while (!running) {
            yield();
        }

        super.run();
    }

    public void pauseThread() throws InterruptedException {
        running = false;
    }

    public void resumeThread() {
        running = true;
    }
}
