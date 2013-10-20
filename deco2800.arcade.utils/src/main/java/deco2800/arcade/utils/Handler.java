package deco2800.arcade.utils;

/**
 * A generic interface for handling objects, most likely as callbacks.
 */
public interface Handler <T> {
    /**
     * Handles the given object.
     *
     * @param obj The object to handle.
     */
    void handle(T obj);
}
