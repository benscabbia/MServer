package gudthing.models;

/**
 * Created by Ben on 07/04/2016.
 */
public class ClientWithSelection extends Client {
    private boolean selected;
    private Message message;

    private InstructionType instructionType;

    public ClientWithSelection(Client client, boolean selected){
        this.clientID = client.getClientID();
        this.ipAddress = client.getIpAddress();
        this.portNumber = client.getPortNumber();
        this.description = client.getDescription();
        this.selected = selected;


        //this.message = message;

    }

    public ClientWithSelection(){

    }

    public boolean getSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClientWithSelection{" +
                "selected=" + selected +
                ", message=" + message +
                '}';
    }
}


