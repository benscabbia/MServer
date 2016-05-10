package gudthing.models.spark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 10/05/2016.
 */
public class ClientWithSparkInstructionWrapper {

    private ArrayList<ClientWithSparkInstruction> clientList;

    public List<ClientWithSparkInstruction> getClientList(){
        return clientList;
    }

    public void setClientList(ArrayList<ClientWithSparkInstruction> clients){
        this.clientList = clients;
    }

    public ClientWithSparkInstructionWrapper(){

    }


}
