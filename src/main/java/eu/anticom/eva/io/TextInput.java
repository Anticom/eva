package eu.anticom.eva.io;

import eu.anticom.eva.event.*;

import java.util.Scanner;

public class TextInput extends EventEmitter implements IOModule, Runnable {
    protected boolean running;
    protected Scanner scanner;

    public TextInput() {

    }

    @Override
    public void boot() {
        scanner = new Scanner(System.in);
        running = true;
    }

    @Override
    public void shutdown() {
        running = false;

    }

    @Override
    public void run() {
        while(running) {
            System.out.print("eva < ");
            String input = scanner.nextLine();

            InputEvent event = new InputEvent(input);

            try {
                emit(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
