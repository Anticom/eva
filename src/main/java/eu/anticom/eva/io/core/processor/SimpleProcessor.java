package eu.anticom.eva.io.core.processor;

import eu.anticom.eva.event.*;
import eu.anticom.eva.io.Module;

import java.util.Date;

/**
 * Listens for exact phrases
 */
public class SimpleProcessor extends Module {
    @Override
    public void boot() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void run() {

    }

    @Override
    public void recieveEvent(Event event) {
        if(event.getEventType() == EventType.INPUT) {
            String sentence = (String) event.getData();

            if((!event.isHandled()) && sentence.equals("shutdown")) {
                event.markHandled();
                System.exit(0);
            }

            if((!event.isHandled()) && sentence.startsWith("say:")) {
                String toSay = sentence.substring(4);

                emit(new Event(EventType.OUTPUT, this, toSay));
                event.markHandled();
            }

            if((!event.isHandled()) && sentence.contains("time is it")) {
                Date date = new Date();

                StringBuilder dateString = new StringBuilder();
                dateString.append("It's");

                Integer minutes = date.getMinutes();
                if(minutes == 0) {
                    //nothing here
                } else if(minutes <= 30) {
                    dateString.append(" ").append(String.valueOf(minutes));
                    dateString.append(" ").append("past");
                } else {
                    dateString.append(" ").append(String.valueOf((60 - minutes)));
                    dateString.append(" ").append("to");
                }

                dateString.append(" ").append(String.valueOf(date.getHours()));

                emit(new Event(EventType.OUTPUT, this, dateString.toString()));
                event.markHandled();
            }

            if((!event.isHandled()) && sentence.contains("who are you?")) {
                emit(new Event(EventType.OUTPUT, this, "My name is EVA"));
                event.markHandled();
            }

            if((!event.isHandled()) && sentence.contains("what are you?")) {
                emit(new Event(EventType.OUTPUT, this, "EVA is a acronym for Electronic Virtual Assistant."));
                event.markHandled();
            }

            if((!event.isHandled()) && sentence.contains("hello")) {
                emit(new Event(EventType.OUTPUT, this, "Hi!"));
                event.markHandled();
            }
        }
    }
}
