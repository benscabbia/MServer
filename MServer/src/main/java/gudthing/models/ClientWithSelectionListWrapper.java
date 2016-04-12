package gudthing.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 08/04/2016.
 */
public class ClientWithSelectionListWrapper {

    private ArrayList<ClientWithSelection> clientList;

    public List<ClientWithSelection> getClientList(){
        return clientList;
    }

    public void setClientList(ArrayList<ClientWithSelection> clients){
        this.clientList = clients;
    }

    public ClientWithSelectionListWrapper(){

    }
}
