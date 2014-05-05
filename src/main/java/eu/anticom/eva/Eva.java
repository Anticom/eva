package eu.anticom.eva;

import eu.anticom.eva.config.Configuration;
import eu.anticom.eva.event.EventBus;
import eu.anticom.eva.hooks.ShutdownHook;
import eu.anticom.eva.module.io.*;
import eu.anticom.eva.util.ClassLoader;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Eva {
    //region constants
    public static final String CONFIG_FILE = "eva.properties";
    public static final String CONFIG_FILE_XML = "config.xml";
    //endregion

    protected EventBus eventBus = new EventBus();
    protected Configuration configuration;
    protected XMLConfiguration xmlConfiguration;

    //region io
    protected ConcurrentHashMap<String, IModule> modules = new ConcurrentHashMap<String, IModule>();
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
    }
    //endregion

    //region high-level API
    public void boot() {
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

    public void startThreads() {
        textInputThread = new Thread(modules.get("textInput"));
        audioOutputThread = new Thread(modules.get("audioOutput"));

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
        configuration = new Configuration();
        try {
            configuration.load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File xmlConfigFile = new File(CONFIG_FILE_XML);
        xmlConfiguration = new XMLConfiguration();
        try {
            xmlConfiguration.load(xmlConfigFile);
        } catch (ConfigurationException e) {
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
        ConcurrentHashMap<String, String> inputProperties = configuration.filter("eva.modules");
        Iterator it = inputProperties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry property = (Map.Entry) it.next();

            String key = (String) property.getKey();
            String val = (String) property.getValue();

            try {
                System.out.printf("Loading module:       [%s]\n", val);
                IModule module = (IModule) ClassLoader.load(val);
                System.out.printf("Booting module:       [%s]\n", val);
                module.boot();
                modules.put(key, module);
            } catch (Exception e) {
                System.err.printf("Unable to load class: [%s]\n", val);
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
    }
    //endregion
}
