package gudthing.models.InstructionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Ben on 21/04/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Health {
    private String status;
    private boolean connected = false;

    public Health(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String toString() {
        return "Health{" +
                "status='" + status + '\'' +
                '}';
    }

    public Health(){}
}



//"status" : "UP",
//        "diskSpace" : {
//        "status" : "UP",
//        "total" : 921558839296,
//        "free" : 230497275904,
//        "threshold" : 10485760