package it.aranciaict.jobmatch.web.rest.vm;

import ch.qos.logback.classic.Logger;

// TODO: Auto-generated Javadoc
/**
 * View Model object for storing a Logback logger.
 */
public class LoggerVM {

    /** The name. */
    private String name;

    /** The level. */
    private String level;

    /**
     * Instantiates a new logger VM.
     *
     * @param logger the logger
     */
    public LoggerVM(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    /**
     * Instantiates a new logger VM.
     */
    public LoggerVM() {
        // Empty public constructor used by Jackson.
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LoggerVM{" +
            "name='" + name + '\'' +
            ", level='" + level + '\'' +
            '}';
    }
}
