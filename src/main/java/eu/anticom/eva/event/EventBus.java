package eu.anticom.eva.event;

import eu.anticom.eva.io.core.ProcessorList;
import eu.anticom.eva.io.core.processor.Processor;

import java.util.Enumeration;
import java.util.Vector;

public class EventBus {
    protected Vector<EventListener> registeredObjects;

    public EventBus() {
        registeredObjects = new Vector<EventListener>();
    }

    //region bus registration
    public void register(EventListener listener) {
        registeredObjects.add(listener);
    }

    public void unregister(EventListener listener) {
        registeredObjects.remove(listener);
    }
    //endregion

    //region convenience
    public void register(EventListener[] listeners) {
        for(EventListener listener : listeners) {
            register(listener);
        }
    }

    public void register(Enumeration<EventListener> listeners) {
        while(listeners.hasMoreElements()){
            EventListener listener = listeners.nextElement();
            register(listener);
        }
    }

    public void connectEmitters(EventEmitter[] emitters) {
        for(EventEmitter emitter : emitters) {
            emitter.setEventBus(this);
        }
    }

    public void connectEmitters(Enumeration<EventEmitter> emitters) {
        while(emitters.hasMoreElements()){
            EventEmitter emitter = emitters.nextElement();
            emitter.setEventBus(this);
        }
    }

    public void connectProcessors(ProcessorList processors) throws Exception {
        for(Processor processor : processors) {
            if(processor instanceof EventEmitter) {
                ((EventEmitter) processor).setEventBus(this);
            } else {
                throw new Exception("Processor is unable to emit events.");
            }
        }
    }
    //endregion

    //region messaging
    public void broadcast(AbstractEvent event, Object sender) {
        for(EventListener listener : registeredObjects) {
            if(sender instanceof EventListener && listener == sender) continue;
            listener.recieveEvent(event);
        }
    }
    //endregion
}
