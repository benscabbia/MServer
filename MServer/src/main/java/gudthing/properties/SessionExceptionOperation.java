package gudthing.properties;

import gudthing.models.ClientWithInstruction;
import gudthing.models.spark.ClientWithSparkInstruction;

import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ben on 24/05/2016.
 */
public class SessionExceptionOperation {
    private static TreeSet<SessionData> sessionData = new TreeSet<SessionData>();
    private static final AtomicInteger counter = new AtomicInteger();

    public static AtomicInteger getCounter() {
        return counter;
    }

    public static void incrementCounter(){
        counter.incrementAndGet();
    }

    public static TreeSet<SessionData> getSessionData() {
        return sessionData;
    }

    public static void addSessionData(SessionData data) {
        sessionData.add(data);
    }

    public static void addSessionData(ClientWithInstruction clientWithInstruction, String response){
        sessionData.add(new SessionData(response, clientWithInstruction.getInstructionType().toString(),
                clientWithInstruction.getIpAddress()));

        incrementCounter();
    }

    public static void addSessionData(ClientWithSparkInstruction clientWithSparkInstruction, String response){
        sessionData.add(new SessionData(response, clientWithSparkInstruction.getSingleClientSparkInstruction().toString(),
                clientWithSparkInstruction.getIpAddress()));
        incrementCounter();
    }
}
