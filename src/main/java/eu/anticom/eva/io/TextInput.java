package eu.anticom.eva.io;

import eu.anticom.eva.event.*;

import java.util.Scanner;

public class TextInput extends EventEmitter implements IOModule {
    protected boolean running;

    protected Scanner scanner;

    public TextInput() {

    }

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
            String input = scanner.nextLine();

            Event event = new Event(EventType.INPUT, this, input);

            try {
                emit(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
