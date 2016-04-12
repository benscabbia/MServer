package gudthing.models;

/**
 * Created by Ben on 07/04/2016.
 */
public class ClientWithSelection extends Client {
    private boolean selected;

    public ClientWithSelection(Client client, boolean selected){
        this.clientID = client.getClientID();
        this.ipAddress = client.getIpAddress();
        this.description = client.getDescription();
        this.selected = selected;
    }

    public ClientWithSelection(){

    }


    public boolean getSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}


