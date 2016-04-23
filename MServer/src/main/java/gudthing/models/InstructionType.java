package gudthing.models;

/**
 * Created by Ben on 14/04/2016.
 */
public enum InstructionType {
    DEFAULT("/health"),
    HEALTH("/health"),
    INFO("/info"),
    MAPPINGS("/mappings"),
    METRICS("/metrics"),
    QUERY("/query"),
    SHUTDOWN("/shutdown");

    private String mapping;

    InstructionType(String mapping){
        this.mapping = mapping;
    }

    public String mapping() {
        return mapping;
    }
}

