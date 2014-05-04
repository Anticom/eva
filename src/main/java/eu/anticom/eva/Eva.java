package eu.anticom.eva;

import eu.anticom.eva.config.Configuration;
import eu.anticom.eva.event.EventBus;
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
    protected ConcurrentHashMap<String, IModule> modules = new ConcurrentHashMap<String, IModule>();
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
        loadModules();
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

    public EventBus getEventBus() {
        return eventBus;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ConcurrentHashMap<String, IModule> getModules() {
        return modules;
    }

    /**
     * @deprecated
     */
    public TextInput getTextInput() {
        return (TextInput) modules.get("textInput");
    }

    /**
     * @deprecated
     */
    public TextOutput getTextOutput() {
        return (TextOutput) modules.get("textOutput");
    }

    /**
     * @deprecated
     */
    public AudioInput getAudioInput() {
        return (AudioInput) modules.get("audioInput");
    }

    /**
     * @deprecated
     */
    public AudioOutput getAudioOutput() {
        return (AudioOutput) modules.get("audioOutput");
    }

    /**
     * @deprecated
     */
    public VisualInput getVisualInput() {
        return (VisualInput) modules.get("visualInput");
    }

    /**
     * @deprecated
     */
    public Hands getHands() {
        return (Hands) modules.get("handsOutput");
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

    protected void loadModules() {
        ConcurrentHashMap<String, String> inputProperties = configuration.filter("eva.io");
        Iterator it = inputProperties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry property = (Map.Entry) it.next();

            String key = (String) property.getKey();
            String val = (String) property.getValue();

            try {
                System.out.printf("Loading module: [%s]\n", val);
                IModule module = (IModule) ClassLoader.load(val);
                System.out.printf("Booting module: [%s]\n", val);
                module.boot();
                modules.put(key, module);
            } catch (Exception e) {
                System.err.printf("Unable to load module class: [%s]\n", val);
                e.printStackTrace();
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    protected void registerIO() {
        //region add modules to bus
        eventBus.register(modules.elements());
        eventBus.connectEmitters(modules.elements());
        //endregion

        //region threads
        textInputThread = new Thread((Runnable) modules.get("textInput"));
        audioOutputThread = new Thread((Runnable) modules.get("audioOutput"));
        //endregion
    }
    //endregion
}
