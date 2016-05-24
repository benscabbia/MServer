package gudthing.controllers;

import gudthing.models.Client;
import gudthing.models.ClientWithSelection;
import gudthing.models.ClientWithSelectionListWrapper;
import gudthing.models.spark.*;
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

            //more than one client selected, multi-client spark query
            ArrayList<ClientWithSparkInstruction> allClientsWithSparkInstructions = new ArrayList<ClientWithSparkInstruction>();
            for (ClientWithSelection clientSelected : allClients) {
                allClientsWithSparkInstructions.add(new ClientWithSparkInstruction(clientSelected, new SingleClientSparkInstruction("test", SparkType.WORDCOUNT,false)));
            }

            ClientWithSparkInstructionWrapper sparkInstructionWrapper = new ClientWithSparkInstructionWrapper();
            sparkInstructionWrapper.setClientList(allClientsWithSparkInstructions);

            model.addAttribute("allClients", allClients);
            model.addAttribute("sparkInstructionWrapper", sparkInstructionWrapper);
            model.addAttribute("sparkInstructionTypes", SparkType.values());

            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("allClients", allClients);
                context.setVariable("sparkInstructionWrapper", sparkInstructionWrapper);
                context.setVariable("sparkInstructionTypes", SparkType.values());
            }
        }

        return "SparkController/sparkSender";
        //in query sender, i then need to return it to return "SparkController/results";
    }

    //   ------------------------------------- POST sparkSender/results---------------------------------------------------
    @RequestMapping(value="/sparkSender/results", method = RequestMethod.POST)
    public String sendQuery(@ModelAttribute ClientWithSparkInstructionWrapper sparkInstructionWrapper, Model model){

        List<ClientWithSparkInstruction> clientsWithInstruction = sparkInstructionWrapper.getClientList();

        for(ClientWithSparkInstruction client : clientsWithInstruction){
            sparkHandler(client);
        }

//        model.addAttribute("singleClientSparkInstructions", clientWithSparkInstructions);
//

        model.addAttribute("clientsWithSparkInstruction", clientsWithInstruction);
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("clientsWithSparkInstruction", clientsWithInstruction);
        }
//
//
//        model.addAttribute("clientsWithInstruction", clientsWithInstruction);
//        //model.addAttribute("instructionWrapper", instructionWrapper);
//        System.out.println(instructionWrapper.getClientList() != null ? instructionWrapper.getClientList().size() : "null list");
//
//        if(false){
//            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
//            context.setVariable("clientsWithInstruction", clientsWithInstruction);
//        }

        return "SparkController/results";
    }

    //Single Client exercution
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

            //execute spark handler per client query (1 client)
            for(ClientWithSparkInstruction client : clientWithSparkInstructions){
                sparkHandler(client);
            }

             model.addAttribute("singleClientSparkInstructions", clientWithSparkInstructions);

            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("singleClientSparkInstructions", clientWithSparkInstructions);
            }
        }

        return "SparkController/clientResults";
    }



    private void sparkHandler(ClientWithSparkInstruction client) {

        SparkHandler.handleRequest(client);


//        switch(client.getSingleClientSparkInstruction().getSparkType()){
//
//            case WORDSEARCH:
//                //TODO wordsearch implementaiton
//            case WORDCOUNT:
//                System.out.println("WORDCOUNT");
//
//                SparkHandler.handleRequest(client);
//                break;
//
//            default:
//                System.out.println("Default bit");
//                break;
//        }
    }



}
