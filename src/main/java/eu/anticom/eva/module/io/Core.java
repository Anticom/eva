package eu.anticom.eva.module.io;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventBus;
import eu.anticom.eva.module.io.core.Environment;
import eu.anticom.eva.module.io.core.ProcessorList;

public class Core extends Module implements IModule {
    protected boolean running;

    protected Environment environment;  //stuff like time, date, own location

    protected ProcessorList processors = new ProcessorList();;
    protected Integer context;      //topic history, but also word context, speaker, is eva meant with what is said?

    public Core() {
        environment = new Environment();

        //processors.add(new SimpleProcessor());
        //eventBus.register((Processor[]) processors.toArray());
    }

    @Override
    public void boot() {
        running = true;
    }

    @Override
    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {

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
    public void recieveEvent(Event event) {
        processors.passEvent(event);
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
