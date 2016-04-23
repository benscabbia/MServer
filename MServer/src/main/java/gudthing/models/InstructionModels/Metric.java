package gudthing.models.InstructionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

/**
 * Created by Ben on 23/04/2016.
 * Class to handle the metric JSON deserialization
 * The properties are given to the closest Gigabyte
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric {
    @JsonProperty(value="mem")
    private float memory;
    @JsonProperty(value="mem.free")
    private float memoryAvailable;
    private int processors;
    private long uptime;

    public Metric(float memory, float memoryAvailable, int processors, int uptime) {
        this.memory = memory;
        this.memoryAvailable = memoryAvailable;
        this.processors = processors;
        this.uptime =  TimeUnit.MILLISECONDS.toMinutes(uptime);
    }

    public Metric() {
    }

    public float getMemory() {
        return memory;
    }

    public void setMemory(float mem) {
        this.memory = mem / 10000;
    }

    public float getMemoryAvailable() {
        return memoryAvailable;
    }

    public void setMemoryAvailable(float memFree) {
        this.memoryAvailable = memFree / 10000;
    }

    public int getProcessors() {
        return processors;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = TimeUnit.MILLISECONDS.toMinutes(uptime);
    }

    @Override
    public String toString() {
        String metrics = "Total Memory: " + memory + " GB, ";
        metrics += "\nFree Memory: " + memoryAvailable + " GB, ";
        metrics += "\nProcessors: " + processors + ", ";
        metrics += "\nUptime: " + uptime + " minutes, ";

        return metrics;
    }
}
