package eu.anticom.eva;

import eu.anticom.eva.module.io.IModule;

import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager {
    protected ConcurrentHashMap<Integer, IModule> modules;
    protected ConcurrentHashMap<Integer, Thread> threads;

    public ModuleManager() {}
    public ModuleManager(IModule[] modules) {
        for(IModule module : modules)
            add(module);
    }

    public void add(IModule module) {
        modules.put(module.hashCode(), module);
        threads.put(module.hashCode(), new Thread(module));
    }

    public void remove(String hash) {

    }

    public void remove(IModule module) {

    }

    public void boot(IModule module) {

    }

    public void shutdown(IModule module) {

    }

    public void bootAll() {

    }

    public void shutdownAll() {

    }

    public Object load(String fqn) {
        Object obj = null;
        try {
            obj = eu.anticom.eva.util.ClassLoader.load(fqn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
