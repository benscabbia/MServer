package gudthing.controllers;

import gudthing.models.Client;
import gudthing.models.ClientWithSelection;
import gudthing.models.ClientWithSelectionListWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 06/04/2016.
 */

@Controller
@RequestMapping("/query")
public class QueryController {
    private boolean started = false;

    private List<Client> allClients = new ArrayList<Client>();
    private ArrayList<ClientWithSelection> allClientsWithSelection = new ArrayList<ClientWithSelection>();


    //   ------------------------------------- GET  /---------------------------------------------------
    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model) {

        if(!started){
            populateClientList();

            for(int i=0; i<allClients.size(); i++){
                //add clients to the list which also has the selection, false as default
                allClientsWithSelection.add(i, new ClientWithSelection(allClients.get(i), false));
            }
            started = true;
        }

        //create a wrapper
        ClientWithSelectionListWrapper wrapper = new ClientWithSelectionListWrapper();
        wrapper.setClientList(allClientsWithSelection);

        model.addAttribute("wrapper", wrapper);

        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("wrapper", wrapper);
        }
        return "QueryController/queryIndex";
    }

    //   ------------------------------------- POST /submitQuery---------------------------------------------------

    @RequestMapping(value="/submitQuery", method = RequestMethod.POST)
    public String processQuery(@ModelAttribute ClientWithSelectionListWrapper wrapper, Model model){

        System.out.println(wrapper.getClientList() != null ? wrapper.getClientList().size() : "null list");
        System.out.println("--");
        model.addAttribute("wrapper", wrapper);

        if(wrapper != null){
            List<ClientWithSelection> allClients = wrapper.getSelectedClients();

            //no selection was made
            if(allClients.size() < 1){
                return "QueryController/queryIndex";
            }

            //TESTING PURPOSES
            ClientWithSelection firstClient = allClients.get(0);
            System.out.println("#####################################TESTING##############################");
            System.out.println(firstClient.toString());
        }








        return "QueryController/queryIndex";
    }




    private void populateClientList(){
        allClients.add(new Client(1, "192.168.1.151", "8081", "Ben's Desktop"));
        allClients.add(new Client(2, "192.168.1.65", "123", "another IP address"));
        allClients.add(new Client(3, "192.168.3.48", "5895"));
        allClients.add(new Client(4, "192.168.24.45", "9999"));
    }
}
