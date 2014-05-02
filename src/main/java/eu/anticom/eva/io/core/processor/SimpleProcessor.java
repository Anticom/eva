package eu.anticom.eva.io.core.processor;

import eu.anticom.eva.event.*;

import java.util.Date;

/**
 * Listens for exact phrases
 */
public class SimpleProcessor extends EventEmitter implements Processor {
    @Override
    public void recieveEvent(Event event) {
        if(event.getEventType() == EventType.INPUT) {
            String sentence = (String) event.getData();

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

                    emit(new Event(EventType.OUTPUT, this, "It is " + dateString));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("who are you?")) {
                try {
                    emit(new Event(EventType.OUTPUT, this, "My name is EVA"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("what are you?")) {
                try {
                    emit(new Event(EventType.OUTPUT, this, "EVA stands for Electronic Virtual Assistant. I'm here to help you out basically."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(sentence.contains("hello")) {
                try {
                    emit(new Event(EventType.OUTPUT, this, "Hi!"));
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
                    emit(new Event(EventType.OUTPUT, this, toSay));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
