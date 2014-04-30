package eu.anticom.eva.util;

public class ClassLoader {
    public static Object load(String fqn) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> classname = Class.forName(fqn);
        return classname.newInstance();
    }

    public static Class<?> getClass(String fqn) throws ClassNotFoundException {
        return Class.forName(fqn);
    }
}
