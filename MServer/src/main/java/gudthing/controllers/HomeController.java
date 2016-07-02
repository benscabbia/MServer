package gudthing.controllers;

import gudthing.models.Client;
import gudthing.properties.SessionExceptionOperation;
import gudthing.properties.SessionFailureOperation;
import gudthing.properties.SessionSuccessOperations;
import gudthing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
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

    @RequestMapping(value = "/sessiondata/success", method= RequestMethod.GET)
    String displaySuccessfulSessionData(Model model){

        model.addAttribute("sessions", SessionSuccessOperations.getSessionData());

        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("sessions", SessionSuccessOperations.getSessionData());
        }
        return "sessionData";
    }

    @RequestMapping(value = "/sessiondata/exception", method= RequestMethod.GET)
    String displayUnsuccessfulSessionData(Model model){

        model.addAttribute("sessions", SessionSuccessOperations.getSessionData());

        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("sessions", SessionSuccessOperations.getSessionData());
        }
        return "sessionData";
    }

    @RequestMapping(value = "/sessiondata/failure", method= RequestMethod.GET)
    String displayFailureSessionData(Model model){

        model.addAttribute("sessions", SessionSuccessOperations.getSessionData());

        if(false){
            WebContext context = new org.thymeleaf.context.WebContext(null,null,null);
            context.setVariable("sessions", SessionSuccessOperations.getSessionData());
        }

        return "sessionData";
    }

    @RequestMapping(value = "login", method= RequestMethod.GET)
    String showLogin(){
        return "login";
    }

    @RequestMapping(value = "login", method= RequestMethod.POST)
    String loggedIn(){
        return "/";
    }

    @RequestMapping(value = "/temp", method= RequestMethod.GET)
    @ResponseBody
    String test(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://192.168.1.201:8082/health";

        try{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if(HttpStatus.OK == response.getStatusCode()){
                System.out.println("TRUE");
            }else{
                System.out.println("FALSE");
            }
        }catch (Exception e){
            System.out.println("EXCEPTION: The RestTemplate is unable to connect with the client ");
            System.out.println("FALSE");
        }

        return "Page loaded OK";
    }

}