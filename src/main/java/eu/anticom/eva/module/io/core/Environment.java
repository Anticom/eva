package eu.anticom.eva.module.io.core;

import eu.anticom.eva.context.LocationContext;

import java.util.Date;

/**
 * Current (and sort of static) environment, Eva is at
 */
public class Environment {
    protected LocationContext location;
    protected Date bootetAt;

    public Environment() {
        location = new LocationContext();
        bootetAt = new Date();
    }

    //region getters
    public LocationContext getLocation() {
        return location;
    }

    public Date getBootetAt() {
        return bootetAt;
    }

    public Date getTime() {
        return new Date();
    }
    //endregion
}
