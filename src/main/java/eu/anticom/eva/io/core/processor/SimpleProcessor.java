package eu.anticom.eva.io.core.processor;

import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.InputEvent;
import eu.anticom.eva.event.OutputEvent;

import java.util.Date;

/**
 * Listens for exact phrases
 */
public class SimpleProcessor extends EventEmitter implements Processor {
    @Override
    public void recieveEvent(AbstractEvent event) {
        if(event instanceof InputEvent) {
            String sentence = ((InputEvent) event).getSentence();

            if(sentence.contains("time is it")) {
                try {
                    Date date = new Date();
                    String hourString;
                    if(date.getHours() > 12) {
                        hourString = String.valueOf(date.getHours() - 12) + " p m";
                    } else {
                        hourString = String.valueOf(date.getHours()) + " a m";
                    }
                    String dateString = date.getMinutes() + " past " + hourString;

                    emit(new OutputEvent("It is " + dateString));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("who are you?")) {
                try {
                    emit(new OutputEvent("My name is EVA."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("what are you?")) {
                try {
                    emit(new OutputEvent("EVA stands for Electronic Virtual Assistant. I'm here to help you out basically."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("hello")) {
                try {
                    emit(new OutputEvent("Hi!"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.equals("shutdown")) {
                System.exit(0);
            }

            if(sentence.startsWith("say:")) {
                String toSay = sentence.substring(4);
                try {
                    emit(new OutputEvent(toSay));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
