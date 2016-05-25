package gudthing.properties;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ben on 24/05/2016.
 */
public class SessionFailureOperation {
    private static final AtomicInteger counter = new AtomicInteger();

    public static AtomicInteger getCounter() {
        return counter;
    }

    public static void incrementCounter(){
        counter.incrementAndGet();
    }
}
