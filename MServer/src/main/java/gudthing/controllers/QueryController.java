package gudthing.controllers;

import gudthing.models.*;
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
 * Created by Ben on 06/04/2016.
 */

@Controller
@RequestMapping("/query")
public class QueryController {
    @Autowired
    private ClientRepository clientRepository;

    private ArrayList<ClientWithSelection> allClientsWithSelection;

    //   ------------------------------------- GET  /---------------------------------------------------
    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model) {

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

        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("wrapper", wrapper);
        }
        return "QueryController/queryIndex";
    }


    //   ------------------------------------- POST /querySender---------------------------------------------------
    @RequestMapping(value="/querySender", method = RequestMethod.POST)
    public String processQuery(@ModelAttribute ClientWithSelectionListWrapper wrapper, Model model){

        if(wrapper != null) {
            List<ClientWithSelection> allClients = wrapper.getSelectedClients();

            //no selection was made, select all
            if (allClients.size() < 1) {
                allClients = allClientsWithSelection;
            }

            if(allClients.size() == 1){
                System.out.println("1 client selected#######################");
                Client client = allClients.get(0);
                int id = client.getClientID();
                //todo single client goes to comprehensive query
                return "redirect:/query/" + id;

//                return "QueryController/querySender/{+" + client.getClientID() + "}";

                /*
                * if client is 1 select, get its id and post url of
                * querySender/3 - 3 is client id
                * then load a page which gives use an option to select which queries he would like to send
                * -[need to design this page in my head]
                * */

            }


            ArrayList<ClientWithInstruction> allClientsWithInstructions = new ArrayList<ClientWithInstruction>();

            for(ClientWithSelection clientSelected : allClients){
                allClientsWithInstructions.add(new ClientWithInstruction(clientSelected, new Message(""), InstructionType.DEFAULT));
            }

            ClientWithInstructionWrapper instructionWrapper = new ClientWithInstructionWrapper();
            instructionWrapper.setClientList(allClientsWithInstructions);

            System.out.println("############################################################");
            //System.out.println("NUmber of clients in queryWrapper: " + queryWrapper.getQueries().size());
            System.out.println("NUmber of clients in instructionWrapper: " + instructionWrapper.getClientList().size());


            model.addAttribute("allClients", allClients);

            //model.addAttribute("queryWrapper", queryWrapper);
            model.addAttribute("instructionWrapper", instructionWrapper);

            model.addAttribute("instructionTypes", InstructionType.values());

            if(false){
                WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
                context.setVariable("allClients", allClients);
                //context.setVariable("queryWrapper", queryWrapper);
                context.setVariable("instructionWrapper", instructionWrapper);
                context.setVariable("instructionTypes", InstructionType.values());

            }
        }
            return "QueryController/querySender";
    }


    //   ------------------------------------- GET /query/{id}-------------------------------------------------
    @RequestMapping(value="/{clientID}", method=RequestMethod.GET)
    public String getClient(@PathVariable int clientID, Model model){

        if(clientID>0){
            Client client = clientRepository.findClientByClientID(clientID);
            model.addAttribute(client);
            model.addAttribute("instructionTypes", InstructionType.values());

            //an array that can store many instructions
            List<SingleClientInstruction> clientInstructions = new ArrayList<SingleClientInstruction>();
            SingleClientInstructionWrapper singleClientInstructionWrapper = new SingleClientInstructionWrapper();
            singleClientInstructionWrapper.setInstructionArray(clientInstructions);

            model.addAttribute("singleClientInstructionWrapper", singleClientInstructionWrapper);
            model.addAttribute("SingleClientInstruction", new SingleClientInstruction());

            if(false){
                WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
                context.setVariable("singleClientInstruction", new SingleClientInstruction());
            }

        }else{
            return "QueryController/querySender";
        }

        System.out.println("HITTING GET");
        System.out.println(clientID + " is the id found");



        return "QueryController/querySingleSender";
    }


    //   ------------------------------------- POST  /query/{id}---------------------------------------------------
    @RequestMapping(value="/{clientID}", method = RequestMethod.POST)
    public String processSingleClient(@ModelAttribute SingleClientInstructionWrapper wrapper, Model model){

        if(wrapper != null){
            List<SingleClientInstruction> instructions = wrapper.getInstructionArray();

            model.addAttribute("singleClientInstructions", instructions);

            if(false){
                WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
                context.setVariable("singleClientInstructions", instructions);
            }
        }

        return "QueryController/clientResults";
    }

    //   ------------------------------------- POST /results---------------------------------------------------
    @RequestMapping(value="/querySender/results", method = RequestMethod.POST)
    public String sendQuery(@ModelAttribute ClientWithInstructionWrapper instructionWrapper, Model model){

        List<ClientWithInstruction> clientsWithInstruction = instructionWrapper.getClientList();

        //find out the type of message and query accordingly
        for(ClientWithInstruction client : clientsWithInstruction){
            switch(client.getInstructionType()){

                case DEFAULT:
                case HEALTH:
                    System.out.println("HEALTH");

                    Boolean clientConnected = QueryHandler.healthHandLer(client);

                    if(Boolean.TRUE.equals(clientConnected)) {
                        client.message.setClientResponse("The Client is Connected");
                    }else{
                        client.message.setClientResponse("The Client is Disconnected");
                    }
                    break;

                case INFO:
                    System.out.println("info");
                    //TODO info
                    break;


                case METRICS:
                    System.out.println("METRICS");
                    client.message.setClientResponse(QueryHandler.metricsHandler(client));
                    break;

                case QUERY:
                    System.out.println("QUERY");
                    client.message.setClientResponse(QueryHandler.queryHandler(client));
                    break;

                case SHUTDOWN:
                    System.out.println("SHUWDOWN");
                    client.message.setClientResponse(QueryHandler.shutdownHandler(client));
                    break;

                default:
                    System.out.println("Default bit");
                    break;
            }

        }


        model.addAttribute("clientsWithInstruction", clientsWithInstruction);
        //model.addAttribute("instructionWrapper", instructionWrapper);
        System.out.println(instructionWrapper.getClientList() != null ? instructionWrapper.getClientList().size() : "null list");

        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("clientsWithInstruction", clientsWithInstruction);
        }

        return "QueryController/results";
    }

}

//TESTING PURPOSES
//            ClientWithSelection firstClient = allClients.get(0);
//            System.out.println("#####################################TESTING##############################");
//            System.out.println(firstClient.toString());


//TESTING
//            RestTemplate restTemplate = new RestTemplate();
//            String url = firstClient.getIpAddress() + ":" + firstClient.getPortNumber();
//
//            String workingUrl = "http://192.168.1.151:8080";
//            System.out.println(url);
//            Message message = restTemplate.getForObject(workingUrl, Message.class);
//
//            System.out.println("#####################################TOSTRING##############################");
//            System.out.println(message.toString());
//
//            model.addAttribute("message", message);

//required to remove thymeleaf error