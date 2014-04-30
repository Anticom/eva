package eu.anticom.eva.hooks;

import eu.anticom.eva.Eva;

import java.io.FileWriter;
import java.io.IOException;

public class ShutdownHook implements Runnable {
    protected Eva eva;

    public ShutdownHook(Eva eva) {
        this.eva = eva;
    }

    @Override
    public void run() {
        System.out.println("Executing shutdown hook :)");

        //save configuration
        /*
        try {
            eva.getConfiguration().save(new FileWriter(Eva.CONFIG_FILE));
        } catch (IOException e) {
            System.out.println("Unable to save configuration");
            e.printStackTrace();
        }
        */

        //shutdown io's
        //TODO
    }
}
