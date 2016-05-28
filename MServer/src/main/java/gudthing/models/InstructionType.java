package gudthing.models;

/**
 * Created by Ben on 14/04/2016.
 */
public enum InstructionType {
    DEFAULT("/health"),
    HEALTH("/health"),
    METRICS("/metrics"),
    QUERY_MYSQL("/query"),
    QUERY_MONGO("/query"),
    SHUTDOWN("/shutdown");

    private String mapping;

    InstructionType(String mapping){
        this.mapping = mapping;
    }

    public String mapping() {
        return mapping;
    }
}

