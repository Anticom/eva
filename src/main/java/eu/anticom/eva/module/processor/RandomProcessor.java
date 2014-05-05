package eu.anticom.eva.module.processor;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventType;
import eu.anticom.eva.module.io.Module;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomProcessor extends Module {
    protected static Random rand = new Random();

    @Override
    public void boot() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void run() {
        while(true) {
            //random time sleeping
            int timeToSleep = randInt(3, 10);
            try {
                TimeUnit.SECONDS.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //now speak a random word
            String[] words = {"humpty dumpty", "lol", "okay", "i'm here", "words", "cheese cake"};
            int chosenIndex = randInt(0, words.length - 1);
            String chosenWord = words[chosenIndex];
            emit(new Event(EventType.OUTPUT, this, chosenWord));
        }
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }
}
