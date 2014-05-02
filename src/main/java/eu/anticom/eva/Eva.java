package eu.anticom.eva;

import eu.anticom.eva.config.Configuration;
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
    protected Configuration configuration = new Configuration();

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
        System.out.println("All modules loaded, booted and configured");

        startThreads();
        //endregion

        //region shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(this)));
        //endregion
    }
    //endregion

    //region high-level API
    public void startThreads() {
        //region threads
        textInputThread.start();
        audioOutputThread.start();
        //endregion
    }

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

    public Configuration getConfiguration() {
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

        configuration = new Configuration();
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

        ((IOModule) core).boot();

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
                System.out.printf("Loading module: [%s]\n", val);
                IOModule module = (IOModule) ClassLoader.load(val);
                System.out.printf("Booting module: [%s]\n", val);
                module.boot();
                inputChannels.put(key, (EventEmitter) module);
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
                System.out.printf("Loading module: [%s]\n", val);
                IOModule module = (IOModule) eu.anticom.eva.util.ClassLoader.load(val);
                System.out.printf("Booting module: [%s]\n", val);
                module.boot();
                outputChannels.put(key, (EventListener) module);
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
}
