package deco2800.arcade.utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import deco2800.arcade.utils.Handler;

/**
 * Asynchronous future that can safely be provided with data from any thread.
 * Implements Java's future interface and additionally provides a mechanism
 * for asynchronously calling a handler when the future's data arrives.
 */
public class AsyncFuture <T> implements Future<T> {
    private T result;
    private boolean provided;
    private Handler<T> handler;

    /**
     * Initialises an empty AsyncFuture.
     */
    public AsyncFuture() {
        result = null;
        provided = false;
        handler = null;
    }

    /**
     * Sets the future's handler. This handler's handle(T) method will be
     * called when this future is provided with a result.
     *
     * @param handler The object which will handle the future's result once
     *                it is provided.
     */
    public synchronized void setHandler(Handler<T> handler) {
        this.handler = handler;
        if (provided && handler != null) {
            handler.handle(result);
        }
    }

    /**
     * Provides the future with a result. This may safely be called from any
     * thread. If the future has already been provided with a result or is
     * cancelled, the result will be ignored. Otherwise, the future's result
     * is set to the given result and the future's handler (if it has one)
     * is called in this thread.
     *
     * @param result The result to provide the future with.
     */
    public synchronized void provide(T result) {            
        // while we were waiting this might have been cancelled so handle
        // that condition (or this future might have already been provided
        // with the result)
        if (provided)
            return;
        
        this.result = result;
        provided = true;
        if (handler != null)
            handler.handle(result);
        this.notify();
    }
    
    /**
     * Cancels the future if it hasn't yet been provided. A cancelled future
     * has its result set to null (i.e get() == null). The mayInterruptIfRunning
     * argument is for conformance to the Future<T> interface and is ignored.
     *
     * @param mayInterruptIfRunning This value is ignored, just pass any boolean.
     */
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (this.isDone())
            return false;
        
        this.provide(null);
        return true;
    }
    
    /**
     * Returns whether the future has been cancelled.
     *
     * @return Whether the future has been cancelled.
     */
    public synchronized boolean isCancelled() {
        return provided && result == null;
    }

    /**
     * Returns whether the future has been either cancelled or provided with
     * a result. A future in this state will not block in the get() method
     * waiting for a result to be provided.
     *
     * @return Whether the future is done (i.e get() will not block)
     */
    public synchronized boolean isDone() {
        return provided;
    }

    /**
     * Returns the future's result, or null if it was cancelled. If the
     * future has yet to be provided with a result (or cancelled), this
     * method will block until that happens.
     *
     * @return The future's result, or null if it was cancelled.
     */
    public synchronized T get() {
        while (!provided) {
            try {             
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                cancel(false);
            }
        }

        return result;
    }

    /**
     * Behaves identically to get(), except with a timeout associated
     * with any blocking. If the timeout is hit, null is returned
     * regardless of whether the future was cancelled or not.
     *
     * @param timeout The amount of time that this method is allowed
     *                to block for.
     * @param unit    The unit of the timeout (i.e milliseconds, seconds)
     * @return        The future's result, or null if it was cancelled 
     *                or the timeout waas reached.
     */
    public synchronized T get(long timeout, TimeUnit unit) {
        while (!provided) {
            try {
                this.wait(unit.toMillis(timeout));                
            } catch (InterruptedException e) {
                cancel(false);
            }
        }

        return result;
    }
}
