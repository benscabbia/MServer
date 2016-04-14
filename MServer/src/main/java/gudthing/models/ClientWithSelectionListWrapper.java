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

    public List<ClientWithSelection> getSelectedClients(){
        if(clientList != null && clientList.size() > 1){

            List<ClientWithSelection> selectedClients = new ArrayList<ClientWithSelection>();

            for(ClientWithSelection client : clientList){
                if(client.getSelected() == true){
                    selectedClients.add(client);
                }
            }
            return selectedClients;
        }else{
            return null;
        }
    }
}
