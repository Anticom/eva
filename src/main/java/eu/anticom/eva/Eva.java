package eu.anticom.eva;

import edu.cmu.sphinx.api.Configuration;
import eu.anticom.eva.event.EventBus;
import eu.anticom.eva.event.EventEmitter;
import eu.anticom.eva.event.EventListener;
import eu.anticom.eva.hooks.ShutdownHook;
import eu.anticom.eva.io.*;
import eu.anticom.eva.util.ClassLoader;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Eva {
    protected EventBus eventBus = new EventBus();
    protected eu.anticom.eva.config.Configuration configuration = new eu.anticom.eva.config.Configuration();

    //region io
    protected ConcurrentHashMap<String, EventEmitter> inputChannels = new ConcurrentHashMap<String, EventEmitter>();
    protected ConcurrentHashMap<String, EventListener> outputChannels = new ConcurrentHashMap<String, EventListener>();
    //endregion

    //region constants
    public static final String CONFIG_FILE = "eva.properties";
    //endregion

    //region threads
    protected Thread textInputThread;
    protected Thread audioOutputThread;
    /*
    protected Thread textOutputThread;
    protected Thread audioOutputThread;
    protected Thread visualInputThread;
    protected Thread handsThread;
    protected Thread brainThread;
    */
    //endregion

    //region constructors
    public Eva() {
        loadConfiguration();
        setSystemProperties();

        //region set up io
        loadCore();
        loadInputs();
        loadOutputs();
        registerIO();
        //endregion

        bootModules();

        //region shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(this)));
        //endregion
    }
    //endregion

    //region high-level API
    public void bootModules() {
        //enable heavy operations
        //getAudioInput().startListening();
        getVisualInput().startWatching();

        //region threads
        textInputThread.start();
        audioOutputThread.start();
        //endregion
    }

    public void wakeup() {
        bootModules();
    }

    public void hibernate() {
        //disable heavy operations
        getAudioInput().stopListening();
        getVisualInput().stopWatching();

        //region threads
        //textInputThread.
        //endregion
    }
    //endregion

    //region getters

    /**
     * @deprecated
     */
    public Core getCore() {
        return (Core) inputChannels.get("core");
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public eu.anticom.eva.config.Configuration getConfiguration() {
        return configuration;
    }

    public ConcurrentHashMap<String, EventEmitter> getInputChannels() {
        return inputChannels;
    }

    public ConcurrentHashMap<String, EventListener> getOutputChannels() {
        return outputChannels;
    }

    /**
     * @deprecated
     */
    public TextInput getTextInput() {
        return (TextInput) inputChannels.get("text");
    }

    /**
     * @deprecated
     */
    public TextOutput getTextOutput() {
        return (TextOutput) outputChannels.get("text");
    }

    /**
     * @deprecated
     */
    public AudioInput getAudioInput() {
        return (AudioInput) inputChannels.get("audio");
    }

    /**
     * @deprecated
     */
    public AudioOutput getAudioOutput() {
        return (AudioOutput) outputChannels.get("audio");
    }

    /**
     * @deprecated
     */
    public VisualInput getVisualInput() {
        return (VisualInput) inputChannels.get("visual");
    }

    /**
     * @deprecated
     */
    public Hands getHands() {
        return (Hands) outputChannels.get("hands");
    }

    //endregion

    //region initialisation
    protected void newBootModules() {

    }

    protected void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        //create config file, if not exists
        if (!configFile.exists()) {
            try {
                //create config file
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configuration = new eu.anticom.eva.config.Configuration();
        try {
            configuration.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setSystemProperties() {
        ConcurrentHashMap<String, String> systemProperties = configuration.filter("system");
        Iterator it = systemProperties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry property = (Map.Entry) it.next();
            String key = (String) property.getKey();
            String val = (String) property.getValue();

            System.setProperty(key, val);

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    protected void loadCore() {
        String className = configuration.get("eva.io.core");
        Object core = null;
        try {
            core = ClassLoader.load(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        inputChannels.put("core", (EventEmitter) core);
        outputChannels.put("core", (EventListener) core);
    }

    protected void loadInputs() {
        ConcurrentHashMap<String, String> inputProperties = configuration.filter("eva.io.input");
        Iterator it = inputProperties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry property = (Map.Entry) it.next();

            String key = (String) property.getKey();
            String val = (String) property.getValue();

            try {
                inputChannels.put(key, (EventEmitter) eu.anticom.eva.util.ClassLoader.load(val));
            } catch (Exception e) {
                e.printStackTrace();
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        /*
        inputChannels.put("text", new TextInput());
        //inputChannels.put("audio", new AudioInput(getAudioInputConfiguration()));
        inputChannels.put("visual", new VisualInput());
        */
    }

    protected void loadOutputs() {
        ConcurrentHashMap<String, String> inputProperties = configuration.filter("eva.io.output");
        Iterator it = inputProperties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry property = (Map.Entry) it.next();

            String key = (String) property.getKey();
            String val = (String) property.getValue();

            try {
                outputChannels.put(key, (EventListener) ClassLoader.load(val));
            } catch (Exception e) {
                e.printStackTrace();
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

        /*
        outputChannels.put("text", new TextOutput());
        outputChannels.put("audio", new AudioOutput());
        outputChannels.put("hands", new Hands());
        */
    }

    protected void registerIO() {
        //region add inputChannels & outputChannels to bus
        eventBus.register(outputChannels.elements());
        eventBus.connectEmitters(inputChannels.elements());
        //endregion

        //region threads
        textInputThread = new Thread((Runnable) inputChannels.get("text"));
        audioOutputThread = new Thread((Runnable) outputChannels.get("audio"));
        //endregion
    }
    //endregion

    //region auxiliaries
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
    //endregion
}
