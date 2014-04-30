package eu.anticom.eva.io;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import eu.anticom.eva.event.EventEmitter;

import java.io.IOException;
import java.io.InputStream;

public class AudioInput extends EventEmitter {
    protected Configuration configuration;
    protected LiveSpeechRecognizer lsr;
    protected StreamSpeechRecognizer ssr;

    //region constructors
    public AudioInput(Configuration configuration) {
        this.configuration = configuration;

        try {
            lsr = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            System.out.println("Eva was unable to configure Ears.");
            e.printStackTrace();
        }

        try {
            ssr = new StreamSpeechRecognizer(configuration);
        } catch (IOException e) {
            System.out.println("Eva was unable to configure Ears.");
            e.printStackTrace();
        }
    }
    //endregion

    //region high-level API
    public void startListening() {
        lsr.startRecognition(true);
    }

    public SpeechResult listen() {
        return lsr.getResult();
    }

    public void stopListening() {
        lsr.stopRecognition();
    }

    public SpeechResult listenToStream(InputStream inputStream) {
        ssr.startRecognition(inputStream);
        SpeechResult speechResult = ssr.getResult();
        ssr.stopRecognition();

        return speechResult;
    }
    //endregion

    //region getters
    public Configuration getConfiguration() {
        return configuration;
    }

    public LiveSpeechRecognizer getLsr() {
        return lsr;
    }

    public StreamSpeechRecognizer getSsr() {
        return ssr;
    }
    //endregion
}
