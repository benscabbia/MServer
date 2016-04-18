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
                allClientsWithInstructions.add(new ClientWithInstruction(clientSelected, new Message("Default"), InstructionType.DEFFAULT));
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



            if(false){
                WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
                context.setVariable("allClients", allClients);
                //context.setVariable("queryWrapper", queryWrapper);
                context.setVariable("instructionWrapper", instructionWrapper);
            }
        }
            return "QueryController/querySender";
    }

    //   ------------------------------------- POST /results---------------------------------------------------
        //i think i need to now post my next query from send queries to here
    //this is where i should be able to retrieve the message and instruction type.

    @RequestMapping(value="/querySender/results", method = RequestMethod.POST)
//    public String sendQuery(@ModelAttribute QueryWrapper wrapperReceived, Model model){
    public String sendQuery(@ModelAttribute ClientWithInstructionWrapper instructionWrapper, Model model){

        //model.addAttribute("wrapper", wrapperReceived);
        model.addAttribute("instructionWrapper", instructionWrapper);
        System.out.println("#############@@@@@@@@@@@@@@@@@@@@@@@@@@@@#########################");

        System.out.println(instructionWrapper.getClientList() != null ? instructionWrapper.getClientList().size() : "null list");






        return "QueryController/results";
    }














    private void populateClientList(){
        allClients.add(new Client(1, "192.168.1.151", "8080", "Ben's Desktop"));
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