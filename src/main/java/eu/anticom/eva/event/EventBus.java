package eu.anticom.eva.event;

import eu.anticom.eva.io.IModule;
import eu.anticom.eva.io.core.ProcessorList;
import eu.anticom.eva.io.core.processor.Processor;

import java.util.Enumeration;
import java.util.Vector;

public class EventBus {
    protected Vector<IModule> registeredObjects;

    public EventBus() {
        registeredObjects = new Vector<IModule>();
    }

    //region bus registration
    public void register(IModule listener) {
        registeredObjects.add(listener);
    }

    public void unregister(IModule listener) {
        registeredObjects.remove(listener);
    }
    //endregion

    //region convenience
    public void register(IModule[] listeners) {
        for(IModule listener : listeners) {
            register(listener);
        }
    }

    public void register(Enumeration<IModule> listeners) {
        while(listeners.hasMoreElements()){
            IModule listener = listeners.nextElement();
            register(listener);
        }
    }

    public void connectEmitters(IModule[] emitters) {
        for(IModule emitter : emitters) {
            emitter.setEventBus(this);
        }
    }

    public void connectEmitters(Enumeration<IModule> emitters) {
        while(emitters.hasMoreElements()){
            IModule emitter = emitters.nextElement();
            emitter.setEventBus(this);
        }
    }

    public void connectProcessors(ProcessorList processors) throws Exception {
        for(Processor processor : processors) {
            processor.setEventBus(this);
        }
    }
    //endregion

    //region messaging
    public void broadcast(Event event) {
        for(IModule listener : registeredObjects) {
            if(event.getOrigin() == listener) continue;
            listener.recieveEvent(event);
        }
    }
    //endregion
}
