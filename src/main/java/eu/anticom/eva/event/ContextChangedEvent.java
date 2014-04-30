package eu.anticom.eva.event;

/**
 * This Event is fired, whenever a context changes
 *
 * e.g.:
 *  When a person changes room, eva should disable old room mic/camera and enable new room mic/camera
 *  That should be caused by a ContextChangedEvent
 */
public class ContextChangedEvent {

    public ContextChangedEvent() {

    }
}
