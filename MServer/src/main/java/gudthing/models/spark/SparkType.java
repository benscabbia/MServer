package gudthing.models.spark;

/**
 * Created by Ben on 09/05/2016.
 */
public enum SparkType {
    WORDCOUNT("/wordcount"),
    WORDSEARCH("/wordsearch");

    private String mapping;

    SparkType(String mapping){
        this.mapping = mapping;
    }

    public String mapping() {
        return mapping;
    }
}
