package util;

/**
 * An Observer is an object that can execute an action when notified of a change of state
 * from the Observable object they observe.
 */
public interface Observer {
    /**
     * Executes an action when notified of a change.
     */
    void update();
}
