package eu.anticom.eva.io;

public interface IOModule extends Runnable {
    //initialisation (allocate resources etc.)
    public void boot();
    //final shutdown
    public void shutdown();

    //restart the module (shutdown + boot)
    //public void restart();

    //temporarily suspend the module (like stop listening to events) but keep it bootet (e.g. resources allocated)
    //public void pause();
    //resume temporary suspension
    //public void resume();
}
