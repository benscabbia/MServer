package gudthing.models;

/**
 * Created by Ben on 18/04/2016.
 */
public class ClientWithInstruction extends Client {

    public Message message;
    public InstructionType instructionType;

    public ClientWithInstruction(Client client, Message message, InstructionType instructionType){

        this.clientID = client.getClientID();
        this.ipAddress = client.getIpAddress();
        this.portNumber = client.getPortNumber();
        this.description = client.getDescription();

        this.message = message;
        this.instructionType = instructionType;
    }

    public ClientWithInstruction(){}

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }
}
