package gudthing.controllers;

import gudthing.models.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 30/03/2016.
 */
@Controller
@RequestMapping("/clients")
public class ClientController {

    private List<Client> allClients = new ArrayList<Client>();

    @RequestMapping(method= RequestMethod.GET)
    String index(Model model) {
        populateClientList();
        model.addAttribute("clients", allClients);

        //required so form can use it on POST
        model.addAttribute("client", new Client());


        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("clients", allClients);
        }
        return "clients";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String processSubmit(@ModelAttribute Client client, Model model){

        model.addAttribute("client", client);

        Client newClient = client;
        System.out.println("==================================================================");
        System.out.println(client.getClientID());
        System.out.println(client.getIpAddress());
        System.out.println(client.getDescription());



        return "clients";
    }















//    public String greeting(@RequestParam(value="name", required = false, defaultValue = "world") String name, Model model) {
//        model.addAttribute("name", name);
//    }

    private void populateClientList(){
        allClients.add(new Client(1, "192.168.1.56", "this is a made up ip address :p"));
        allClients.add(new Client(2, "192.168.1.65", "another IP address"));
        allClients.add(new Client(3, "192.168.3.48"));
        allClients.add(new Client(4, "192.168.24.45"));


    }




}
