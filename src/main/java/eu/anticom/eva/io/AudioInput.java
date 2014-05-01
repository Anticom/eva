package eu.anticom.eva.io;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import eu.anticom.eva.event.EventEmitter;

import java.io.IOException;
import java.io.InputStream;

public class AudioInput extends EventEmitter implements IOModule {
    protected Configuration configuration;
    protected LiveSpeechRecognizer lsr;
    protected StreamSpeechRecognizer ssr;

    //region constructors
    public AudioInput() {
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
    }
    //endregion

    @Override
    public void boot() {
        lsr.startRecognition(true);
    }

    @Override
    public void shutdown() {
        lsr.stopRecognition();
    }

    //region high-level API
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

    protected Configuration getAudioInputConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
        configuration.setDictionaryPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
        configuration.setLanguageModelPath("models/language/en-us.lm.dmp");

        //configuration.setAcousticModelPath("resource:/sphinx/en/acoustic");
        //configuration.setDictionaryPath("resource:/sphinx/en/acoustic/dict/cmudict.0.6d");
        configuration.setLanguageModelPath("resource:/sphinx/en/language/cmusphinx-5.0-en-us.lm.dmp");

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
}
