package eu.anticom.eva.event.transcoder;

import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.RemoteEvent;

import java.io.*;

public class SerializeTranscoder implements Transcoder {
    @Override
    public RemoteEvent encode(Event event) {
        OutputStream os;
        try {
            os = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(os);

            o.writeObject(event);

            return new RemoteEvent(os.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Event decode(RemoteEvent event) {
        InputStream is;
        try {
            is = new ByteArrayInputStream(event.getBody().getBytes());
            ObjectInputStream i = new ObjectInputStream(is);

            return (Event) i.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
