package gudthing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ben on 28/03/2016.
 */

@Controller
public class DashboardController {
    @RequestMapping("/")
    String home() {
        return "index";
    }
}
