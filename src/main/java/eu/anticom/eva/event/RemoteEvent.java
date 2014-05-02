package eu.anticom.eva.event;

import java.util.Vector;

public class RemoteEvent {
    protected Vector<String> headers;
    protected String body;

    public RemoteEvent(String body) {
        this.body = body;
        this.headers = new Vector<String>();
    }

    public RemoteEvent(String body, Vector<String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public Vector<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
