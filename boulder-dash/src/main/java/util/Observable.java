package util;

/**
 * An Observable is an object that can notify Observers whenever their state changes.
 */
public interface Observable {
    /**
     * Adds an observer to the list of objects that observe this Observable.
     *
     * @param o a non-null Observer
     */
    void registerObserver(Observer o);

    /**
     * Removes an observer from the list of objects that observe this Observable.
     *
     * @param o an Observer
     */
    void removeObserver(Observer o);

    /**
     * Notifies all Observers of this Observable to update their state.
     */
    void notifyObservers();
}
