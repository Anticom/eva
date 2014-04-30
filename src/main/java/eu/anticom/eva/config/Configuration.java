package eu.anticom.eva.config;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration {
    protected Properties properties;

    protected static final String HEADER = "eva";

    public Configuration(Properties propierties) {
        this.properties = propierties;
    }

    public Configuration() {
        this(new Properties());
    }

    public Configuration set(String key, String value) {
        properties.setProperty(key, value);

        return this;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public Enumeration<?> getKeys() {
        return properties.propertyNames();
    }

    /**
     * Lookup by portion of namespace
     * <p/>
     * All keys containing that namespace will be returned
     *
     * @param namespace String
     * @return Vector<String>
     */
    public Vector<String> lookup(String namespace) {
        Enumeration<?> enumeration = properties.propertyNames();

        Vector<String> output = new Vector<String>();
        while (enumeration.hasMoreElements()) {
            String possibleKey = (String) enumeration.nextElement();

            if(possibleKey.startsWith(namespace)) {
                output.add(possibleKey);
            }
        }

        return output;
    }

    public boolean has(String key) {
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String possibleKey = (String) enumeration.nextElement();

            if(possibleKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Filter configuration by namespace lookup
     *
     * Returns a HashMap containing key-value pairs with config
     *
     * @param namespace
     * @return
     */
    public ConcurrentHashMap<String, String> filter(String namespace, boolean trim) {
        ConcurrentHashMap<String, String> output = new ConcurrentHashMap<String, String>();

        for(String foundKey : lookup(namespace)) {
            String key = foundKey;
            String value = get(foundKey);
            if(trim) {
                key = key.substring(namespace.length() + 1);
                /*
                if(key.startsWith(".")) {
                    key = key.substring(1);
                }
                */
            }
            output.put(key, value);
        }

        return output;
    }

    public ConcurrentHashMap<String, String> filter(String namespace) {
        return filter(namespace, true);
    }



    public Configuration load(File file) throws IOException {
        return load(new FileReader(file));
    }

    public Configuration load(FileReader reader) throws IOException {
        properties.load(reader);

        return this;
    }

    public Configuration load(InputStream inputStream) throws IOException {
        properties.load(inputStream);

        return this;
    }

    public Configuration save(File file) throws IOException {
        return save(new FileWriter(file));
    }

    public Configuration save(FileWriter writer) throws IOException {
        properties.store(writer, HEADER);

        return this;
    }

    public Configuration save(OutputStream outputStream) throws IOException {
        properties.store(outputStream, HEADER);

        return this;
    }

    public Properties getProperties() {
        return properties;
    }
}
