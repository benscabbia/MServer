package gudthing.controllers;

import gudthing.models.Client;
import gudthing.models.ClientWithInstruction;
import gudthing.models.ClientWithSelection;
import gudthing.models.ClientWithSelectionListWrapper;
import gudthing.models.spark.ClientWithSparkInstruction;
import gudthing.models.spark.SingleClientSparkInstruction;
import gudthing.models.spark.SingleClientSparkInstructionWrapper;
import gudthing.models.spark.SparkType;
import gudthing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 06/05/2016.
 */

@Controller
@RequestMapping("/spark")
public class SparkController {

    @Autowired
    private ClientRepository clientRepository;

    private ArrayList<ClientWithSelection> allClientsWithSelection;

    //   ------------------------------------- GET /spark/-------------------------------------------------
    //TODO probs should just show an index page here
    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model) {
        //required to remove thymeleaf error
        List<Client> allClients = clientRepository.findAll();
        allClientsWithSelection = new ArrayList<ClientWithSelection>();
        for(int i=0; i<allClients.size(); i++){
            //add clients to the list which also has the selection, false as default
            allClientsWithSelection.add(i, new ClientWithSelection(allClients.get(i), false));
        }

        //create a wrapper
        ClientWithSelectionListWrapper wrapper = new ClientWithSelectionListWrapper();
        wrapper.setClientList(allClientsWithSelection);

        model.addAttribute("wrapper", wrapper);


        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            //context.setVariable("wrapper", wrapper);
        }
        return "SparkController/sparkIndex";
    }

    //   ------------------------------------- POST /sparkSender---------------------------------------------------
    @RequestMapping(value="/sparkSender", method = RequestMethod.POST)
    public String processQuery(@ModelAttribute ClientWithSelectionListWrapper wrapper, Model model) {

        if (wrapper != null) {
            List<ClientWithSelection> allClients = wrapper.getSelectedClients();

            //no selection was made, select all
            if (allClients.size() < 1) {
                allClients = allClientsWithSelection;
            }

            if (allClients.size() == 1) {
                System.out.println("1 client selected#######################");
                Client client = allClients.get(0);
                int id = client.getClientID();
                return "redirect:/spark/" + id;
            }
        }

        //THIS IS WHERE I HANDLE MULTIPLE SPARK CLIENTS QUERIES (equi to /query/querysender)
        //so has client 1: instruction, client2: instruction etc

        return "QueryController/querySender";
        //in query sender, i then need to return it to return "SparkController/results";
    }

    //   ------------------------------------- GET /spark/{id}-------------------------------------------------
    @RequestMapping(value="/{clientID}", method=RequestMethod.GET)
    public String getClient(@PathVariable int clientID, Model model) {

        if (clientID > 0) {
            //TODO page sparksinglesender ffor single queires

            Client client = clientRepository.findClientByClientID(clientID);
            model.addAttribute(client);
            model.addAttribute("sparkType", SparkType.values());

            //an array that can store many spark instructions
            List<SingleClientSparkInstruction> clientSparkInstructions = new ArrayList<SingleClientSparkInstruction>();
            SingleClientSparkInstructionWrapper singleClientSparkInstructionWrapper = new SingleClientSparkInstructionWrapper();
            singleClientSparkInstructionWrapper.setSparkInstructionArray(clientSparkInstructions);

            model.addAttribute("singleClientSparkInstructionWrapper", singleClientSparkInstructionWrapper);
            model.addAttribute("singleClientSparkInstruction", new SingleClientSparkInstruction());

            if(false){
                WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
                //context.setVariable("client", client);
                context.setVariable("singleClientSparkInstruction", new SingleClientSparkInstruction());
                context.setVariable("sparkType", SparkType.values());
            }

        }else{
            return "SparkController/sparkIndex";
        }
        return "SparkController/sparkSingleSender";
    }

    //   ------------------------------------- POST /spark/{id}-------------------------------------------------
    @RequestMapping(value="/{clientID}", method = RequestMethod.POST)
    public String processSingleClient(@ModelAttribute SingleClientSparkInstructionWrapper wrapper, @PathVariable int clientID, Model model) {

        Client findClient = clientRepository.findClientByClientID(clientID);

        if (wrapper != null && findClient != null) {
            List<SingleClientSparkInstruction> instructions = wrapper.getSparkInstructionArray();

            List<ClientWithSparkInstruction> clientWithSparkInstructions = new ArrayList<ClientWithSparkInstruction>();

            //iterate over wrapper object and create new singleClientSparkInstruction objects
            //then add in list
            for(SingleClientSparkInstruction instruction : instructions){
                clientWithSparkInstructions.add(new ClientWithSparkInstruction(findClient, instruction));
            }

            //execute sparkhandler per one //TODO
            for(ClientWithSparkInstruction client : clientWithSparkInstructions){
                //queryHandler(client);
            }

             model.addAttribute("singleClientSparkInstructions", clientWithSparkInstructions);

            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("singleClientSparkInstructions", clientWithSparkInstructions);
            }
        }

        return "SparkController/clientResults";
    }



}
