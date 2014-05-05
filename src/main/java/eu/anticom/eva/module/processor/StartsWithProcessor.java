package eu.anticom.eva.module.processor;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventType;
import eu.anticom.eva.module.io.Module;

import java.util.ArrayList;

public abstract class StartsWithProcessor extends Module {
    protected ArrayList<String> triggers;

    public StartsWithProcessor(ArrayList<String> triggers) {
        this.triggers = triggers;
    }

    @Override
    public void recieveEvent(Event event) {
        if (event.getEventType() == EventType.INPUT
                && !event.isHandled()) {

            String input = (String) event.getData();

            for(String trigger : triggers) {
                if(input.startsWith(trigger)) {
                    emit(new Event(EventType.OUTPUT, this, buildMessage(input)));
                    event.markHandled();
                }
            }
        }
    }

    protected abstract String buildMessage(String input);
}
