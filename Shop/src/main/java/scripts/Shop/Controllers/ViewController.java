package scripts.Shop.Controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import scripts.Shop.Entity.Product.Product;
import scripts.Shop.Entity.Product.ProductResponse;
import scripts.Shop.Entity.Product.Pservice;
import scripts.Shop.Entity.Uuser.Uservice;
import scripts.Shop.Entity.Uuser.Uuser;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final Uservice service;
    private final Pservice pservice;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/main")
    public String main(){
        return "main";}

    @GetMapping("/signup")
    public String signup(){return "signup";}

    @GetMapping("/img")
    public String img(){return "img";}

    @GetMapping("/mem")
    public String findmem(Model model, HttpSession session){

        List<Uuser> memlist = service.findall();
        model.addAttribute("userlist", memlist);
        return "mem";
    }
    @GetMapping("/mem/{id}")
    public String findbyId(@PathVariable Long id, Model model){
        Uuser userDto = service.findByid(id);
        model.addAttribute("users",userDto);
        System.out.println(id);
        return "details";
    }

    @GetMapping("/signout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/items")
    public String shop(Model model){
        List<Product> productList = pservice.findall();
        model.addAttribute("itemlist",productList);
        return "items";
    }

    @GetMapping("/itemadd")
    public String additem(){
    return "itemadd";
    }

    @GetMapping("/item/{id}")
    public String itemdetail(@PathVariable Long id, Model model){
        ProductResponse.FindByIdDto product = pservice.findByid(id);
        model.addAttribute("items",product);
        System.out.println(id);
        return "itemdetails";
    }

    @GetMapping("/mem/delete/{id}")
    public String delete(@PathVariable Long id){
        service.deleteById(id);
        return "redirect:/mem";
    }
}
