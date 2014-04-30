package eu.anticom.eva.event;

/**
 * Emitted by Processors to tell the output channels to output the event result
 */
public class OutputEvent extends Event {
    /** this might be changed to Sentence class later */
    protected String result;

    public OutputEvent(String result) {
        super();
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
