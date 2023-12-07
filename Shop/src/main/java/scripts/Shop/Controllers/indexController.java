package scripts.Shop.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/main")
    public String main(){return "main";}

    @GetMapping("/signup")
    public String signup(){return "signup";}

    @GetMapping("/img")
    public String img(){return "img";}
}
