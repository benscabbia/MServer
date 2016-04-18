package gudthing.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 18/04/2016.
 */
public class ClientWithInstructionWrapper {

        private ArrayList<ClientWithInstruction> clientList;

        public List<ClientWithInstruction> getClientList(){
            return clientList;
        }

        public void setClientList(ArrayList<ClientWithInstruction> clients){
            this.clientList = clients;
        }

        public ClientWithInstructionWrapper(){

        }

}


