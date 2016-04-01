package gudthing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ben on 28/03/2016.
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(method= RequestMethod.GET)
    String index() {
        return "index";
    }


//    @RequestMapping("/clients")
//    String showClient(){return "clients"; }

}
