package util;

/**
 * A Command is a reversible action on a certain receiver.
 */
public interface Command {
    /**
     * Executes an action.
     */
    void execute();

    /**
     * Reverses the action.
     */
    void undo();
}
