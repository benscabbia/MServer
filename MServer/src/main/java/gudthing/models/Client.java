package gudthing.models;

/**
 * Created by Ben on 01/04/2016.
 */
public class Client {


    private int clientID; //mandatory
    private String ipAddress; //mandatory
    private String description;
    //sets the technical  of a client
    private SystemInformation systemInformation;

    public Client(int clientID, String ipAddress, String description){
        this.clientID = clientID;
        this.ipAddress = ipAddress;
        this.description = description;
    }

    public Client(int clientID, String ipAddress) {
        this.clientID = clientID;
        this.ipAddress = ipAddress;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemInformation getSystemInformation() {
        return systemInformation;
    }

    public void setSystemInformation(SystemInformation systemInformation) {
        this.systemInformation = systemInformation;
    }
}
