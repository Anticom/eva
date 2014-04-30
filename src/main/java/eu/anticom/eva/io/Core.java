package eu.anticom.eva.io;

import eu.anticom.eva.event.AbstractEvent;
import eu.anticom.eva.event.EventBus;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.EventListener;
import eu.anticom.eva.io.core.Environment;
import eu.anticom.eva.io.core.ProcessorList;
import eu.anticom.eva.io.core.processor.SimpleProcessor;

public class Core extends EventEmitter implements EventListener {
    protected Environment environment;  //stuff like time, date, own location

    protected ProcessorList processors;
    protected Integer context;      //topic history, but also word context, speaker, is eva meant with what is said?

    public Core() {
        environment = new Environment();

        processors = new ProcessorList();
        processors.add(new SimpleProcessor());
        //eventBus.register((Processor[]) processors.toArray());
    }


    @Override
    public void setEventBus(EventBus eventBus) {
        super.setEventBus(eventBus);

        try {
            eventBus.connectProcessors(processors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recieveEvent(AbstractEvent event) {
        processors.passEvent(event);
//        System.out.println(this.getClass().toString() + " recieved event:");
//        System.out.println(event);
    }

    //region getters
    public Environment getEnvironment() {
        return environment;
    }

    public ProcessorList getProcessors() {
        return processors;
    }

    public Integer getContext() {
        return context;
    }
    //endregion
}
