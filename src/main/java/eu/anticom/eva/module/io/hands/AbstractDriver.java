package eu.anticom.eva.module.io.hands;

/**
 * This is meant to be a bridge between Eva's actions, that it can perform, and the real world
 */
public abstract class AbstractDriver {
    public AbstractDriver() {

    }

    abstract public Actions getPossibleActions();

    public void perform(Action action) {

    }
}
