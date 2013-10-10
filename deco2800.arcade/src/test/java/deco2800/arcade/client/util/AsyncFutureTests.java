package deco2800.arcade.util;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import deco2800.arcade.util.AsyncFuture;
import deco2800.arcade.util.Handler;

public class AsyncFutureTests {

    AsyncFuture<Integer> future;
    Thread t;
    boolean handled;

    @Before
    public void reset() {
        future = new AsyncFuture<Integer>();
        t = null;
        handled = false;
    }

    @After
    public void waitForThread() {
        try {
            if (t != null)
                t.join();
        } catch (InterruptedException e) {
            // just fail - this is ridiculous
            assertEquals(false, true);
        }
    }

    @Test
    public void testInitState() {     
        assertEquals(false, future.isCancelled());
        assertEquals(false, future.isDone());
    }

    @Test
    public void testCancelledState() {
        future.cancel(false);
        assertEquals(true, future.isCancelled());
        assertEquals(true, future.isDone());
    }

    @Test
    public void testProvide() {
        future.provide(42);
        assertEquals(false, future.isCancelled());
        assertEquals(true, future.isDone());
        assertEquals(new Integer(42), future.get());
    }
    
    @Test
    public void testAsyncCancel() {
        t = new Thread() {
            public void run() {
                Integer res = future.get();
                assertEquals(null, res);
            }
        };
        t.start();

        future.cancel(false);
        assertEquals(null, future.get());
    }

    @Test
    public void testAsyncProvide() {
        t = new Thread() {
            public void run() {
                Integer res = future.get();
                assertEquals(new Integer(42), null);
            }
        };
        t.start();

        future.provide(42);
        assertEquals(true, future.isDone());
        assertEquals(new Integer(42), future.get());
    }

    @Test
    public void testHandler() {
        future.setHandler(new Handler<Integer>() {
            public void handle(Integer val) {
                assertEquals(new Integer(42), val);
                synchronized (this) {
                    handled = true;
                }
            }
        });

        future.provide(42);
        synchronized (this) {
            assertEquals(true, handled);
        }
    }

    @Test
    public void testAsyncHandler() {
        future.setHandler(new Handler<Integer>() {
            public void handle(Integer val) {
                assertEquals(new Integer(42), val);
                synchronized (this) {
                    handled = true;
                }
            }
        });

        t = new Thread() {
            public void run() {
                Integer res = future.get();
                synchronized (this) {
                    assertEquals(true, handled);
                }

                assertEquals(new Integer(42), res);
            }
        };
        t.start();
        future.provide(42);
        synchronized (this) {
            assertEquals(true, handled);
        }
    }
}
