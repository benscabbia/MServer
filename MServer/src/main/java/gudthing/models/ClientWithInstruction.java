package gudthing.models;

/**
 * Created by Ben on 18/04/2016.
 */
public class ClientWithInstruction extends Client {

    public String message;
    public String instructionType;

    public ClientWithInstruction(Client client, String message, String instructionType){

        this.clientID = client.getClientID();
        this.ipAddress = client.getIpAddress();
        this.portNumber = client.getPortNumber();
        this.description = client.getDescription();

        this.message = message;
        this.instructionType = instructionType;


        //this.message = message;

    }

    public ClientWithInstruction(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }
}
