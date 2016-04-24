package gudthing.controllers;

import gudthing.models.*;
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


    //   ------------------------------------- POST /querySender---------------------------------------------------

    @RequestMapping(value="/querySender", method = RequestMethod.POST)
    public String processQuery(@ModelAttribute ClientWithSelectionListWrapper wrapper, Model model){

        System.out.println(wrapper.getClientList() != null ? wrapper.getClientList().size() : "null list");
        System.out.println("--");


        //model.addAttribute("wrapper", wrapper);

        if(wrapper != null) {
            List<ClientWithSelection> allClients = wrapper.getSelectedClients();

            //no selection was made, select all
            if (allClients.size() < 1) {
                allClients = allClientsWithSelection;
            }


            ArrayList<ClientWithInstruction> allClientsWithInstructions = new ArrayList<ClientWithInstruction>();

            for(ClientWithSelection clientSelected : allClients){
                allClientsWithInstructions.add(new ClientWithInstruction(clientSelected, new Message("Message is loud and clear"), InstructionType.DEFAULT));
            }

//            QueryWrapper queryWrapper = new QueryWrapper();
//            queryWrapper.setQueries(allClientsToQuery);

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

    //   ------------------------------------- POST /results---------------------------------------------------
    @RequestMapping(value="/querySender/results", method = RequestMethod.POST)
    public String sendQuery(@ModelAttribute ClientWithInstructionWrapper instructionWrapper, Model model){

        List<ClientWithInstruction> clientsWithInstruction = instructionWrapper.getClientList();

        //List<ClientWithResult> clientWithResults = new ArrayList<ClientWithResult>();


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
                    System.out.println("query");
                    break;

                case SHUTDOWN:
                    System.out.println("SHUWDOWN");
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














    private void populateClientList(){
        allClients.add(new Client(1, "192.168.1.152", "8080", "Ben's Desktop"));
        allClients.add(new Client(2, "192.168.1.65", "123", "another IP address"));
        allClients.add(new Client(3, "192.168.3.48", "5895"));
        allClients.add(new Client(4, "192.168.24.45", "9999"));
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