package eu.anticom.eva.event;

/**
 * Emitted by Input channels to start the action-reaction-cycle on that input
 */
public class InputEvent extends Event {
    /** this might be changed to Sentence class later */
    protected String sentence;

    public InputEvent(String sentence) {
        super();
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }
}
