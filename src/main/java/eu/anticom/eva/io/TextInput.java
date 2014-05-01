package eu.anticom.eva.io;

import eu.anticom.eva.event.*;

import java.util.Scanner;

public class TextInput extends EventEmitter implements IOModule, Runnable {
    public boolean running;

    public TextInput() {

    }

    @Override
    public void boot() {}

    @Override
    public void shutdown() {}

    @Override
    public void run() {
        running = true;

        Scanner scanner = new Scanner(System.in);
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
