package eu.anticom.eva.module.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventType;

import java.util.Scanner;

public class TextInput extends Module implements IModule {
    protected boolean running;

    protected Scanner scanner;

    @Override
    public void boot() {
        running = true;
        scanner = new Scanner(System.in);
    }

    @Override
    public void shutdown() {
        running = false;
        scanner.close();
    }

    @Override
    public void run() {
        while(running) {
            System.out.print("eva < ");
            if(scanner.hasNextLine()) {
                String input = scanner.nextLine();
                emit(new Event(EventType.INPUT, this, input));
            }
        }
    }
}
