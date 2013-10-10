package deco2800.arcade.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import deco2800.arcade.util.Handler;

public class AsyncFuture <T> implements Future<T> {
    private T result;
    private boolean provided;
    private Handler<T> handler;

    public AsyncFuture() {
        result = null;
        provided = false;
        handler = null;
    }

    public synchronized void setHandler(Handler handler) {
        this.handler = handler;
        if (provided && handler != null) {
            handler.handle(result);
        }
    }

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
    
    public synchronized boolean cancel(boolean mayInterruptIfRunning) {
        if (this.isDone())
            return false;
        
        this.provide(null);
        return true;
    }
    
    public synchronized boolean isCancelled() {
        return provided && result == null;
    }

    public synchronized boolean isDone() {
        return provided;
    }

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

    public synchronized T get(long timeout, TimeUnit unit) {
        while (!provided) {
            try {
                this.wait(unit.toMillis(timeout));                
            } catch (InterruptedException e) {
                e.printStackTrace();
                cancel(false);
            }
        }

        return result;
    }
}
