package gudthing.controllers;

import gudthing.models.Client;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 30/03/2016.
 */
@Controller
@RequestMapping("/clients")
public class ClientController {
    //repopulate list once only
    private boolean started = false;
    private List<Client> allClients = new ArrayList<Client>();

    //  ---------------------------------------GET /------------------------------------------------------
    @RequestMapping(method= RequestMethod.GET)
    public String index(Model model) {

        if(!started){
            populateClientList();
            started = true;
        }
        model.addAttribute("clients", allClients);

        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("clients", allClients);
        }
        return "clients";
    }

    //  ---------------------------------------GET /{id}/details-------------------------------------------------
    @RequestMapping(value="/{clientID}/details", method = RequestMethod.GET)
    public String getClientDetails(@PathVariable int clientID, Model model){
        Client client = getClientById(clientID);
        model.addAttribute("client", client);
        //TODO details page is to be completed
        return "viewClient";
    }

    //  ---------------------------------------GET /Create-------------------------------------------------------
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String addNewClientForm(Model model){
        //required so form can use it on POST
        model.addAttribute("client", new Client());
        return "createClient";
    }

    //  -------------------------------------- POST /Create------------------------------------------------------
    @RequestMapping(value="/create", method= RequestMethod.POST)
    public String processSubmit(@ModelAttribute Client client, Model model){
        model.addAttribute("client", client);
        allClients.add(client);
        //good practice to redirect after handling a POST request.
        return "redirect:/clients";
    }

    //   ------------------------------------- GET  /{id}---------------------------------------------------
    @RequestMapping(value="/{clientID}", method=RequestMethod.GET)
    public String getClient(@PathVariable int clientID, Model model){
        //this will need to change once db is up and running

        Client theClient = null;
        theClient = getClientById(clientID);

        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("theClient", theClient);
        }

        if(theClient == null){
            System.out.println("CLIENT NOT FOUND########################");
            throw new UserNotFoundException(clientID);
        }else{
            model.addAttribute("theClient", theClient);
            return "editClient";
        }
    }

    //   ------------------------------------- POST  /{id}/update---------------------------------------------------
    //Most browsers do not support action=PUT in HTML forms
    @RequestMapping(value="/{clientID}/update", method=RequestMethod.POST)
    public String updateClient(@ModelAttribute Client client, @PathVariable int clientID, Model model){
        if(client == null){
            throw new UserNotFoundException(clientID);
        }else{
            for(int i=0; i<allClients.size(); i++){
                Client current = allClients.get(i);
                if(current.getClientID() == client.getClientID()){
                    allClients.set(i, client);
                    break;
                }
            }
            return "redirect:/clients";
        }
    }

    //   ------------------------------------- POST  /{id}/delete---------------------------------------------------
    @RequestMapping(value="/{clientID}/delete", method=RequestMethod.POST)
    public String deleteClient(@ModelAttribute Client client, @PathVariable int clientID, Model model){
        if(client == null){
            throw new UserNotFoundException(clientID);
        }else{
            for(int i=0; i<allClients.size(); i++){
                Client current = allClients.get(i);
                if(current.getClientID() == client.getClientID()){
                    allClients.remove(i);
                    break;
                }
            }
            return "redirect:/clients";
        }
    }













//    public String greeting(@RequestParam(value="name", required = false, defaultValue = "world") String name, Model model) {
//        model.addAttribute("name", name);
//    }

    //helper method to get find client by ID
    private Client getClientById(int clientID){
        Client theClient = null;
        for(Client client : allClients){
            if(client.getClientID() == clientID){
                theClient = client;
                break;
            }
        }
        return theClient;
    }

    private void populateClientList(){
        allClients.add(new Client(1, "192.168.1.56", "this is a made up ip address :p"));
        allClients.add(new Client(2, "192.168.1.65", "another IP address"));
        allClients.add(new Client(3, "192.168.3.48"));
        allClients.add(new Client(4, "192.168.24.45"));
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int clientID) {
        super("Could not find client " + clientID);
    }
}
