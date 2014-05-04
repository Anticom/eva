package eu.anticom.eva.io;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import eu.anticom.eva.event.Event;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.EventType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AudioInput extends Module implements IModule {
    protected boolean running;

    protected Configuration configuration;
    protected LiveSpeechRecognizer lsr;
    protected StreamSpeechRecognizer ssr;

    @Override
    public void boot() {
        running = true;
        this.configuration = getAudioInputConfiguration();

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

        lsr.startRecognition(true);

        /*
        //some testing:
        System.out.println("testing some audio files:");
        String[] filenames = {
                "src/main/resources/mp3/GetOnTheHorse.mp3",
                "src/main/resources/mp3/TheSunIsUp.mp3"
        };
        for(String filename : filenames) {
            InputStream inputStream = getInputStreamByFilename(filename);
            SpeechResult speechResult = listenToStream(inputStream);
            System.out.println(speechResult.getHypothesis());
        }
        */
    }

    @Override
    public void shutdown() {
        running = false;
        lsr.stopRecognition();
    }

    @Override
    public void run() {
        SpeechResult speechResult;
        while(running) {
            speechResult = listen();
            String hypothesis = speechResult.getHypothesis();
            if(!hypothesis.isEmpty()) {
                emit(new Event(EventType.INPUT, this, hypothesis));
            }
        }
    }

    //region high-level API
    public SpeechResult listen() {
        return lsr.getResult();
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

    protected Configuration getAudioInputConfiguration() {
        Configuration configuration = new Configuration();
        // Set path to acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/acoustic/wsj");
        // Set path to dictionary.
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/acoustic/wsj/dict/cmudict.0.6d");
        // Set language model.
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/language/en-us.lm.dmp");


        /*
        //some validation should be done with this
        configuration.getAcousticModelPath();
        configuration.getDictionaryPath();
        configuration.getGrammarPath();
        configuration.getLanguageModelPath();

        configuration.getGrammarName();
        configuration.getSampleRate();
        configuration.getUseGrammar();
        */

        return configuration;
    }

    public static InputStream getInputStreamByFilename(String filename) {
        File inputFile = new File(filename);
        InputStream inputStream = null;
        try {
            inputStream = inputFile.toURI().toURL().openStream();
        } catch (IOException e) {
            System.out.println("Unable to create InputStream from file.");
            e.printStackTrace();
        }

        return inputStream;
    }
}
