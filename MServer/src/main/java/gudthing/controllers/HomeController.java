package gudthing.controllers;

import gudthing.models.Client;
import gudthing.properties.SessionExceptionOperation;
import gudthing.properties.SessionFailureOperation;
import gudthing.properties.SessionSuccessOperations;
import gudthing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;

import java.util.List;

/**
 * Created by Ben on 28/03/2016.
 */

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(method= RequestMethod.GET)
    String index(Model model) {

        List<Client> allClients= clientRepository.findAll();

        model.addAttribute("clientCount", allClients.size());
        model.addAttribute("successOperation", SessionSuccessOperations.getCounter());
        model.addAttribute("failureOperation", SessionFailureOperation.getCounter());
        model.addAttribute("queryExceptionOperation", SessionExceptionOperation.getCounter());


        //required to remove thymeleaf error
        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("clientCount", allClients.size());
            context.setVariable("successOperation", SessionSuccessOperations.getCounter());
            context.setVariable("failureOperation", SessionFailureOperation.getCounter());
            context.setVariable("queryExceptionOperation", SessionExceptionOperation.getCounter());
        }



        return "index";
    }

    @RequestMapping(value = "login", method= RequestMethod.GET)
    String showLogin(){
        return "login";
    }

}